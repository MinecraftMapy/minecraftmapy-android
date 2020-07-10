package pl.kapiz.minecraftmapy.ui.modules.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pl.kapiz.minecraftmapy.data.paging.MapPagingSource
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.data.pojo.User
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.data.repository.UserRepository
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel

class UserViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val mapRepository: MapRepository
) : BaseViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private lateinit var username: String

    private val _mapsLoading = MutableLiveData<Boolean>()
    val mapsLoading: LiveData<Boolean> = _mapsLoading

    private lateinit var mapPagingSource: MapPagingSource
    lateinit var mapFlow: Flow<PagingData<Map>>

    fun init(username: String) {
        if (user.value == null) {
            _loading.value = true
            this.username = username
            downloadUser()
            downloadUserMaps()
        }
    }

    fun onMapsLoadStateFlow(loadStates: CombinedLoadStates) {
        _mapsLoading.value = loadStates.refresh is LoadState.Loading
    }

    private fun downloadUser() {
        viewModelScope.launch {
            val user = userRepository.getUser(username)

            _user.value = user.data
            _loading.value = false
        }
    }

    private fun downloadUserMaps() {
        if (!this::mapFlow.isInitialized) {
            mapFlow = Pager(PagingConfig(pageSize = 20)) {
                mapPagingSource = MapPagingSource(mapRepository, username = username)
                return@Pager mapPagingSource
            }.flow.cachedIn(viewModelScope)
        }
    }

    fun onMapItemClick(map: Map) {
        _action.value = UserFragmentDirections.actionUserToMap(map)
    }
}
