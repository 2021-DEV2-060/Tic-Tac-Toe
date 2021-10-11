package com.example.tictactoe.play

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.R
import com.example.tictactoe.other.Symbol
import com.example.tictactoe.databinding.ActivityPlaygroundBinding
import com.example.tictactoe.other.addNextStep
import com.example.tictactoe.other.setHTMLText
import com.example.tictactoe.other.showToast

class PlaygroundActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPlaygroundBinding
    private lateinit var viewModel: PlaygroundViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaygroundBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return PlaygroundViewModel(
                    intent.getBooleanExtra(IS_SINGLE_PLAYER, true),
                    intent.getStringExtra(PLAYER_ONE_SYMBOL),
                    resources
                ) as T
            }
        }).get(PlaygroundViewModel::class.java)
        setData()
        setListener()
        init()
    }

    private fun setData() {
        if (Symbol.X == intent.getStringExtra(PLAYER_ONE_SYMBOL)) {
            binding.tvPlayerOneSymbol.setHTMLText(getString(R.string.player_one_o))
            binding.tvPlayerTwoSymbol.setHTMLText(getString(R.string.player_two_x))
        } else {
            binding.tvPlayerOneSymbol.setHTMLText(getString(R.string.player_one_x))
            binding.tvPlayerTwoSymbol.setHTMLText(getString(R.string.player_two_o))
        }
    }

    private fun setListener() {
        viewModel.msg.observe(this,
            { t -> t?.let { binding.tvMsg.text = it } })
        viewModel.playerWon.observe(this, { t ->
            when (t) {
                1 -> {
                    showToast(getString(R.string.player_1_won))
                }
                2 -> {
                    showToast(getString(R.string.player_2_won))
                }
                else -> {
                    showToast(getString(R.string.game_draw))
                }
            }
            Handler(Looper.getMainLooper()).postDelayed({ finish() }, FINISH_TIME)
        })
        viewModel.machineStep.observe(this, { t ->
            t?.let {
                addInput(getInputView(it), it)
            }
        })
    }

    private fun init() {
        binding.tvOne.setOnClickListener(this)
        binding.tvTwo.setOnClickListener(this)
        binding.tvThree.setOnClickListener(this)
        binding.tvFour.setOnClickListener(this)
        binding.tvFive.setOnClickListener(this)
        binding.tvSix.setOnClickListener(this)
        binding.tvSeven.setOnClickListener(this)
        binding.tvEight.setOnClickListener(this)
        binding.tvNine.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            addInput(v as TextView, Integer.parseInt(v.tag.toString()))
        }
    }

    private fun addInput(view: TextView, position: Int) {
        view.isClickable = false
        if (viewModel.isPlayerOne) {
            view.addNextStep(viewModel.playerOneSymbol)
        } else {
            view.addNextStep(viewModel.playerTwoSymbol)
        }
        viewModel.addInput(position)
    }

    private fun getInputView(position: Int): TextView {
        return when (position) {
            1 -> binding.tvOne
            2 -> binding.tvTwo
            3 -> binding.tvThree
            4 -> binding.tvFour
            5 -> binding.tvFive
            6 -> binding.tvSix
            7 -> binding.tvSeven
            8 -> binding.tvEight
            else -> binding.tvNine
        }
    }

    companion object {
        const val IS_SINGLE_PLAYER = "IS_SINGLE_PLAYER"
        const val PLAYER_ONE_SYMBOL = "PLAYER_ONE_SYMBOL"
        const val FINISH_TIME = 2000L
    }
}