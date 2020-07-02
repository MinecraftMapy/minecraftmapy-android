package pl.kapiz.minecraftmapy.ui.modules.map

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.utils.LiveEvent
import javax.inject.Inject

class MapViewModel @Inject constructor() : ViewModel() {

    private val _map = MutableLiveData<Map>()
    val map: LiveData<Map> = _map

    private val _action = LiveEvent<NavDirections>()
    val action: LiveData<NavDirections> = _action

    fun init(map: Map) {
        _map.value = map
    }

    @Suppress("unused_parameter")
    fun onAuthorClicked(view: View) {
        map.value?.author?.username?.let {
            _action.value = MapFragmentDirections.actionMapToUser(it)
        }
    }
}
