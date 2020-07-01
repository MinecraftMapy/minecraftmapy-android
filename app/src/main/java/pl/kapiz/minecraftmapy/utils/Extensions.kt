package pl.kapiz.minecraftmapy.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener

fun String.trimEnd(length: Int, end: String = "..."): String {
    return if (this.length <= length) this
    else this.substring(0, length) + end
}

inline fun RecyclerView.setEndlessScrollListener(
    visibleThreshold: Int,
    crossinline callback: (currentPage: Int) -> Unit
) {
    layoutManager?.let {
        addOnScrollListener(object : EndlessRecyclerOnScrollListener(it, visibleThreshold) {
            override fun onLoadMore(currentPage: Int) {
                callback(currentPage)
            }
        })
    }
}

fun <T> LiveData<T>.observeNonNull(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, Observer<T> {
        if (it !== null) observer.onChanged(it)
    })
}
