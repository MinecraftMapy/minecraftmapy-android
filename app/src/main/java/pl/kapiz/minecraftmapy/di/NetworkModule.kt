package pl.kapiz.minecraftmapy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkInterceptorModule::class])
@InstallIn(ApplicationComponent::class)
internal class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptors: NetworkInterceptors) = OkHttpClient.Builder()
        .apply {
            interceptors.list.forEach {
                addNetworkInterceptor(it)
            }
        }
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl("https://minecraftmapy.pl/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
