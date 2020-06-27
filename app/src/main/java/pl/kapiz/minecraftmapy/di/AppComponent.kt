package pl.kapiz.minecraftmapy.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pl.kapiz.minecraftmapy.App
import pl.kapiz.minecraftmapy.data.api.ApiModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        BindingModule::class,
        ViewModelFactoryModule::class,
        ApiModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: App): AppComponent
    }
}
