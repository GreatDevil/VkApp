package com.example.lestradde.vkapp.sliderMenu.transform;

import android.view.View;

import com.example.lestradde.vkapp.sliderMenu.util.SideNavUtils;

/**
 * Created by lestradde on 16.09.18.
 */

public class YTranslationTransformation implements RootTransformation {

    private static final float START_TRANSLATION = 0f;

    private final float endTranslation;

    public YTranslationTransformation(float endTranslation) {
        this.endTranslation = endTranslation;
    }

    @Override
    public void transform(float dragProgress, View rootView) {
        float translation = SideNavUtils.evaluate(dragProgress, START_TRANSLATION, endTranslation);
        rootView.setTranslationY(translation);
    }
}
