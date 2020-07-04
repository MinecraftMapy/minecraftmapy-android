package pl.kapiz.minecraftmapy.ui.modules.maps

import android.view.MenuItem
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.mikepenz.fastadapter.IAdapter
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.api.Api
import pl.kapiz.minecraftmapy.data.api.ApiResponse
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel
import pl.kapiz.minecraftmapy.ui.modules.maps.filter.FilterDialogFragment
import pl.kapiz.minecraftmapy.utils.LiveEvent
import kotlin.random.Random

class MapsViewModel @ViewModelInject constructor(private val api: Api) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val mapList = mutableListOf<Map>()
    private val _maps = MediatorLiveData<List<Map>>()
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
        val page = api.getMaps(
            page = ++currentPage,
            query = searchString.value,
            sortBy = Map.SORT_BY_DISCOVER,
            seed = seed
        )
        _maps.addSource(page) {
            if (it is ApiResponse.ApiSuccessResponse) {
                mapList.addAll(it.body.data)
                _maps.value = mapList
                _loading.value = false
            }
        }
        page.refresh()
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
                _dialog.value = FilterDialogFragment.newInstance()
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
