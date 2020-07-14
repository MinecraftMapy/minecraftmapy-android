package pl.kapiz.minecraftmapy.data.api

import pl.kapiz.minecraftmapy.data.api.model.ResponseBody
import pl.kapiz.minecraftmapy.data.model.Comment
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {

    @GET("recommended")
    suspend fun getRecommendedMaps(
        @Query("page") page: Int? = null
    ): ResponseBody<List<Map>>

    @GET("map")
    suspend fun getMaps(
        @Query("page") page: Int? = null,
        @Query("sort_by") sortById: Int? = null,
        @Query("s") sortSeed: Int? = null,
        @Query("category_id") categoryId: Int? = null,
        @Query("minecraft_version_id") versionId: Int? = null,
        @Query("query") queryText: String? = null
    ): ResponseBody<List<Map>>

    @GET("map/{code}")
    suspend fun getMap(
        @Path("code") mapCode: String
    ): ResponseBody<Map>

    @GET("map/{code}/comments")
    suspend fun getMapComments(
        @Path("code") mapCode: String,
        @Query("page") page: Int? = null
    ): ResponseBody<List<Comment>>

    @GET("user/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): ResponseBody<User>

    @GET("user/{username}/maps")
    suspend fun getUserMaps(
        @Path("username") username: String,
        @Query("page") page: Int? = null
    ): ResponseBody<List<Map>>
}
