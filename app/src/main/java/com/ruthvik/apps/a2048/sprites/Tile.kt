package com.ruthvik.apps.a2048.sprites

import android.graphics.Canvas

class Tile constructor(
    private val screenWidth: Int,
    private val screenHeight: Int,
    private val standardSize: Int,
    private val tileManagerCallback: TileManagerCallback,
    private val matrixX: Int,
    private val matrixY: Int,
): Sprite {

    private var currentX: Int = 0
    private var currentY: Int = 0

    private var count = 1

    init {
        currentX = screenWidth/2 - 2*standardSize + matrixY * standardSize
        currentY = screenHeight/2 - 2*standardSize + matrixX * standardSize
    }


    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(tileManagerCallback.getBitmap(count), currentX.toFloat(), currentY.toFloat(), null)
    }
}