package pl.kapiz.minecraftmapy.ui.modules.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.User
import pl.kapiz.minecraftmapy.data.paging.MapPagingSource
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.data.repository.UserRepository
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel

class UserViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository,
    private val mapRepository: MapRepository
) : BaseViewModel() {

    val user = MutableLiveData<User>()
    val userFetched = MutableLiveData<Boolean>()

    private val username get() = user.value?.info?.username

    private var mapsPagingSource: MapPagingSource? = null
    val maps = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10)
    ) {
        mapsPagingSource = MapPagingSource(mapRepository, username = username)
        return@Pager mapsPagingSource!!
    }.flow.cachedIn(viewModelScope)

    suspend fun loadUser(username: String) {
        if (this.username == username)
            return
        fetchUser(username)?.let {
            loadUser(it)
        }
    }

    fun loadUser(user: User) {
        if (this.user.value == user)
            return
        this.user.postValue(user)
        this.userFetched.postValue(true)
        mapsPagingSource?.invalidate()
    }

    private suspend fun fetchUser(username: String): User? {
        if (this.username == username)
            return null
        val user = userRepository.getUser(username)
        return user.data
    }

    fun onMapClicked(map: Map) {
        navigate(UserFragmentDirections.actionToMapFragment(
            map = map,
            mapCode = null
        ))
    }
}
