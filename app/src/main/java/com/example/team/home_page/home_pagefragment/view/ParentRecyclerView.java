package com.example.team.home_page.home_pagefragment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ParentRecyclerView extends RecyclerView {
    private int mTouchSlop;
    private int mLastXIntercepted;
    private int mLastYIntercepted;

    public ParentRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    //不拦截，继续分发下去
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        boolean intercepted = false;
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastXIntercepted = x;
                mLastYIntercepted = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaX = y - mLastXIntercepted;
                if (isTop() && deltaX > 0 && Math.abs(deltaX) > mTouchSlop) {
                    /*下拉*/
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastXIntercepted = x;
        mLastYIntercepted = y;
        return intercepted;


    }

    boolean isTop() {
        return false;
    }
}