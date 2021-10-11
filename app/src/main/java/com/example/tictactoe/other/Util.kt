package com.example.tictactoe.other

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tictactoe.R

fun AppCompatActivity.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun TextView.addNextStep(s: String?) {
    text = s
    if (Symbol.X == s) {
        setTextColor(ContextCompat.getColor(context, R.color.red))
    } else {
        setTextColor(ContextCompat.getColor(context, R.color.blue))
    }
}
