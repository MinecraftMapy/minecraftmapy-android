/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.paging

import androidx.paging.PagingSource
import pl.kapiz.minecraftmapy.data.model.Comment
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import retrofit2.HttpException
import java.io.IOException

class MapCommentPagingSource(
    private val mapRepository: MapRepository,
    private val mapCode: String?
) : PagingSource<Int, Comment>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comment> {
        if (mapCode == null) {
            return LoadResult.Error(Exception("MapCode is not provided."))
        }

        return try {
            val page = params.key ?: 1

            val response = mapRepository.getMapComments(
                page = page,
                mapCode = mapCode
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
