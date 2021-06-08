package com.android.filemaster.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.lang.ref.WeakReference

class AppPrefs private constructor(context: Context) {
    private val K_IS_DONT_SHOW_AGAIN = "is_dont_show_again"
    private val appPref: SharedPreferences =
        context.getSharedPreferences("file-master-prefs", Context.MODE_PRIVATE)

    companion object {
        var instance: AppPrefs? = null
            private set

        fun init(context: Context?) {
            val ctx: Context? = WeakReference(context).get()
            if (instance == null) {
                instance = AppPrefs(ctx!!)
            }
        }
    }

    val isDontShowAgain: Boolean
        get() = this.appPref.getBoolean(K_IS_DONT_SHOW_AGAIN, false)

    fun applyDontShowAgain() {
        appPref.edit { putBoolean(K_IS_DONT_SHOW_AGAIN, true) }
    }
}