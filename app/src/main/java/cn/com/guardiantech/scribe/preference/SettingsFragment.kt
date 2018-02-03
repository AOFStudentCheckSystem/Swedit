package cn.com.guardiantech.scribe.preference

import android.os.Bundle
import android.preference.PreferenceFragment
import cn.com.guardiantech.scribe.R


/**
 * Created by dedztbh on 18-1-31.
 */
class SettingsFragment : PreferenceFragment() {
    companion object {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager.sharedPreferencesName = getString(R.string.global_config_file_name)
        addPreferencesFromResource(R.xml.preferences)
    }
}