package com.example.tictactoe.play

import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.R
import com.example.tictactoe.other.Symbol

class PlaygroundViewModel(
    private val isSinglePlayer: Boolean = true,
    val playerOneSymbol: String? = Symbol.X,
    private val resources: Resources
) :
    ViewModel() {
    var playerTwoSymbol = Symbol.O
    var msg = MutableLiveData<String>()
    var playerWon = MutableLiveData<Int>()
    var machineStep = MutableLiveData<Int>()
    private val winningCombinations = HashMap<Int, ArrayList<Int>>()


    var isPlayerOne = true
    private var playerOneInput = ArrayList<Int>(5)
    private var playerTwoInput = ArrayList<Int>(5)
    private var inputs = ArrayList<Int>(9)

    init {
        playerTwoSymbol = if (playerOneSymbol == Symbol.X) {
            Symbol.O
        } else {
            Symbol.X
        }
        winningCombinations.apply {
            put(0, arrayListOf(1, 2, 3))
            put(1, arrayListOf(4, 5, 6))
            put(2, arrayListOf(7, 8, 9))
            put(3, arrayListOf(1, 4, 7))
            put(4, arrayListOf(2, 5, 8))
            put(5, arrayListOf(3, 6, 9))
            put(6, arrayListOf(1, 5, 9))
            put(7, arrayListOf(3, 5, 7))
        }
        for (i in 1..9) {
            inputs.add(i)
        }
        if (playerOneSymbol == Symbol.O) {
            isPlayerOne = false
            msg.value = resources.getString(R.string.player_2_turn)
        } else {
            msg.value = resources.getString(R.string.player_1_turn)
        }
        if (isSinglePlayer && playerTwoSymbol == Symbol.X) {
            playNextStep()
        }
    }

    fun addInput(position: Int) {
        inputs.remove(position)
        if (isPlayerOne) {
            playerOneInput.add(position)
        } else {
            playerTwoInput.add(position)
        }
        if (checkResult()) {
            return
        }

        if (playerOneInput.size + playerTwoInput.size == 9) {
            playerWon.value = 0
            return
        }
        isPlayerOne = !isPlayerOne

        if (isPlayerOne) {
            msg.value = resources.getString(R.string.player_1_turn)
        } else {
            msg.value = resources.getString(R.string.player_2_turn)
        }


        if (isSinglePlayer && !isPlayerOne) {
            msg.value = resources.getString(R.string.machine_turn)
            Handler(Looper.getMainLooper()).postDelayed({ playNextStep() }, 300)
        }
    }

    private fun playNextStep() {
        if (playerOneInput.size < 2) {
            inputs.shuffle()
            addInputAndPass(inputs[0])
            return
        } else {
            var o: Int
            var input: Int
            for ((_, v) in winningCombinations) {
                o = 0
                input = 0
                for (i in v) {
                    if (playerTwoInput.contains(i)) {
                        o++
                    } else {
                        input = i
                    }
                }
                if (o == 2 && inputs.contains(input)) {
                    addInputAndPass(input)
                    return
                }
            }
        }
        var o: Int
        var input: Int
        for ((_, v) in winningCombinations) {
            o = 0
            input = 0
            for (i in v) {
                if (playerOneInput.contains(i)) {
                    o++
                } else {
                    input = i
                }
            }
            if (o == 2 && inputs.contains(input)) {
                addInputAndPass(input)
                return
            }
        }
        inputs.shuffle()
        addInputAndPass(inputs[0])
    }

    private fun addInputAndPass(position: Int) {
        machineStep.value = position
        msg.value = resources.getString(R.string.your_turn)
    }

    private fun checkResult(): Boolean {
        if (isPlayerOne) {
            if (isWin(playerOneInput)) {
                playerWon.value = 1
                return true
            }
        } else {
            if (isWin(playerTwoInput)) {
                playerWon.value = 2
                return true
            }
        }
        return false
    }

    private fun isWin(list: ArrayList<Int>): Boolean {
        if (list.size < 3) {
            return false
        }
        for ((_, v) in winningCombinations) {
            if (list.containsAll(v)) {
                return true
            }
        }
        return false
    }
}