package pl.kapiz.minecraftmapy.ui.modules.maps

import android.view.MenuItem
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.data.paging.MapPagingSource
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel

class MapListViewModel @ViewModelInject constructor(
    private val mapRepository: MapRepository
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var mapQuery = MapQuery.default()

    private var mapPagingSource: MapPagingSource? = null
    val mapFlow = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10)
    ) {
        mapPagingSource = MapPagingSource(mapRepository, mapQuery)
        return@Pager mapPagingSource!!
    }.flow.cachedIn(viewModelScope)

    fun onLoadStateFlow(loadStates: CombinedLoadStates) {
        _loading.value = loadStates.refresh is LoadState.Loading
    }

    fun submitMapQuery(mapQuery: MapQuery) {
        if (this.mapQuery == mapQuery)
            return
        this.mapQuery = mapQuery
        this.mapPagingSource?.invalidate()
    }

    fun onMapClicked(map: Map) {
        navigate(MapListFragmentDirections.actionNavigationMapsToMap(
            map = map,
            mapCode = null
        ))
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_maps_search -> true
            R.id.menu_maps_filter -> {
                navigate(MapListFragmentDirections.actionNavigationMapsToFilterDialog())
                true
            }
            else -> false
        }
    }

    fun onQueryTextSubmit(queryText: String?): Boolean {
        if (mapQuery.queryText != queryText) {
            mapQuery = if (queryText == null)
                MapQuery.default()
            else
                MapQuery.withText(queryText)
            submitMapQuery(mapQuery)
            return true
        }
        return false
    }

    fun onSearchCollapse(): Boolean {
        onQueryTextSubmit(null)
        return true
    }
}
