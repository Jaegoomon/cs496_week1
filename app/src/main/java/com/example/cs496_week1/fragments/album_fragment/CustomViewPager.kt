package com.example.cs496_week1.fragments.album_fragment

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context,attributeSet)

    override fun onTouchEvent(ev: MotionEvent?): Boolean = try {
        super.onTouchEvent(ev)
    } catch (e : Exception){
        false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = try {
        super.onInterceptTouchEvent(ev)
    } catch (e : Exception){
        false
    }
}
