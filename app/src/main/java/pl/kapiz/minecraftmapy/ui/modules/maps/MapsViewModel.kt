package pl.kapiz.minecraftmapy.ui.modules.maps

import android.view.MenuItem
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mikepenz.fastadapter.IAdapter
import kotlinx.coroutines.launch
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel
import pl.kapiz.minecraftmapy.utils.LiveEvent
import kotlin.random.Random

class MapsViewModel @ViewModelInject constructor(
    private val mapRepository: MapRepository
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val mapList = mutableListOf<Map>()
    private val _maps = MutableLiveData<List<Map>>()
    val maps: LiveData<List<Map>> = _maps

    private var currentPage = 0
    private var seed: Int? = null

    private val _searchString = LiveEvent<String>()
    val searchString: LiveData<String> = _searchString

    override fun init() {
        if (currentPage == 0) {
            _loading.value = true
            seed = Random.nextInt(0, 99999)
            downloadNextPage()
        }
    }

    private fun search() {
        currentPage = 0
        mapList.clear()
        _loading.value = true
        downloadNextPage()
    }

    fun downloadNextPage() {
        viewModelScope.launch {
            val page = mapRepository.getMaps(
                page = ++currentPage,
                query = searchString.value,
                sortBy = Map.SORT_BY_DISCOVER,
                seed = seed
            )

            mapList.addAll(page.data)
            _maps.value = mapList
            _loading.value = false
        }
    }

    @Suppress("unused_parameter")
    fun onItemClicked(
        view: View?,
        adapter: IAdapter<MapItem>,
        item: MapItem,
        position: Int
    ): Boolean {
        _action.value = MapsFragmentDirections.actionNavigationMapsToMap(item.model)
        return true
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
        if (_searchString.value != query) {
            _searchString.value = query
            search()
            return true
        }
        return false
    }
}
