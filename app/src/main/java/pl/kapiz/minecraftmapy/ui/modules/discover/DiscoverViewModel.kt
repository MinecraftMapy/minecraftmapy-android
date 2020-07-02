package pl.kapiz.minecraftmapy.ui.modules.discover

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.mikepenz.fastadapter.IAdapter
import pl.kapiz.minecraftmapy.data.api.Api
import pl.kapiz.minecraftmapy.data.api.ApiResponse
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.ui.modules.map.MapItem
import pl.kapiz.minecraftmapy.utils.LiveEvent
import javax.inject.Inject
import kotlin.random.Random

class DiscoverViewModel @Inject constructor(private val api: Api) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val mapList = mutableListOf<Map>()
    private val _maps = MediatorLiveData<List<Map>>()
    val maps: LiveData<List<Map>> = _maps

    private val _action = LiveEvent<NavDirections>()
    val action: LiveData<NavDirections> = _action

    private var currentPage = 0
    private val seed = Random.nextInt(0, 99999)

    fun init() {
        if (currentPage == 0) {
            _loading.value = true
            downloadNextPage()
        }
    }

    fun downloadNextPage() {
        val page = api.getMaps(page = ++currentPage, sortBy = Map.SORT_BY_DISCOVER, seed = seed)
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
        _action.value = DiscoverFragmentDirections.actionNavigationDiscoverToMap(item.model)
        return true
    }
}
