package pl.kapiz.minecraftmapy.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("android:imageUrl")
fun bindImageUrl(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
    }
}

@BindingAdapter("android:isVisible")
fun bindIsVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("android:isInvisible")
fun bindIsInvisible(view: View, isInvisible: Boolean) {
    view.isInvisible = isInvisible
}
