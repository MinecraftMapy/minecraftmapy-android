package pl.kapiz.minecraftmapy.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.kapiz.minecraftmapy.ui.modules.main.MainActivity
import pl.kapiz.minecraftmapy.ui.modules.main.MainModule

@Module
internal abstract class BindingModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
