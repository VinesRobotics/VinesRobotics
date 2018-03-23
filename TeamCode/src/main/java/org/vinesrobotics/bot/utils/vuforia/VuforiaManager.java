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

package org.vinesrobotics.bot.utils.vuforia;

import android.os.AsyncTask;

import com.vuforia.CameraDevice;
import com.vuforia.ObjectTracker;
import com.vuforia.Tracker;
import com.vuforia.TrackerManager;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.R;
import org.vinesrobotics.bot.utils.Utils;

/**
 * Created by ViBots on 11/18/2017.
 */

public class VuforiaManager {

    private boolean mCameraRunning;

    public static void init(boolean blockUntilDone) {
        InitVuforiaTask task = new InitVuforiaTask();

        task.execute();

        try {
            if (blockUntilDone) {
                synchronized (task) {
                    task.wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void init() {init(true);}



    //
    // An async task to initialize Vuforia asynchronously.
    private static class InitVuforiaTask extends AsyncTask<Void, Integer, Boolean> {
        // Initialize with invalid value:
        private int mProgressValue = -1;


        protected Boolean doInBackground(Void... params) {
            // Prevent the onDestroy() method to overlap with initialization:
            //synchronized (mShutdownLock) {
            Vuforia.setInitParameters(AppUtil.getInstance().getActivity(), 0,
            Utils.getContext().getResources().getText(R.string.VuForiaKey).toString());

            do {
                // Vuforia.init() blocks until an initialization step is
                // complete, then it proceeds to the next step and reports
                // progress in percents (0 ... 100%).
                // If Vuforia.init() returns -1, it indicates an error.
                // Initialization is done when progress has reached 100%.
                mProgressValue = Vuforia.init();

                // Publish the progress value:
                publishProgress(mProgressValue);

                // We check whether the task has been canceled in the
                // meantime (by calling AsyncTask.cancel(true)).
                // and bail out if it has, thus stopping this thread.
                // This is necessary as the AsyncTask will run to completion
                // regardless of the status of the component that
                // started is.
            } while (!isCancelled() && mProgressValue >= 0
            && mProgressValue < 100);

            synchronized (this) {
                this.notifyAll();
            }

            return (mProgressValue > 0);
            //}
        }
    }
    //

    int camera;

    /*public void initCamera(int camdir) {
        camera = camdir;
        CameraDevice.getInstance().init(camdir);

        if(!CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_CONTINUOUSAUTO))
        {
            // If continuous autofocus mode fails, attempt to set to a different mode
            if(!CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_TRIGGERAUTO))
            {
                CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_NORMAL);
            }
        }


    }*/

    public void startVuforia(int camera) {
        if (!Vuforia.isInitialized()) init();

        startCameraAndTrackers(camera);

        if(!CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_CONTINUOUSAUTO))
        {
            // If continuous autofocus mode fails, attempt to set to a different mode
            if(!CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_TRIGGERAUTO))
            {
                CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_NORMAL);
            }
        }
    }

    // called by startAR
    private void startCameraAndTrackers(int cameradev)
    {
        String error;
        if(mCameraRunning)
        {
            error = "Camera already running, unable to open again";
        }

        camera = cameradev;
        if (!CameraDevice.getInstance().init(camera))
        {
            error = "Unable to open camera device: " + camera;
        }

        if (!CameraDevice.getInstance().selectVideoMode(
                CameraDevice.MODE.MODE_DEFAULT))
        {
            error = "Unable to set video mode";
        }

        if (!CameraDevice.getInstance().start())
        {
            error = "Unable to start camera device: " + camera;
        }

        //mSessionControl.doStartTrackers();

        mCameraRunning = true;
    }

    public void stopCamera()
    {
        if (mCameraRunning)
        {
            //mSessionControl.doStopTrackers();
            mCameraRunning = false;
            CameraDevice.getInstance().stop();
            CameraDevice.getInstance().deinit();
        }
    }

    public boolean doStartTrackers()
    {
        // Indicate if the trackers were started correctly
        boolean result = true;

        Tracker objectTracker = TrackerManager.getInstance().getTracker(
                ObjectTracker.getClassType());
        if (objectTracker != null)
            objectTracker.start();

        return result;
    }

    public Tracker getObjectTracker() {
        return TrackerManager.getInstance().getTracker(
                ObjectTracker.getClassType());
    }

    public boolean doStopTrackers()
    {
        // Indicate if the trackers were stopped correctly
        boolean result = true;

        Tracker objectTracker = TrackerManager.getInstance().getTracker(
                ObjectTracker.getClassType());
        if (objectTracker != null)
            objectTracker.stop();

        return result;
    }

    public boolean doDeinitTrackers()
    {
        // Indicate if the trackers were deinitialized correctly
        boolean result = true;

        TrackerManager tManager = TrackerManager.getInstance();
        tManager.deinitTracker(ObjectTracker.getClassType());

        return result;
    }

}
