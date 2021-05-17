package com.ruthvik.apps.a2048.sprites

import android.content.res.Resources
import android.graphics.*
import com.ruthvik.apps.a2048.R

class Score constructor(
    private val resources: Resources,
    private val screenWidth: Int,
    private val screenHeight: Int,
    private val standardSize: Int,
): Sprite {

    private lateinit var bitmapScore: Bitmap
    private lateinit var paint: Paint

    private var score = 0

    init {
        val width = resources.getDimension(R.dimen.score_width).toInt()
        val height = resources.getDimension(R.dimen.score_height).toInt()

        val resource = BitmapFactory.decodeResource(resources, R.drawable.score)
        bitmapScore = Bitmap.createScaledBitmap(resource, width, height, false)

        paint = Paint()
        paint.apply {
            color = Color.BLACK
            style = Paint.Style.FILL
            textSize = resources.getDimension(R.dimen.score_text_size)
        }
    }

    override fun draw(canvas: Canvas) {
       canvas.drawBitmap(bitmapScore, screenWidth/4 - bitmapScore.width/2f, bitmapScore.height.toFloat(), null )

        val width = paint.measureText(score.toString())
        canvas.drawText(score.toString(), screenWidth/4 - width/2f, bitmapScore.height * 4f, paint)
    }

    override fun update() {

    }

    fun updateScore(delta: Int) {
        score += delta
    }
}