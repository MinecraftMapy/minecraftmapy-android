package pl.kapiz.minecraftmapy.ui.modules.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import pl.kapiz.minecraftmapy.data.api.Api
import pl.kapiz.minecraftmapy.data.api.ApiResponse
import pl.kapiz.minecraftmapy.data.pojo.Map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeViewModel @Inject constructor(
    private val api: Api
) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

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
