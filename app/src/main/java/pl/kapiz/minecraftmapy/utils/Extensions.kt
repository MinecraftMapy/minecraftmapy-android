package pl.kapiz.minecraftmapy.utils

import android.content.res.Resources
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.SpannedString
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

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

var TextView.drawableTop: Drawable?
    get() = compoundDrawables.getOrNull(1)
    set(value) {
        setCompoundDrawables(null, value, null, null)
    }

fun List<CharSequence?>.concat(delimiter: CharSequence? = null): CharSequence {
    if (this.isEmpty()) {
        return ""
    }

    if (this.size == 1) {
        return this[0] ?: ""
    }

    var spanned = delimiter is Spanned
    if (!spanned) {
        for (piece in this) {
            if (piece is Spanned) {
                spanned = true
                break
            }
        }
    }

    var first = true
    if (spanned) {
        val ssb = SpannableStringBuilder()
        for (piece in this) {
            if (piece == null)
                continue
            if (!first && delimiter != null)
                ssb.append(delimiter)
            first = false
            ssb.append(piece)
        }
        return SpannedString(ssb)
    } else {
        val sb = StringBuilder()
        for (piece in this) {
            if (piece == null)
                continue
            if (!first && delimiter != null)
                sb.append(delimiter)
            first = false
            sb.append(piece)
        }
        return sb.toString()
    }
}

