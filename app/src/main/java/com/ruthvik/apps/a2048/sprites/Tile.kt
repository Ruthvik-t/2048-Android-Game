package com.ruthvik.apps.a2048.sprites

import android.graphics.Canvas
import kotlin.math.pow

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
    private var destX: Int = 0
    private var destY: Int = 0
    private var moving: Boolean = false

    //to control the speed of moving tiles
    private var speed = 50

    private var count = 1
    var toIncrement = false

    init {
        currentX = screenWidth/2 - 2*standardSize + matrixY * standardSize
        currentY = screenHeight/2 - 2*standardSize + matrixX * standardSize

        destX = currentX
        destY = currentY
    }


    fun getValue(): Int = count

    fun increment(): Tile {
        toIncrement = true
        return this
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(tileManagerCallback.getBitmap(count), currentX.toFloat(), currentY.toFloat(), null)

        if(moving && currentX == destX && currentY == destY) {
            moving = false

            if(toIncrement) {
                count++
                toIncrement = false
                val amount = 2.toDouble().pow(count.toDouble()).toInt()
                tileManagerCallback.updateScore(amount)
            }
            tileManagerCallback.finishedMoving(this)
        }
    }

    override fun update() {
        if(currentX < destX) {
            if(currentX + speed > destX) {
                currentX = destX
            } else {
                currentX += speed
            }
        } else if (currentX > destX) {
            if(currentX - speed < destX) {
                currentX = destX
            } else {
                currentX -= speed
            }
        }

        if(currentY < destY) {
            if(currentY + speed > destY) {
                currentY = destY
            } else {
                currentY += speed
            }
        } else if(currentY > destY) {
            if(currentY - speed < destY) {
                currentY = destY
            } else {
                currentY -= speed
            }
        }
    }

    fun move(mX: Int, mY: Int) {
        moving = true
        destX = screenWidth/2 - 2*standardSize + mY * standardSize
        destY = screenHeight/2 - 2*standardSize + mX * standardSize
    }
}