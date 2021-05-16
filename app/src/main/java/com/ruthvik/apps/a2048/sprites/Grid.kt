package com.ruthvik.apps.a2048.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.ruthvik.apps.a2048.R

class Grid constructor(
    private val resources: Resources,
    private val screenWidth: Int,
    private val screenHeight: Int,
    private val standardSize: Int,
) : Sprite {

    private var grid: Bitmap? = null

    init {
        val bitmap: Bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.grid)
        grid = Bitmap.createScaledBitmap(bitmap, standardSize * 4, standardSize * 4, false)
    }

    override fun draw(canvas: Canvas) {
        grid?.let {
            canvas.drawBitmap(it,
                screenWidth / 2 - it.width / 2f,
                screenHeight / 2 - it.height / 2f,
                null)
        }
    }

    override fun update() {

    }
}