package pl.kapiz.minecraftmapy.ui.modules.maps

import android.view.MenuItem
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.paging.MapPagingSource
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel
import kotlin.random.Random

class MapsViewModel @ViewModelInject constructor(
    private val mapRepository: MapRepository
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var seed: Int? = Random.nextInt(0, 99999)
    private var searchString: String? = null

    private lateinit var mapPagingSource: MapPagingSource
    val mapFlow = Pager(PagingConfig(pageSize = 20)) {
        mapPagingSource = MapPagingSource(mapRepository, searchString, Map.SORT_BY_DISCOVER, seed)
        return@Pager mapPagingSource
    }.flow.cachedIn(viewModelScope)

    fun onLoadStateFlow(loadStates: CombinedLoadStates) {
        _loading.value = loadStates.refresh is LoadState.Loading
    }

    fun onMapItemClick(map: Map) {
        _action.value = MapsFragmentDirections.actionNavigationMapsToMap(map)
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_maps_search -> true
            R.id.menu_maps_filter -> {
                _action.value = MapsFragmentDirections.actionNavigationMapsToFilterDialog()
                true
            }
            else -> false
        }
    }

    fun onQueryTextSubmit(query: String?): Boolean {
        if (searchString != query) {
            searchString = query
            mapPagingSource.invalidate()
            return true
        }
        return false
    }

    fun onSearchCollapse(): Boolean {
        onQueryTextSubmit(null)
        return true
    }
}
