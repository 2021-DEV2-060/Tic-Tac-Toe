package com.example.tictactoe.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoe.R
import com.example.tictactoe.other.Symbol
import com.example.tictactoe.databinding.ActivityMainBinding
import com.example.tictactoe.other.setHTMLText
import com.example.tictactoe.play.PlaygroundActivity

class MainActivity : AppCompatActivity() {
    private var singlePlayer = true
    private lateinit var binding: ActivityMainBinding
    private var playerOneSymbolX = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.btnPlayerOne.isChecked = true
        binding.btnStart.setOnClickListener {
            val intent = Intent(this@MainActivity, PlaygroundActivity::class.java)
            intent.putExtra(PlaygroundActivity.IS_SINGLE_PLAYER, singlePlayer)
            intent.putExtra(
                PlaygroundActivity.PLAYER_ONE_SYMBOL, if (playerOneSymbolX) {
                    Symbol.X
                } else {
                    Symbol.O
                }
            )
            startActivity(intent)
        }
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            singlePlayer = !singlePlayer
        }
        binding.tvPlayerOneSymbol.setOnClickListener { toggle() }
        binding.tvPlayerTwoSymbol.setOnClickListener { toggle() }
    }

    private fun toggle() {
        if (playerOneSymbolX) {
            binding.tvPlayerOneSymbol.setHTMLText(getString(R.string.player_one_o))
            binding.tvPlayerTwoSymbol.setHTMLText(getString(R.string.player_two_x))
        } else {
            binding.tvPlayerOneSymbol.setHTMLText(getString(R.string.player_one_x))
            binding.tvPlayerTwoSymbol.setHTMLText(getString(R.string.player_two_o))
        }
        playerOneSymbolX = !playerOneSymbolX
    }
}