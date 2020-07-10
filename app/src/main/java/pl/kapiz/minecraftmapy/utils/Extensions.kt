package pl.kapiz.minecraftmapy.utils

import android.graphics.Paint
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun String.trimEnd(length: Int, end: String = "..."): String {
    return if (this.length <= length) this
    else this.substring(0, length) + end
}

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, Observer<T> {
        if (it !== null) observer.onChanged(it)
    })
}

fun TextView.setUnderlined(underlined: Boolean = true) {
    val isUnderlined = (paintFlags and Paint.UNDERLINE_TEXT_FLAG) == Paint.UNDERLINE_TEXT_FLAG
    paintFlags = when {
        underlined and !isUnderlined -> paintFlags or Paint.UNDERLINE_TEXT_FLAG
        !underlined and isUnderlined -> paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        else -> paintFlags
    }
}
