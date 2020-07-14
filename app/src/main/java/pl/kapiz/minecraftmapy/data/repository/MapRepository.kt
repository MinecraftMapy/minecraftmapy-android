package pl.kapiz.minecraftmapy.data.repository

import pl.kapiz.minecraftmapy.data.api.ApiService
import pl.kapiz.minecraftmapy.data.api.model.ResponseBody
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.MapQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapRepository @Inject constructor(private val api: ApiService) {

    suspend fun getMaps(
        page: Int = 1,
        mapQuery: MapQuery?,
        recommended: Boolean,
        username: String?
    ): ResponseBody<List<Map>> {
        if (recommended) {
            return api.getRecommendedMaps(page)
        }
        if (username != null) {
            return api.getUserMaps(username, page)
        }
        if (mapQuery == null) {
            return ResponseBody(listOf(), null)
        }
        return api.getMaps(
            page = page,
            sortById = mapQuery.sortBy?.id,
            sortSeed = mapQuery.sortSeed,
            categoryId = mapQuery.category?.id,
            versionId = mapQuery.version?.id,
            queryText = mapQuery.queryText
        )
    }

    suspend fun getMap(mapCode: String) = api.getMap(mapCode)

    suspend fun getMapComments(page: Int = 1, mapCode: String) = api.getMapComments(
        mapCode = mapCode,
        page = page
    )
}
