package com.example.lestradde.vkapp.sliderMenu.transform;

import android.os.Build;
import android.view.View;

import com.example.lestradde.vkapp.sliderMenu.util.SideNavUtils;

/**
 * Created by lestradde on 16.09.18.
 */

public class ElevationTransformation implements RootTransformation {

    private static final float START_ELEVATION = 0f;

    private final float endElevation;

    public ElevationTransformation(float endElevation) {
        this.endElevation = endElevation;
    }

    @Override
    public void transform(float dragProgress, View rootView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float elevation = SideNavUtils.evaluate(dragProgress, START_ELEVATION, endElevation);
            rootView.setElevation(elevation);
        }
    }
}
