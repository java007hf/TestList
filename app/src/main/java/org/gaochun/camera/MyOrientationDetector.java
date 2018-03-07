package org.gaochun.camera;

import android.content.Context;
import android.view.OrientationEventListener;


public class MyOrientationDetector extends OrientationEventListener {
    int Orientation;

    public MyOrientationDetector(Context context) {
        super(context);
    }

    @Override
    public void onOrientationChanged(int orientation) {
        this.Orientation = orientation;
    }

    public int getOrientation() {
        return Orientation;
    }
}