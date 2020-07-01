package pl.kapiz.minecraftmapy.ui.modules.newest

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mikepenz.fastadapter.IAdapter
import pl.kapiz.minecraftmapy.data.api.Api
import pl.kapiz.minecraftmapy.data.api.ApiResponse
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.ui.base.MapItem
import javax.inject.Inject

class NewestViewModel @Inject constructor(private val api: Api) : ViewModel() {

    private val mapList = mutableListOf<Map>()
    private val _maps = MediatorLiveData<List<Map>>()
    val maps: LiveData<List<Map>> = _maps

    private val _selectedMap = MutableLiveData<Map>()
    val selectedMap = _selectedMap

    private var currentPage = 0

    fun init() {
        _selectedMap.value = null
        if (currentPage == 0) downloadNextPage()
    }

    fun downloadNextPage() {
        val page = api.getMaps(++currentPage)
        _maps.addSource(page) {
            if (it is ApiResponse.ApiSuccessResponse) {
                mapList.addAll(it.body.data)
                _maps.value = mapList
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
        _selectedMap.value = item.model
        return true
    }
}
