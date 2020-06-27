package pl.kapiz.minecraftmapy

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.kapiz.minecraftmapy.di.DaggerAppComponent

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}
