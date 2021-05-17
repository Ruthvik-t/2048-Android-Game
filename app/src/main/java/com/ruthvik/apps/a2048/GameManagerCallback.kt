package com.ruthvik.apps.a2048

interface GameManagerCallback {

    fun gameOver()

    fun updateScore(delta: Int)
}