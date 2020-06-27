package pl.kapiz.minecraftmapy.utils

import androidx.lifecycle.MutableLiveData


open class RefreshLiveData<T>(private val refreshAction: (callback: (T) -> Unit) -> Unit) :
    MutableLiveData<T>() {

    fun refresh() = refreshAction { postValue(it) }
}
