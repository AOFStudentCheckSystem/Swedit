package cn.com.guardiantech.scribe.preference

import android.os.Bundle
import android.preference.PreferenceFragment
import cn.com.guardiantech.scribe.R
import cn.com.guardiantech.scribe.util.Tagable


/**
 * Created by dedztbh on 18-1-31.
 */
class SettingsFragment : PreferenceFragment(), Tagable {
    override val TAG = "SettingsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager.sharedPreferencesName = getString(R.string.global_config_file_name)
        addPreferencesFromResource(R.xml.preferences)
    }
}