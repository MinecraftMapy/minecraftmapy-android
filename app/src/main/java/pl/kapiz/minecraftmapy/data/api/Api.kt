package pl.kapiz.minecraftmapy.data.api

import pl.kapiz.minecraftmapy.data.pojo.Map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Api @Inject constructor(private val apiService: ApiService) {

    fun getMaps(
        page: Int = 1,
        query: String? = null,
        sortBy: Int? = Map.SORT_BY_NEWEST,
        seed: Int? = null
    ) =
        apiService.getMaps(
            page = page,
            query = query,
            sortBy = sortBy,
            seed = seed
        )

    fun getUserMaps(username: String, page: Int = 1) = apiService.getUserMaps(
        username = username,
        page = page
    )

    fun getUser(username: String) = apiService.getUser(username)
}
