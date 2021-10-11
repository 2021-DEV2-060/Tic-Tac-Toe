package com.example.tictactoe.other

import android.os.Build
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tictactoe.R
import org.w3c.dom.Text

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

fun TextView.setHTMLText(htmlText: String?){
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(htmlText)
    }
}