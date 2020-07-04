package pl.kapiz.minecraftmapy.ui.modules.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel
import javax.inject.Inject

class MapViewModel @Inject constructor() : BaseViewModel() {

    private val _map = MutableLiveData<Map>()
    val map: LiveData<Map> = _map

    fun setMap(map: Map) {
        _map.value = map
    }

    fun onAuthorClicked() {
        map.value?.author?.username?.let {
            _action.value = MapFragmentDirections.actionMapToUser(it)
        }
    }
}
