package pl.kapiz.minecraftmapy.data.repository

import pl.kapiz.minecraftmapy.data.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapRepository @Inject constructor(private val api: ApiService) {

    suspend fun getMaps(
        page: Int = 1,
        query: String? = null,
        sortBy: Int? = null,
        seed: Int? = null
    ) = api.getMaps(page, query, sortBy, seed)

    suspend fun getUserMaps(username: String, page: Int = 1) = api.getUserMaps(username, page)
}
