package com.ruthvik.apps.a2048

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.ruthvik.apps.a2048.sprites.EndGame
import com.ruthvik.apps.a2048.sprites.Grid
import com.ruthvik.apps.a2048.sprites.Score
import com.ruthvik.apps.a2048.sprites.TileManager
import com.ruthvik.apps.a2048.swipe.SwipeCallback
import com.ruthvik.apps.a2048.swipe.SwipeListener

class GameView : SurfaceView, SurfaceHolder.Callback, SwipeCallback, GameManagerCallback {

    private lateinit var grid: Grid
    private lateinit var tileManager: TileManager
    private lateinit var swipeListener: SwipeListener
    private lateinit var endGameSprite: EndGame
    private lateinit var score: Score

    private var gameThread: GameThread? = null

    private var endGame = false

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
        isLongClickable = true

        grid = Grid(context.resources, screenWidth, screenHeight, standardSize)
        tileManager = TileManager(context.resources, screenWidth, screenHeight, standardSize, this)
        swipeListener = SwipeListener(context, this)
        endGameSprite = EndGame(context.resources, screenWidth, screenHeight)
        score = Score(context.resources, screenWidth, screenHeight, standardSize)
    }

    private fun restartGame() {
        endGame = false
        tileManager.restartGame()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gameThread = GameThread(holder, this).also {
            it.setRunning(true)
            it.start()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(endGame) {
            if(event?.action == MotionEvent.ACTION_DOWN)
                restartGame()
        } else {
            event?.let {
                swipeListener.onTouchEvent(it)
            }
        }
        return super.onTouchEvent(event)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        gameThread?.surfaceHolder = holder
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true;

        while(retry) {
            try {
                gameThread?.let {
                    it.setRunning(false)
                    it.join()
                    retry = false
                }
            } catch (exception: InterruptedException){

            }
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.let {
            it.drawRGB(255,255,255) // white background for the screen
            grid.draw(canvas)
            tileManager.draw(canvas)
            score.draw(canvas)

            if(endGame) {
                endGameSprite.draw(it)
            }
        }
    }

    override fun onSwipe(direction: SwipeCallback.Direction) {
        tileManager.onSwipe(direction)
    }

    override fun gameOver() {
        endGame = true
    }

    override fun updateScore(delta: Int) {
        score.updateScore(delta)
    }

    internal fun update(){
        if(!endGame) {
            tileManager.update()
        }
    }
}