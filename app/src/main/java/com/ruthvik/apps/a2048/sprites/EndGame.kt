package com.ruthvik.apps.a2048.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.ruthvik.apps.a2048.R

class EndGame constructor(
    private val resources: Resources,
    private val screenWidth: Int,
    private val screenHeight: Int,
) : Sprite {

    private lateinit var bitmap: Bitmap

    init {
        val endGameWidth = resources.getDimension(R.dimen.endgame_width).toInt()
        val endGameHeight = resources.getDimension(R.dimen.endgame_height).toInt()

        val source = BitmapFactory.decodeResource(resources, R.drawable.gameover)
        bitmap = Bitmap.createScaledBitmap(source, endGameWidth, endGameHeight, false)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap,
            screenWidth / 2 - bitmap.width / 2f,
            screenHeight / 2 - bitmap.height / 2f,
            null)
    }

    override fun update() {}
}