package pl.kapiz.minecraftmapy

import android.app.Application
import com.mikepenz.iconics.Iconics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Iconics.init(this)
    }
}
