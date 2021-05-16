package com.ruthvik.apps.a2048

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.ruthvik.apps.a2048.sprites.Grid

class GameView : SurfaceView, SurfaceHolder.Callback {

    private lateinit var grid: Grid

    private var gameThread: GameThread? = null

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
        gameThread = GameThread(holder, this).also {
            it.setRunning(true)
            it.start()
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.let {
            it.drawRGB(255,255,255) // white background for the screen
            grid.draw(canvas)
        }
    }
}