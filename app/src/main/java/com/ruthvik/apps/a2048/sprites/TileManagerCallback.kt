package com.ruthvik.apps.a2048.sprites

import android.graphics.Bitmap

/**
 * An interface to communicate between tile and tilemanager
 */

interface TileManagerCallback {

    fun getBitmap(count: Int): Bitmap

    fun finishedMoving(tile: Tile)

    fun updateScore(delta: Int)
}