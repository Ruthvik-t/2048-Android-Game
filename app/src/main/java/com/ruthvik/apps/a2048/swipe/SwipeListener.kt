package com.ruthvik.apps.a2048.swipe

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import com.ruthvik.apps.a2048.swipe.SwipeCallback.Direction
import kotlin.math.abs

class SwipeListener constructor(
    private val context: Context,
    private val swipeCallback: SwipeCallback,
) : GestureDetector.OnGestureListener {

    private var gestureDetector = GestureDetector(context, this)

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float,
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float,
    ): Boolean {

        swipeCallback.apply {
            if (abs(velocityX) > abs(velocityY)) {
                if (velocityX > 0) {
                    onSwipe(Direction.RIGHT)
                } else {
                    onSwipe(Direction.LEFT)
                }
            } else {
                if (velocityY > 0) {
                    onSwipe(Direction.DOWN)
                } else {
                    onSwipe(Direction.UP)
                }
            }
        }

        return false
    }

    fun onTouchEvent(event: MotionEvent) {
        gestureDetector.onTouchEvent(event)
    }
}