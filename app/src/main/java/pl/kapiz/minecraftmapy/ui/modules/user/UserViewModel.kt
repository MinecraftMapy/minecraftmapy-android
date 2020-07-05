package pl.kapiz.minecraftmapy.ui.modules.user

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mikepenz.fastadapter.IAdapter
import kotlinx.coroutines.launch
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.data.pojo.User
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.data.repository.UserRepository
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel
import pl.kapiz.minecraftmapy.ui.modules.maps.MapItem

class UserViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val mapRepository: MapRepository
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _mapsLoading = MutableLiveData<Boolean>()
    val mapsLoading: LiveData<Boolean> = _mapsLoading

    private val mapList = mutableListOf<Map>()
    private val _maps = MutableLiveData<List<Map>>()
    val maps: LiveData<List<Map>> = _maps

    private var currentMapsPage = 0
    private lateinit var username: String

    fun init(username: String) {
        if (user.value == null) {
            _loading.value = true
            _mapsLoading.value = true
            this.username = username
            downloadUser()
        }
    }

    private fun downloadUser() {
        viewModelScope.launch {
            val user = userRepository.getUser(username)

            _user.value = user.data
            _loading.value = false
            downloadNextMapsPage()
        }
    }

    fun downloadNextMapsPage() {
        viewModelScope.launch {
            val page = mapRepository.getUserMaps(username, page = ++currentMapsPage)

            mapList.addAll(page.data)
            _maps.value = mapList
            _mapsLoading.value = false
        }
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
