package pl.kapiz.minecraftmapy.data.api

import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.data.pojo.ResponseBody
import pl.kapiz.minecraftmapy.data.pojo.User
import pl.kapiz.minecraftmapy.utils.RefreshLiveData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {

    @GET("map")
    fun getMaps(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: Int?,
        @Query("s") seed: Int?
    ): RefreshLiveData<ApiResponse<ResponseBody<List<Map>>>>

    @GET("user/{username}/maps")
    fun getUserMaps(
        @Path("username") username: String,
        @Query("page") page: Int
    ): RefreshLiveData<ApiResponse<ResponseBody<List<Map>>>>

    @GET("user/{username}")
    fun getUser(@Path("username") username: String): RefreshLiveData<ApiResponse<ResponseBody<User>>>
}
