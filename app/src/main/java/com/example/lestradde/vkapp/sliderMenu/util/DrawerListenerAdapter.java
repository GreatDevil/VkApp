package com.example.lestradde.vkapp.sliderMenu.util;

import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.example.lestradde.vkapp.sliderMenu.callback.DragListener;
import com.example.lestradde.vkapp.sliderMenu.callback.DragStateListener;

/**
 * Created by lestradde on 16.09.18.
 */

public class DrawerListenerAdapter implements DragListener, DragStateListener {

    private DrawerLayout.DrawerListener adaptee;
    private View drawer;

    public DrawerListenerAdapter(DrawerLayout.DrawerListener adaptee, View drawer) {
        this.adaptee = adaptee;
        this.drawer = drawer;
    }

    @Override
    public void onDrag(float progress) {
        adaptee.onDrawerSlide(drawer, progress);
    }

    @Override
    public void onDragStart() {
        adaptee.onDrawerStateChanged(DrawerLayout.STATE_DRAGGING);
    }

    @Override
    public void onDragEnd(boolean isMenuOpened) {
        if (isMenuOpened) {
            adaptee.onDrawerOpened(drawer);
        } else {
            adaptee.onDrawerClosed(drawer);
        }
        adaptee.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
    }
}