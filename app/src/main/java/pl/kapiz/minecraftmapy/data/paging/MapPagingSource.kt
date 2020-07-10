package pl.kapiz.minecraftmapy.data.paging

import androidx.paging.PagingSource
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import retrofit2.HttpException
import java.io.IOException

class MapPagingSource(
    private val mapRepository: MapRepository,
    private val query: String? = null,
    private val sortBy: Int? = null,
    private val seed: Int? = null,
    private val username: String? = null
) : PagingSource<Int, Map>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Map> {
        return try {
            val page = params.key ?: 1

            val response = if (username == null) mapRepository.getMaps(
                page = page,
                query = query,
                sortBy = sortBy,
                seed = seed
            ) else mapRepository.getUserMaps(username, page)

            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = response.nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
