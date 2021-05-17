package com.ruthvik.apps.a2048.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.ruthvik.apps.a2048.GameManagerCallback
import com.ruthvik.apps.a2048.R
import com.ruthvik.apps.a2048.swipe.SwipeCallback.Direction
import com.ruthvik.apps.a2048.swipe.SwipeCallback.Direction.*
import kotlin.random.Random

class TileManager constructor(
    private val resources: Resources,
    private val screenWidth: Int,
    private val screenHeight: Int,
    private val standardSize: Int,
    private val gameManagerCallback: GameManagerCallback,
) : Sprite, TileManagerCallback {

    private val drawables = ArrayList<Int>()
    private val tileBitMaps = HashMap<Int, Bitmap>()

    private var tileMatrix: Array<Array<Tile?>> = Array(4) { Array<Tile?>(4) { null } }

    private var moving = false;

    private lateinit var movingTiles: ArrayList<Tile?>

    private var toSpawn = false

    private var endGame = false

    private val defaultBitmap = Bitmap.createScaledBitmap(
        BitmapFactory.decodeResource(resources, R.drawable.one),
        standardSize,
        standardSize,
        false
    )

    init {
        initDrawables()
        initialiseTileBitmaps()
        initGame()
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
        for (i in 0..3) {
            for (j in 0..3) {
                tileMatrix[i][j]?.draw(canvas)
            }
        }

        if(endGame){
            gameManagerCallback.gameOver()
        }
    }

    override fun getBitmap(count: Int): Bitmap = tileBitMaps.getOrElse(count) { defaultBitmap }

    private fun initGame() {
        for (i in 0..5) {
            val x: Int = Random.nextInt(4)
            val y: Int = Random.nextInt(4)

            if (tileMatrix[x][y] == null) {
                val tile = Tile(screenWidth, screenHeight, standardSize, this, x, y)
                tileMatrix[x][y] = tile
            }
        }
        movingTiles = arrayListOf()
    }

    override fun update() {
        for (i in 0..3) {
            for (j in 0..3) {
                tileMatrix[i][j]?.update()
            }
        }
    }

    override fun finishedMoving(tile: Tile) {
        movingTiles.remove(tile)
        if(movingTiles.isEmpty()) {
            moving = false
            spawn()
            checkEndGame()
        }
    }

    fun onSwipe(direction: Direction) {
        if (!moving) {
            moving = true
            val newMatrix: Array<Array<Tile?>> = Array(4) { Array<Tile?>(4) { null } }

            when (direction) {
                UP -> moveTilesUp(newMatrix)
                DOWN -> moveTilesDown(newMatrix)
                RIGHT -> moveTilesRight(newMatrix)
                LEFT -> moveTilesLeft(newMatrix)
            }

            for (i in 0..3) {
                for (j in 0..3) {
                    if(newMatrix[i][j] != tileMatrix[i][j]){
                        toSpawn = true
                        break
                    }
                }
            }
            tileMatrix = newMatrix
        }
    }

    private fun moveTilesUp(newMatrix: Array<Array<Tile?>>) {
        for (i in 0..3) {
            for (j in 0..3) {
                tileMatrix[i][j]?.let { tile ->
                    newMatrix[i][j] = tileMatrix[i][j]
                    for (k in i - 1 downTo 0) {
                        if (newMatrix[k][j] == null) {
                            newMatrix[k][j] = tile
                            if (newMatrix[k + 1][j] == tile)
                                newMatrix[k + 1][j] = null
                        } else if (newMatrix[k][j]?.getValue() == tile.getValue() && newMatrix[k][j]?.toIncrement != true) {
                            newMatrix[k][j] = tile.increment()
                            if (newMatrix[k + 1][j] == tile)
                                newMatrix[k + 1][j] = null
                        } else {
                            break
                        }
                    }
                }
            }
        }

        for (i in 0..3) {
            for (j in 0..3) {
                val tile = tileMatrix[i][j]
                var newTile: Tile? = null
                var matrixX = 0
                var matrixY = 0

                for (a in 0..3) {
                    for (b in 0..3) {
                        if (newMatrix[a][b] == tileMatrix[i][j]) {
                            newTile = newMatrix[a][b]
                            matrixX = a
                            matrixY = b
                            break
                        }
                    }
                }

                newTile?.let {
                    movingTiles.add(tile)
                    tile?.move(matrixX, matrixY)
                }
            }
        }
    }

    private fun moveTilesDown(newMatrix: Array<Array<Tile?>>) {
        for (i in 3 downTo 0) {
            for (j in 0..3) {
                tileMatrix[i][j]?.let { tile ->
                    newMatrix[i][j] = tileMatrix[i][j]

                    for (k in i + 1..3) {
                        if (newMatrix[k][j] == null) {
                            newMatrix[k][j] = tile

                            if (newMatrix[k - 1][j] == tile) {
                                newMatrix[k - 1][j] = null
                            }
                        } else if (newMatrix[k][j]?.getValue() == tile.getValue() && newMatrix[k][j]?.toIncrement != true) {
                            newMatrix[k][j] = tile.increment()

                            if (newMatrix[k - 1][j] == tile) {
                                newMatrix[k - 1][j] = null
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }

        for (i in 3 downTo 0) {
            for (j in 0..3) {
                val tile = tileMatrix[i][j]
                var newTile: Tile? = null
                var matrixX = 0
                var matrixY = 0

                for (a in 0..3) {
                    for (b in 0..3) {
                        if (newMatrix[a][b] == tile) {
                            newTile = newMatrix[a][b]
                            matrixX = a
                            matrixY = b
                        }
                    }
                }

                newTile?.let {
                    movingTiles.add(tile)
                    tile?.move(matrixX, matrixY)
                }
            }
        }
    }

    private fun moveTilesRight(newMatrix: Array<Array<Tile?>>) {
        for (i in 0..3) {
            for (j in 3 downTo 0) {
                tileMatrix[i][j]?.let { tile ->
                    newMatrix[i][j] = tile

                    for (k in j + 1..3) {
                        if (newMatrix[i][k] == null) {
                            newMatrix[i][k] = tile

                            if (newMatrix[i][k - 1] == tile) {
                                newMatrix[i][k - 1] = null
                            }
                        } else if (newMatrix[i][k]?.getValue() == tile.getValue() && newMatrix[i][k]?.toIncrement != true) {
                            newMatrix[i][k] = tile.increment()
                            if (newMatrix[i][k - 1] == tile) {
                                newMatrix[i][k - 1] = null
                            }
                        } else {
                            break
                        }
                    }
                }
            }
        }

        for (i in 0..3) {
            for (j in 3 downTo 0) {
                val tile = tileMatrix[i][j]
                var newTile: Tile? = null
                var matrixX = 0
                var matrixY = 0

                for (a in 0..3) {
                    for (b in 0..3) {
                        if (newMatrix[a][b] == tileMatrix[i][j]) {
                            newTile = newMatrix[a][b]
                            matrixX = a
                            matrixY = b
                            break
                        }
                    }
                }

                newTile?.let {
                    movingTiles.add(tile)
                    tile?.move(matrixX, matrixY)
                }
            }
        }
    }

    private fun moveTilesLeft(newMatrix: Array<Array<Tile?>>) {
        for (i in 0..3) {
            for (j in 0..3) {
                tileMatrix[i][j]?.let { tile ->
                    newMatrix[i][j] = tile

                    for (k in j - 1 downTo 0) {
                        if (newMatrix[i][k] == null) {
                            newMatrix[i][k] = tile

                            if (newMatrix[i][k + 1] == tile) {
                                newMatrix[i][k + 1] = null
                            }
                        } else if (newMatrix[i][k]?.getValue() == tile.getValue() && newMatrix[i][k]?.toIncrement != true) {
                            newMatrix[i][k] = tile.increment()

                            if (newMatrix[i][k + 1] == tile)
                                newMatrix[i][k + 1] = null
                        } else {
                            break
                        }
                    }
                }
            }
        }

        for (i in 0..3) {
            for (j in 0..3) {
                val tile = tileMatrix[i][j]
                var newTile: Tile? = null
                var matrixX = 0
                var matrixY = 0

                for (a in 0..3) {
                    for (b in 0..3) {
                        if (newMatrix[a][b] == tileMatrix[i][j]) {
                            newTile = newMatrix[a][b]
                            matrixX = a
                            matrixY = b
                            break
                        }
                    }
                }

                newTile?.let {
                    movingTiles.add(tile)
                    tile?.move(matrixX, matrixY)
                }
            }
        }
    }

    private fun checkEndGame() {
        endGame = true

        for(i in 0..3) {
            for(j in 0..3) {
                if(tileMatrix[i][j] == null){
                    endGame = false
                    break
                }
            }
        }

        if(endGame) {
            for(i in 0..3) {
                for(j in 0..3) {
                    if(i > 0 &&
                        tileMatrix[i-1][j]?.getValue() == tileMatrix[i][j]?.getValue() ||
                        (i<3 && tileMatrix[i+1][j]?.getValue() == tileMatrix[i][j]?.getValue()) ||
                        (j>0 && tileMatrix[i][j-1]?.getValue() == tileMatrix[i][j]?.getValue()) ||
                        (j<3 && tileMatrix[i][j+1]?.getValue() == tileMatrix[i][j]?.getValue())
                    ) {
                        endGame = false
                    }
                }
            }
        }
    }

    private fun spawn() {
        if(toSpawn){
            toSpawn = false
            var tile: Tile? = null
            while(tile == null) {
                val x: Int = Random.nextInt(4)
                val y: Int = Random.nextInt(4)
                if(tileMatrix[x][y] == null) {
                    tile = Tile(screenWidth, screenHeight, standardSize, this, x, y)
                    tileMatrix[x][y] = tile
                }
            }
        }
    }

}