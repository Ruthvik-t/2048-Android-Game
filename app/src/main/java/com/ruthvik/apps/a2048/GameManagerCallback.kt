package com.ruthvik.apps.a2048

/**
 * An interface to communicate between tilemanager and game view
 */
interface GameManagerCallback {

    fun gameOver()

    fun updateScore(delta: Int)
}