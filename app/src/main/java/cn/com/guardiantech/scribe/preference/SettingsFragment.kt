package cn.com.guardiantech.scribe.preference

import android.os.Bundle
import android.preference.PreferenceFragment
import cn.com.guardiantech.scribe.R


/**
 * Created by dedztbh on 18-1-31.
 */
class SettingsFragment: PreferenceFragment() {
    companion object {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }
}