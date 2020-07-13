package pl.kapiz.minecraftmapy.ui.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import pl.kapiz.minecraftmapy.utils.LiveEvent

@Suppress("PropertyName", "MemberVisibilityCanBePrivate")
open class BaseViewModel : ViewModel() {

    internal val navCommand = LiveEvent<NavDirections>()
    internal val toastText = LiveEvent<CharSequence>()

    internal open fun init() {}

    protected fun navigate(navDirections: NavDirections) {
        navCommand.value = navDirections
    }

    protected fun showToast(text: CharSequence) {
        toastText.value = text
    }
}
