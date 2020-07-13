package pl.kapiz.minecraftmapy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal class NetworkInterceptorModule {

    @Singleton
    @Provides
    fun provideNetworkInterceptors(): NetworkInterceptors {
        return NetworkInterceptors()
    }
}
