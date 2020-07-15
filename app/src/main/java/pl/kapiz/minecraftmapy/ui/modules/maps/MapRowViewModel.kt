/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-15.
 */

package pl.kapiz.minecraftmapy.ui.modules.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.data.paging.MapPagingSource
import pl.kapiz.minecraftmapy.data.repository.MapRepository

class MapRowViewModel(
    private val mapRepository: MapRepository,
    val mapQuery: MapQuery
) : ViewModel() {

    val maps = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10)
    ) {
        MapPagingSource(mapRepository, mapQuery)
    }.flow.cachedIn(viewModelScope)
}
