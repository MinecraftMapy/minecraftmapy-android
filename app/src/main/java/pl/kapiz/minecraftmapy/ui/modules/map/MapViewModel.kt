package pl.kapiz.minecraftmapy.ui.modules.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import pl.kapiz.minecraftmapy.data.model.Comment
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.data.model.User
import pl.kapiz.minecraftmapy.data.paging.MapCommentPagingSource
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.data.repository.UserRepository
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel
import pl.kapiz.minecraftmapy.ui.modules.maps.MapRowViewModel

class MapViewModel @ViewModelInject constructor(
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    val map = MutableLiveData<Map>()
    val mapFetched = MutableLiveData<Boolean>(false)
    val user = MutableLiveData<User>()
    val userFetched = MutableLiveData<Boolean>(false)

    private val mapCode get() = map.value?.code
    private val username get() = user.value?.info?.username

    val comments = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10)
    ) {
        MapCommentPagingSource(mapRepository, map.value?.code)
    }.flow.cachedIn(viewModelScope)

    val similarMapsViewModel by lazy { MapRowViewModel(
        mapRepository,
        map.value?.info?.category?.let { MapQuery.withCategory(it) } ?: MapQuery.default()
    ) }

    suspend fun loadMap(mapCode: String) {
        if (this.mapCode == mapCode)
            return
        fetchMap(mapCode)?.let {
            loadMap(it)
        }
    }

    suspend fun loadMap(map: Map) {
        if (this.map.value == map)
            return
        this.map.postValue(map)
        this.mapFetched.postValue(true)
        fetchUser(map.author.username)?.let {
            this.user.postValue(it)
            this.userFetched.postValue(true)
        }
    }

    private suspend fun fetchMap(mapCode: String): Map? {
        if (this.mapCode == mapCode)
            return null
        val map = mapRepository.getMap(mapCode)
        return map.data
    }

    private suspend fun fetchUser(username: String): User? {
        if (this.username == username)
            return null
        val user = userRepository.getUser(username)
        return user.data
    }

    fun onAuthorClicked(user: User) {
        navigate(MapFragmentDirections.actionToUserFragment(
            user = user,
            username = null
        ))
    }

    fun onCommentClicked(comment: Comment) {
        navigate(MapFragmentDirections.actionToUserFragment(
            user = null,
            username = comment.author.username
        ))
    }

    fun onMapClicked(map: Map) {
        navigate(MapFragmentDirections.actionToMapFragment(
            map = map,
            mapCode = null
        ))
    }

    fun onCategoryClicked(map: Map) {
        navigate(MapFragmentDirections.actionToMapListFragment(
            MapQuery.withCategory(map.info.category)
        ))
    }

    fun onVersionClicked(map: Map) {
        navigate(MapFragmentDirections.actionToMapListFragment(
            MapQuery.withVersion(map.info.version)
        ))
    }

    fun onStarClicked(map: Map) {

    }
}
