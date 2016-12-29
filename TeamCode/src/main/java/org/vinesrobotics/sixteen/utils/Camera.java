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
import android.hardware.camera2.*;
import android.os.Build;

import org.vinesrobotics.sixteen.App;

/**
 * Created by aaron on 12/29/2016.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera {

    private CameraManager manager;

    private Camera cam_inst;

    private Camera(CameraManager cm) throws CameraAccessException {
        manager = cm;
        String[] lst = manager.getCameraIdList();
        setCameraID(lst[0]);
    }

    public void setCameraID(String s){
        //cam_inst = manager.
    }

    public static Camera getCamera() throws CameraAccessException {
        return new Camera((CameraManager) App.context.getSystemService(App.context.CAMERA_SERVICE));
    }

}
