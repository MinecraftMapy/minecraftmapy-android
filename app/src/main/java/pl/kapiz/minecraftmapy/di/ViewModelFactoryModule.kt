package pl.kapiz.minecraftmapy.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import pl.kapiz.minecraftmapy.ui.base.ViewModelFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
