/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-15.
 */

package pl.kapiz.minecraftmapy.ui.modules.home.row

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import pl.kapiz.minecraftmapy.databinding.HomeRowItemBinding
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

/*
 * I had to copy the whole [androidx.paging.LoadStateAdapter] class, because
 * some genius at Google thought that displaying multiple load state/placeholder
 * items is not an idea.
 */
class HomeRowLoadStateAdapter(
    private val itemCount: Int
) : RecyclerView.Adapter<BindingViewHolder<HomeRowItemBinding>>() {

    var loadState: LoadState = LoadState.NotLoading(endOfPaginationReached = false)
        set(loadState) {
            if (field != loadState) {
                val oldItem = displayLoadStateAsItem(field)
                val newItem = displayLoadStateAsItem(loadState)

                if (oldItem && !newItem) {
                    notifyItemRangeRemoved(0, itemCount)
                } else if (newItem && !oldItem) {
                    notifyItemRangeInserted(0, itemCount)
                } else if (oldItem && newItem) {
                    notifyItemRangeChanged(0, itemCount)
                }
                field = loadState
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): BindingViewHolder<HomeRowItemBinding> {
        return BindingViewHolder(HomeRowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<HomeRowItemBinding>,
        position: Int
    ) {
        val b = holder.binding
        b.isPlaceholder = true
    }

    override fun getItemViewType(position: Int): Int = getStateViewType(loadState)

    override fun getItemCount(): Int = if (displayLoadStateAsItem(loadState)) itemCount else 0

    private fun getStateViewType(loadState: LoadState): Int = 0

    private fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading || loadState is LoadState.Error
    }
}
