package pl.kapiz.minecraftmapy.data.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Api @Inject constructor(private val apiService: ApiService) {

    fun getMaps(page: Int = 1) = apiService.getMaps(page)
}
