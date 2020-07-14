/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.ui.modules.home

import androidx.hilt.lifecycle.ViewModelInject
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel

class HomeViewModel @ViewModelInject constructor(
    val mapRepository: MapRepository
) : BaseViewModel() {

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
