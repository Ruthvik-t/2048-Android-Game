package com.ruthvik.apps.a2048.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.ruthvik.apps.a2048.R

class TileManager constructor(
    private val resources: Resources,
    private val screenWidth: Int,
    private val screenHeight: Int,
    private val standardSize: Int,
) : Sprite, TileManagerCallback {

    private val tile = Tile(screenWidth, screenHeight, standardSize, this,1, 1)

    private val drawables = ArrayList<Int>()
    private val tileBitMaps = HashMap<Int, Bitmap>()

    private val defaultBitmap = Bitmap.createScaledBitmap(
        BitmapFactory.decodeResource(resources, R.drawable.one),
        standardSize,
        standardSize,
        false
    )

    init {
        initDrawables()
        initialiseTileBitmaps()
    }

    private fun initDrawables() {
        drawables.add(R.drawable.one)
        drawables.add(R.drawable.two)
        drawables.add(R.drawable.three)
        drawables.add(R.drawable.four)
        drawables.add(R.drawable.five)
        drawables.add(R.drawable.six)
        drawables.add(R.drawable.seven)
        drawables.add(R.drawable.eight)
        drawables.add(R.drawable.nine)
        drawables.add(R.drawable.ten)
        drawables.add(R.drawable.eleven)
        drawables.add(R.drawable.twelve)
        drawables.add(R.drawable.thirteen)
        drawables.add(R.drawable.fourteen)
        drawables.add(R.drawable.fifteen)
        drawables.add(R.drawable.sixteen)
    }

    private fun initialiseTileBitmaps() {
        for (i in 1..16) {
            val bitmap = BitmapFactory.decodeResource(resources, drawables[i - 1])
            val tileBitmap = Bitmap.createScaledBitmap(bitmap, standardSize, standardSize, false)
            tileBitMaps[i] = tileBitmap
        }
    }


    override fun draw(canvas: Canvas) {
        tile.draw(canvas)
    }

    override fun getBitmap(count: Int): Bitmap = tileBitMaps.getOrElse(count) { defaultBitmap }
}