package pl.kapiz.minecraftmapy.data.api

import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.data.pojo.ResponseBody
import pl.kapiz.minecraftmapy.utils.RefreshLiveData
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {

    @GET("map")
    fun getMaps(@Query("page") page: Int = 1): RefreshLiveData<ApiResponse<ResponseBody<List<Map>>>>
}
