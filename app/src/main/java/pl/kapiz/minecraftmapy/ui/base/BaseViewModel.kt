package pl.kapiz.minecraftmapy.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import pl.kapiz.minecraftmapy.utils.LiveEvent

@Suppress("PropertyName", "MemberVisibilityCanBePrivate")
open class BaseViewModel : ViewModel() {

    protected val _action = LiveEvent<NavDirections>()
    val action: LiveData<NavDirections> = _action

    protected val _toast = LiveEvent<String>()
    val toast: LiveData<String> = _toast

    open fun init() {}
}
