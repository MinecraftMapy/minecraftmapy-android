package pl.kapiz.minecraftmapy.ui.modules.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MoreViewModel @Inject constructor() : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun init() {
        _text.value = "This is more Fragment"
    }
}
