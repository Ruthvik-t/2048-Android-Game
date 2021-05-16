package com.ruthvik.apps.a2048

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.ruthvik.apps.a2048.sprites.Grid

class GameView : SurfaceView, SurfaceHolder.Callback {

    private lateinit var grid: Grid

    constructor(context: Context) : super(context) {
        initGameAndViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initGameAndViews()
    }

    private fun initGameAndViews() {
        holder.addCallback(this)

        val screenWidth = context.resources.displayMetrics.widthPixels
        val screenHeight = context.resources.displayMetrics.heightPixels
        val standardSize: Int = ((screenWidth * .88)/4).toInt() // standard size of tile

        grid = Grid(context.resources, screenWidth, screenHeight, standardSize)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }
}