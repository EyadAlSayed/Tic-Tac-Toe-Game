package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private var multiPlayerMode: Boolean? = false
    private var ed_playerOne: EditText? = null
    private var ed_playerTwo: EditText? = null
    private var go_btn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        go_btn = findViewById<Button>(R.id.go_btn)
        ed_playerOne = findViewById<EditText>(R.id.player_one)
        ed_playerTwo = findViewById<EditText>(R.id.player_two)
        findViewById<Switch>(R.id.player_mode_switch).setOnCheckedChangeListener(onChangePlayerMode)
        go_btn!!.setOnClickListener(onGoPressed)
        ed_playerOne!!.addTextChangedListener(onTextChange)
        ed_playerTwo!!.addTextChangedListener(onTextChange)

    }

    private val onTextChange = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (multiPlayerMode!!) {
                go_btn!!.isEnabled = (ed_playerOne!!.text.isNotEmpty() && ed_playerTwo!!.text.isNotEmpty())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

    private val onChangePlayerMode = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        multiPlayerMode = isChecked
        go_btn!!.isEnabled = !isChecked
        ed_playerOne!!.isEnabled = multiPlayerMode!!
        ed_playerTwo!!.isEnabled = multiPlayerMode!!
    }
    private var onGoPressed = View.OnClickListener {
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("PLAYER_MODE", multiPlayerMode!!)
            putExtra("PLAYER_ONE", ed_playerOne!!.text.toString())
            putExtra("PLAYER_TWO", ed_playerTwo!!.text.toString())
        }
        startActivity(intent)
    }


}


