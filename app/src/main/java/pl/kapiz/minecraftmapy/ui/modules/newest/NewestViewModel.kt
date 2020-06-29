package pl.kapiz.minecraftmapy.ui.modules.newest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import pl.kapiz.minecraftmapy.data.api.Api
import pl.kapiz.minecraftmapy.data.api.ApiResponse
import pl.kapiz.minecraftmapy.data.pojo.Map
import javax.inject.Inject

class NewestViewModel @Inject constructor(private val api: Api) : ViewModel() {

    private val mapList = mutableListOf<Map>()
    private val _maps = MediatorLiveData<List<Map>>()
    val maps: LiveData<List<Map>> = _maps

    private var currentPage = 0

    fun init() {
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
}
