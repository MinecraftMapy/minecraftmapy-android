package pl.kapiz.minecraftmapy.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

@BindingAdapter("android:imageUrl")
fun bindImageUrl(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
    }
}

@BindingAdapter("android:imageUrlCircled")
fun bindImageUrlCircled(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
        transformations(CircleCropTransformation())
    }
}

@BindingAdapter("android:imageUrlRoundedTop")
fun bindImageUrlRoundedTop(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
        transformations(RoundedCornersTransformation(8.dp.toFloat(), 8.dp.toFloat(), 0f, 0f))
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
