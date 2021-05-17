package com.ruthvik.apps.a2048.sprites

import android.graphics.Bitmap

interface TileManagerCallback {

    fun getBitmap(count: Int): Bitmap

    fun finishedMoving(tile: Tile)

    fun updateScore(delta: Int)
}