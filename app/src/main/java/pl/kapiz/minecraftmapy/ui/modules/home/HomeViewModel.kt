/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.ui.modules.home

import androidx.hilt.lifecycle.ViewModelInject
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.data.model.enum.SortMode
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel
import pl.kapiz.minecraftmapy.ui.modules.maps.MapRowViewModel

class HomeViewModel @ViewModelInject constructor(
    val mapRepository: MapRepository
) : BaseViewModel() {

    val rows = listOf(
        MapQuery.default(),
        MapQuery.withSortBy(SortMode.NEWEST),
        MapQuery.withSortBy(SortMode.MOST_DOWNLOADED)
    ).map {
        MapRowViewModel(
            mapRepository,
            it
        )
    }.toMutableList()

    fun onMoreButtonClicked(mapQuery: MapQuery) {
        navigate(HomeFragmentDirections.actionToMapListFragment(
            mapQuery
        ))
    }

    fun onMapClicked(map: Map) {
        navigate(HomeFragmentDirections.actionToMapFragment(
            map = map,
            mapCode = null
        ))
    }
}
