package net.nend.sample.kotlin.nativead

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.HorizontalScrollView

class MarqueeView : HorizontalScrollView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        requestDisallowInterceptTouchEvent(true)
    }

    override fun onTouchEvent(ev: MotionEvent) = false
}