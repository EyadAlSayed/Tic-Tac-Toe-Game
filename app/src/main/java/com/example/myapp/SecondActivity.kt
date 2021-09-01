package com.example.myapp

import AI
import XOGame
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import org.w3c.dom.Text
import java.text.Annotation

class SecondActivity : AppCompatActivity(), View.OnClickListener {

    private var xo: XOGame? = null
    private var ai: AI? = null
    private var txt_player_turn: TextView? = null
    private var txt_game_state: TextView? = null
    private var multiPlayerMode: Boolean? = null
    private var PLAYER_ONE: String? = null
    private var PLAYER_TWO: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        txt_player_turn = findViewById(R.id.player_turn_txt)
        txt_game_state = findViewById(R.id.game_state_txt)

        findViewById<Button>(R.id.reset_btn).setOnClickListener(onResetClick)

        multiPlayerMode = intent.getBooleanExtra("PLAYER_MODE", false)

        txt_game_state!!.isVisible = false

        INI_ImageClick()
        xo = XOGame()


        if (multiPlayerMode!!) {
            PLAYER_ONE = intent.getStringExtra("PLAYER_ONE")
            PLAYER_TWO = intent.getStringExtra("PLAYER_TWO")
        }
        if (!(multiPlayerMode!!)) {
            txt_player_turn!!.visibility = View.INVISIBLE
            ai = AI(xo!!)
        }

    }

    private val onResetClick = View.OnClickListener {
        println("Reset Game")
        txt_game_state!!.text = ""
        xo!!.resetTheBoard()
        for (i in 0.rangeTo(8)) {
            val imv = getImageByIdx(i)
            imv!!.setImageResource(0)
        }
    }

    override fun onClick(v: View?) {

        val imv = v as ImageView

        val idx = (imv.tag.toString()).toInt()

        if (multiPlayerMode!!) {
            GoToMultiPlayerMode(idx, imv)
        } else GoToOnePlayerMode(idx, imv)

    }

    private fun INI_ImageClick() {
        for (i in 0.rangeTo(8)) {
            val imv = getImageByIdx(i)
            imv!!.setOnClickListener(this)
        }
    }

    private fun getImageByIdx(idx: Int): ImageView? {

        return when (idx) {
            0 -> findViewById(R.id.imv0)
            1 -> findViewById(R.id.imv1)
            2 -> findViewById(R.id.imv2)
            3 -> findViewById(R.id.imv3)
            4 -> findViewById(R.id.imv4)
            5 -> findViewById(R.id.imv5)
            6 -> findViewById(R.id.imv6)
            7 -> findViewById(R.id.imv7)
            8 -> findViewById(R.id.imv8)
            else -> null
        }
    }

    private fun setAnimationImageProperty(imv: ImageView, time: Long) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_animation)
        animation.reset()
        imv.translationY = -1000f
        imv.animation = animation
        imv.animate().translationYBy(1000f).duration = time

    }

    private fun setAnimationTextProperty(txt: TextView) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_animation)
        animation.reset()
        txt.clearAnimation()
        txt.startAnimation(animation)
    }

    private fun GoToAI() {
        val idx = ai!!.play()

        if (idx != -1) {

            val imv = getImageByIdx(idx)
            imv!!.setImageResource(R.drawable.x)
            setAnimationImageProperty(imv, 3000)
        }

        val gameState = xo!!.getGameState()

        if (gameState.isNotEmpty()) {
            txt_game_state!!.visibility = View.VISIBLE
            txt_game_state!!.text = gameState
            setAnimationTextProperty(txt_game_state!!)
        }

    }

    private fun GoToOnePlayerMode(idx: Int, imv: ImageView) {

        println("human Choose : $idx")
        if (xo!!.playAt(idx, xo!!.huPlayer)) {
            imv.setImageResource(R.drawable.o)
            setAnimationImageProperty(imv, 1500)
            GoToAI()
        }
        val gameState = xo!!.getGameState()

        if (gameState.isNotEmpty()) {
            txt_game_state!!.visibility = View.VISIBLE
            txt_game_state!!.text = gameState
            setAnimationTextProperty(txt_game_state!!)
        }

    }

    private fun GoToMultiPlayerMode(idx: Int, imv: ImageView) {
        val playerTurn = xo!!.getPlayerTurn()

        if (xo!!.playAt(idx, playerTurn)) {
            if (playerTurn == "X") {
                imv.setImageResource(R.drawable.x)
                setAnimationImageProperty(imv, 1500)
                txt_player_turn!!.text = PLAYER_TWO + " Turn"
                setAnimationTextProperty(txt_player_turn!!)
            } else {
                imv.setImageResource(R.drawable.o)
                setAnimationImageProperty(imv, 1500)
                txt_player_turn!!.text = PLAYER_ONE + " Turn"
                setAnimationTextProperty(txt_player_turn!!)

            }
        }


        val gameState = xo!!.getGameState()

        if (gameState.isNotEmpty()) {
            println("Game State : $gameState")
            txt_game_state!!.visibility = View.VISIBLE
            txt_game_state!!.text = gameState
            setAnimationTextProperty(txt_game_state!!)
        }


    }


}