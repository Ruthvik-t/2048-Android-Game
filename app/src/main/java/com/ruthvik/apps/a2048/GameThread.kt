package com.ruthvik.apps.a2048

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameThread (
    var surfaceHolder: SurfaceHolder,
    private val gameView: GameView
) : Thread() {

    private val targetFps = 60

    private var canvas: Canvas? = null

    private var running: Boolean = false

    fun setRunning(isRunning: Boolean) {
        this.running = isRunning
    }

    override fun run() {
        super.run()
        var startTime: Long = 0
        var timeInMillis: Long = 0
        var waitTime: Long = 0
        val targetTime = 1000/targetFps

        while(running) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameView.apply {
                        draw(canvas)
                        update()
                    }
                }
            }catch (e: Exception) {
                e.printStackTrace()
            } finally {
                canvas?.let {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            timeInMillis = (System.nanoTime() - startTime)/1000000
            waitTime = targetTime - timeInMillis

            try {
                if(waitTime > 0) {
                    sleep(waitTime)
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}