package guardiantech.com.cn.swedit.util

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.widget.EditText



/**
 * Created by liupeiqi on 2017/4/26.
 */

fun android.widget.EditText.setString(str: String) {
    text = SpannableStringBuilder(str)
}