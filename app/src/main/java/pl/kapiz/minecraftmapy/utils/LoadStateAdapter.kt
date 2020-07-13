package pl.kapiz.minecraftmapy.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import pl.kapiz.minecraftmapy.databinding.LoadStateItemBinding

class LoadStateAdapter : LoadStateAdapter<BindingViewHolder<LoadStateItemBinding>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BindingViewHolder<LoadStateItemBinding> {
        val binding =
            LoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<LoadStateItemBinding>,
        loadState: LoadState
    ) {
        holder.binding.loadState = loadState
    }
}
