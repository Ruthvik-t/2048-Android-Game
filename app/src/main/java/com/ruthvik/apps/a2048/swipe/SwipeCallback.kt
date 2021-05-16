package com.ruthvik.apps.a2048.swipe

interface SwipeCallback {

    fun onSwipe(direction: Direction)

    enum class Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
    }
}