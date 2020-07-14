package pl.kapiz.minecraftmapy

import android.app.Application
import com.mikepenz.iconics.Iconics
import dagger.hilt.android.HiltAndroidApp
import pl.kapiz.minecraftmapy.di.StethoApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        StethoApp.initialize(this)
        Iconics.init(this)
    }
}
