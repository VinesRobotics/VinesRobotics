/*
 * Copyright (c) 2016 Vines High School Robotics Team
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

package org.vinesrobotics.sixteen.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.camera2.*;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.view.Surface;

import org.vinesrobotics.sixteen.App;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by aaron on 12/29/2016.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera {

    public class CameraException extends Exception {}

    private CameraManager manager;
    public CameraManager manager() {
        return manager;
    }


    private CameraDevice cam_inst;
    private Handler handle;
    private ImageReader imageReader;

    public Image image;

    private Camera(CameraManager cm) throws CameraAccessException {
        handle = new Handler();

        imageReader = ImageReader.newInstance(1024, 1024, ImageFormat.JPEG, 2);
        imageReader.setOnImageAvailableListener(onImageAvailableListener, handle);

        manager = cm;
        for (String cameraId : manager.getCameraIdList()) {
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);

            if (characteristics.get(CameraCharacteristics.LENS_FACING) != CameraCharacteristics.LENS_FACING_BACK) {
                continue;
            }

            setCameraID(cameraId);
        }
    }

    public void setCameraID(String s){
        //cam_inst = manager.
    }

    private CameraCaptureSession session;
    private final CameraDevice.StateCallback cameraStateCB = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice device) {
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
        public void onDisconnected(CameraDevice cameraDevice) {}

        @Override
        public void onError(CameraDevice cameraDevice, int error) {}
    };

    private CameraCaptureSession.StateCallback sessionStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession sess) {
            session = sess;
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {}
    };

    private ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader){
            if (image != null) image.close();
            Image img = reader.acquireLatestImage();
            image = img;
        }
    };

    public static Camera getCamera() throws CameraAccessException {
        Context.CAMERA_SERVICE
        return new Camera((CameraManager) App.context.getSystemService(Context.CAMERA_SERVICE));
    }

}
