package cn.com.guardiantech.scribe.util


/**
 * Created by liupeiqi on 2017/4/26.
 */

fun android.widget.EditText.setString(str: String) {
    text = android.text.SpannableStringBuilder(str)
}

fun parseEventStatus(num: Int): String =
        when (num) {
            0 -> "Scheduled"
            1 -> "Boarding"
            2 -> "Complete"
            else -> "Unknown"
        }