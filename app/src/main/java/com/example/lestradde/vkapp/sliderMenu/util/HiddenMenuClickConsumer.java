package com.example.lestradde.vkapp.sliderMenu.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.example.lestradde.vkapp.sliderMenu.SlidingRootNavLayout;

/**
 * Created by lestradde on 16.09.18.
 */

public class HiddenMenuClickConsumer extends View {

    private SlidingRootNavLayout menuHost;

    public HiddenMenuClickConsumer(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return menuHost.isMenuClosed();
    }

    public void setMenuHost(SlidingRootNavLayout layout) {
        this.menuHost = layout;
    }
}
