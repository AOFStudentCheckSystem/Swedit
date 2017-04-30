package guardiantech.com.cn.swedit.network

import android.content.Context
import com.android.volley.toolbox.Volley

/**
 * Created by liupeiqi on 2017/4/28.
 */

const val BASE_URL = "https://api.aofactivities.com"

object APIGlobal {
    lateinit var context: Context
    val queue by lazy { Volley.newRequestQueue(context) }
}
