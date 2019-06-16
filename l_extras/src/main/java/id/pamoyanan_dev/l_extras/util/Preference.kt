package id.pamoyanan_dev.l_extras.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object Preference {
    private const val KEY_NAME: String = "MyPreference"
    lateinit var mSharedPreference: SharedPreferences

    /**
     * Init shared preference
     */
    @Deprecated("Use injection and pass pref to repository")
    fun getSp(mContext: Context): SharedPreferences {
        mSharedPreference = PreferenceManager.getDefaultSharedPreferences(mContext)
        mSharedPreference = mContext.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
        return mSharedPreference
    }

    /**
     * Save value preference
     */
    @Deprecated("Use injection and pass pref to repository",
            ReplaceWith("getSp(mContext).edit().putString(key, value).apply()",
                    "id.co.gits.gitsdriver.utils.GitsHelper.Preference.getSp"))
    fun savePref(mContext: Context, key: String, value: String) {
        getSp(mContext).edit().putString(key, value).apply()
    }

    @Deprecated("Use injection and pass pref to repository",
            ReplaceWith("getSp(mContext).edit().putString(key, value).apply()",
                    "id.co.gits.gitsdriver.utils.GitsHelper.Preference.getSp"))
    fun savePref(mContext: Context, key: String, value: Int) {
        getSp(mContext).edit().putInt(key, value).apply()
    }

    @Deprecated("Use injection and pass pref to repository",
            ReplaceWith("getSp(mContext).edit().putString(key, value).apply()",
                    "id.co.gits.gitsdriver.utils.GitsHelper.Preference.getSp"))
    fun savePref(mContext: Context, key: String, value: Float) {
        getSp(mContext).edit().putFloat(key, value).apply()
    }

    @Deprecated("Use injection and pass pref to repository",
            ReplaceWith("getSp(mContext).edit().putString(key, value).apply()",
                    "id.co.gits.gitsdriver.utils.GitsHelper.Preference.getSp"))
    fun savePref(mContext: Context, key: String, value: Boolean) {
        getSp(mContext).edit().putBoolean(key, value).apply()
    }

    /**
     * Get value preference
     */
    fun getPref(mContext: Context, key: String): String {
        val pref = getSp(mContext).getString(key, "")
        return pref
    }

    fun getPrefInt(mContext: Context, key: String): Int {
        val pref = getSp(mContext).getInt(key, 0)
        return pref
    }

    fun getPrefFloat(mContext: Context, key: String): Float? {
        val pref = getSp(mContext).getFloat(key, 0.toFloat())
        return pref
    }

    fun getPrefBoolean(mContext: Context, key: String): Boolean {
        val pref = getSp(mContext).getBoolean(key, false)
        return pref
    }

    /**
     * Remove value preference
     */
    @Deprecated("Use injection and pass pref to repository",
            ReplaceWith("getSp(mContext).edit().remove(key)",
                    "id.gits.antaraqua.util.helper.PrefHelper.getSp"))
    fun removePref(mContext: Context, key: String) {
        getSp(mContext).edit().remove(key).apply()
    }
}