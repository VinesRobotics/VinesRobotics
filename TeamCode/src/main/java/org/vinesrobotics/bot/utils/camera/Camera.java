/*
 * Copyright (c) 2017 Vines High School Robotics Team
 *
 *                            Permission is hereby granted, free of charge, to any person obtaining a copy
 *                            of this software and associated documentation files (the "Software"), to deal
 *                            in the Software without restriction, including without limitation the rights
 *                            to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *                            copies of the Software, and to permit persons to whom the Software is
 *                            furnished to do so, subject to the following conditions:
 *
 *                            The above copyright notice and this permission notice shall be included in all
 *                            copies or substantial portions of the Software.
 *
 *                            THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *                            IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *                            FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *                            AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *                            LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *                            OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *                            SOFTWARE.
 */

package org.vinesrobotics.bot.utils.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.*;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;

import org.vinesrobotics.bot.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aaron on 12/29/2016.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera {

    private Object devnull = new Object();

    public class CameraException extends Exception {}

    private CameraManager manager;
    public CameraManager manager() {
        return manager;
    }


    private CameraCaptureSession session;
    private final CameraDevice.StateCallback cameraStateCB = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice device) {
            Log.i("utils.Camera.cdsc.open", String.valueOf(device));

            cam_inst = device;

            List<Surface> outputSurfaces = new LinkedList<>();
            outputSurfaces.add(imageReader.getSurface());

            try {

                cam_inst.createCaptureSession(outputSurfaces, sessionStateCallback, null);

            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            Log.i("utils.Camera.cdsc.disc", String.valueOf(cameraDevice));
        }

        @Override
        public void onError(CameraDevice cameraDevice, int error) {
            Log.i("utils.Camera.cdsc.error", String.valueOf(cameraDevice));
            Log.i("utils.Camera.cdsc.error", String.valueOf(error));
        }
    };

    private CameraCaptureSession.StateCallback sessionStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession sess) {
            session = sess;
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {}
    };

    public boolean isImageAvaliable(){
            return image != null;
    }

    public void killImage(){
        if (isImageAvaliable())
            synchronized (image) {
                image.close();
                image = null;
            }
    }

    private ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader){
            //killImage();
            Image img = reader.acquireLatestImage();

            if (isImageAvaliable())
            synchronized (image) {
                image = img;
            }
        }
    };


    private CameraDevice cam_inst;
    private Handler handle;
    private ImageReader imageReader;

    public Image image = null;

    class HandlThread extends Thread {
        @Override
        public void run() {
            if (Looper.myLooper() == null)
                Looper.prepare();
            handle = new Handler();
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (this) {
                this.notifyAll();
            }
            Looper.loop();
        }
    }

    private Thread thr;

    private Camera(CameraManager cm) throws CameraAccessException {

        thr = new HandlThread();
        thr.start();

        synchronized (thr) {

            try {
                thr.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            imageReader = ImageReader.newInstance(1024, 1024, ImageFormat.JPEG, 2);
            imageReader.setOnImageAvailableListener(onImageAvailableListener, handle);

            manager = cm;
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

                Log.i("utils.Camera", cameraId);

                if (characteristics.get(CameraCharacteristics.LENS_FACING) != CameraCharacteristics.LENS_FACING_BACK) {
                    continue;
                }

                setCameraID(cameraId);
                return;
            }
        }
    }

    public void setCameraID(String s) throws CameraAccessException {
        Log.i("utils.Camera.sid",s);
        Log.i("utils.Camera.sid", String.valueOf(cameraStateCB));
        Log.i("utils.Camera.sid", String.valueOf(handle));
        manager.openCamera(s, cameraStateCB, handle);
    }

    public boolean isReady() {
        return cam_inst != null;
    }

    private CaptureRequest capreq;
    public void queueCapture() throws CameraAccessException {
        if (cam_inst == null) return;
        Log.i("Camera","Camera capturing");
        if (capreq == null) {
            CaptureRequest.Builder requestBuilder = cam_inst.createCaptureRequest(CameraDevice.TEMPLATE_ZERO_SHUTTER_LAG);
            requestBuilder.addTarget(imageReader.getSurface());

            // Focus
            requestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

            // Orientation
            requestBuilder.set(CaptureRequest.JPEG_ORIENTATION, 0);

            capreq = requestBuilder.build();
        }

        session.capture(capreq, new CameraCaptureSession.CaptureCallback() {
            @Override
            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                super.onCaptureCompleted(session, request, result);
            }
        }, handle);
    }

    public static Camera getCamera() throws CameraAccessException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InterruptedException {
        return new Camera((CameraManager) Utils.getApp().getApplicationContext().getSystemService(Context.CAMERA_SERVICE));
    }

}
