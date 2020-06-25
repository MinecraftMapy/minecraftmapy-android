package pl.kapiz.minecraftmapy.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val mapList = mutableListOf("Jedna mapka", "i kolejna")
    private val _maps = MutableLiveData<List<String>>()
    val maps: LiveData<List<String>>
        get() = _maps

    fun init() {
        _maps.value = mapList

        launch {
            delay(2000)
            mapList.add("jeszcze jedna")
            _maps.value = mapList
        }
    }
}
