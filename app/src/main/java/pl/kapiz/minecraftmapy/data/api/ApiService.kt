package pl.kapiz.minecraftmapy.data.api

import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.data.pojo.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {

    @GET("map")
    suspend fun getMaps(
        @Query("page") page: Int,
        @Query("query") query: String?,
        @Query("sort_by") sortBy: Int?,
        @Query("s") seed: Int?
    ): ResponseBody<List<Map>>

    @GET("user/{username}/maps")
    suspend fun getUserMaps(
        @Path("username") username: String,
        @Query("page") page: Int
    ): ResponseBody<List<Map>>

    @GET("user/{username}")
    suspend fun getUser(@Path("username") username: String): ResponseBody<User>
}
