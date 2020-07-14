/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.di

import android.content.Context
import com.facebook.stetho.Stetho

object StethoApp {
    fun initialize(context: Context) {
        Stetho.initializeWithDefaults(context)
    }
}
