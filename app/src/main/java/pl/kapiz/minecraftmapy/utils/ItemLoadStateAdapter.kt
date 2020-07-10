package pl.kapiz.minecraftmapy.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import pl.kapiz.minecraftmapy.databinding.ItemLoadStateBinding

class ItemLoadStateAdapter : LoadStateAdapter<BindingViewHolder<ItemLoadStateBinding>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): BindingViewHolder<ItemLoadStateBinding> {
        val binding =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemLoadStateBinding>,
        loadState: LoadState
    ) {
        holder.binding.loadState = loadState
    }
}
