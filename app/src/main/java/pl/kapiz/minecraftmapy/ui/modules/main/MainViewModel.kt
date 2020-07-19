package pl.kapiz.minecraftmapy.ui.modules.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel @ViewModelInject constructor() : ViewModel() {

    val toolbarTitle = MutableLiveData<CharSequence>()
}
