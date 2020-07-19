package pl.kapiz.minecraftmapy.data.paging

import androidx.paging.PagingSource
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import retrofit2.HttpException
import java.io.IOException

class MapPagingSource(
    private val mapRepository: MapRepository,
    private val mapQuery: MapQuery? = null,
    private val recommended: Boolean = false,
    private val username: String? = null
) : PagingSource<Int, Map>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Map> {
        return try {
            val page = params.key ?: 1

            val response = mapRepository.getMaps(
                page = page,
                mapQuery = mapQuery,
                recommended = recommended,
                username = username
            )

            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = response.paging?.nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
