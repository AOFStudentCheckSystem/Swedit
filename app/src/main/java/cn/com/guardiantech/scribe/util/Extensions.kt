package cn.com.guardiantech.scribe.util

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.widget.EditText



/**
 * Created by liupeiqi on 2017/4/26.
 */

fun android.widget.EditText.setString(str: String) {
    text = android.text.SpannableStringBuilder(str)
}

fun parseEventStatus(num: Int): String {
    when (num) {
        0 -> return "Scheduled"
        1 -> return "Boarding"
        2 -> return "Complete"
        else -> return "Unknown"
    }
}