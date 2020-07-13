package pl.kapiz.minecraftmapy.ui.modules.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.ui.base.BaseViewModel

class MapViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _map = MutableLiveData<Map>()
    val map: LiveData<Map> = _map

    fun setMap(map: Map) {
        _map.value = map
    }

    fun onAuthorClicked() {
        map.value?.author?.username?.let {
            navigate(MapFragmentDirections.actionMapToUser(user = null, username = it))
        }
    }
}
