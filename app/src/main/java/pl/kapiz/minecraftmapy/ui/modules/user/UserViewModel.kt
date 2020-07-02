package pl.kapiz.minecraftmapy.ui.modules.user

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
import pl.kapiz.minecraftmapy.data.pojo.User
import pl.kapiz.minecraftmapy.ui.modules.map.MapItem
import pl.kapiz.minecraftmapy.utils.LiveEvent
import javax.inject.Inject

class UserViewModel @Inject constructor(
    val api: Api
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _user = MediatorLiveData<User>()
    val user: LiveData<User> = _user

    private val _mapsLoading = MutableLiveData<Boolean>()
    val mapsLoading: LiveData<Boolean> = _mapsLoading

    private val mapList = mutableListOf<Map>()
    private val _maps = MediatorLiveData<List<Map>>()
    val maps: LiveData<List<Map>> = _maps

    private var currentMapsPage = 0

    private val _action = LiveEvent<NavDirections>()
    val action: LiveData<NavDirections> = _action

    fun init(username: String) {
        if (user.value == null) {
            _loading.value = true
            _mapsLoading.value = true
            downloadUser(username)
        }
    }

    private fun downloadUser(username: String) {
        val user = api.getUser(username)
        _user.addSource(user) {
            if (it is ApiResponse.ApiSuccessResponse) {
                _user.value = it.body.data
                _loading.value = false
                downloadNextMapsPage()
            }
        }
        user.refresh()
    }

    fun downloadNextMapsPage() {
        val page = api.getUserMaps(user.value!!.info.username, page = ++currentMapsPage)
        _maps.addSource(page) {
            if (it is ApiResponse.ApiSuccessResponse) {
                mapList.addAll(it.body.data)
                _maps.value = mapList
                _mapsLoading.value = false
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
        _action.value = UserFragmentDirections.actionUserToMap(item.model)
        return true
    }
}
