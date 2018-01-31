package cn.com.guardiantech.scribe.util

import android.text.SpannableStringBuilder


/**
 * Created by liupeiqi on 2017/4/26.
 */

fun android.widget.EditText.setString(str: String) {
    text = SpannableStringBuilder(str)
}