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

package org.vinesrobotics.bot.utils.opencv;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.vinesrobotics.bot.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ViBots on 12/21/2017.
 */

public class OpenCvManager implements CameraBridgeViewBase.CvCameraViewListener2 {
    static {
        System.loadLibrary("opencv_java3");
    }

    private static Handler createView = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            OpenCvManager cvm = (OpenCvManager)msg.obj;

            Activity rcact = FtcRobotControllerActivity.ActivityInstance;

            cvm.mOpenCvCameraView =
                    (JavaCameraView)rcact.findViewById(com.qualcomm.ftcrobotcontroller.R.id.color_blob_detection_activity_surface_view);

            cvm.mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
            cvm.mOpenCvCameraView.setCvCameraViewListener(cvm);
            cvm.mOpenCvCameraView.setCameraIndex(JavaCameraView.CAMERA_ID_FRONT);

            Log.i(TAG, "CVCameraView created");
        }
    };

    private static String TAG = "ViBots::OpenCvManager";
    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(Utils.getContext()) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();

                    recorder.setVideoSize(mOpenCvCameraView.getWidth(), mOpenCvCameraView.getHeight());
                    try {
                        recorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    recorder.start();

                    mOpenCvCameraView.setRecorder(recorder);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                    Log.i(TAG, "Callback Failed");
                } break;
            }
        }
    };
    private Mat mRgba;
    private List<ColorBlobDetector> mDetectors = new ArrayList<>();
    private boolean mIsColorSelected = true;
    private JavaCameraView mOpenCvCameraView;
    private MediaRecorder recorder;

    public static int getFrontFacingCameraId() {
        int cameraCount = 0;
        int camId = -1;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    camId = camIdx;
                } catch (RuntimeException e) {
                    Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return camId;
    }

    public void initCV() {

        if (recorder == null)
            recorder = new VideoRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        recorder.setOutputFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + new Date().getTime() + "_auton.mp4");

        Message msg = createView.obtainMessage(0, this);
        msg.sendToTarget();

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, Utils.getContext(), mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        //mOpenCvCameraView.setCameraIndex(cam);
    }

    public void stopCV() {
        mOpenCvCameraView.disableView();
        recorder.stop();
        recorder.reset();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        Log.i(TAG, "View started");
    }

    public void registerBlobDetector(ColorBlobDetector blob) {
        mDetectors.add(blob);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        //Log.i(TAG, "Frame accepted");

        if (mIsColorSelected) {
            for (ColorBlobDetector det : mDetectors) {
                det.process(mRgba);
            }
            /*List<MatOfPoint> contours = mDetector.getContours();
            Log.e(TAG, "Contours count: " + contours.size());
            Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);

            Mat colorLabel = mRgba.submat(4, 68, 4, 68);
            colorLabel.setTo(mBlobColorRgba);

            Mat spectrumLabel = mRgba.submat(4, 4 + mSpectrum.rows(), 70, 70 + mSpectrum.cols());
            mSpectrum.copyTo(spectrumLabel);*/
        }

        return mRgba;
    }



    public Scalar converScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }
}
