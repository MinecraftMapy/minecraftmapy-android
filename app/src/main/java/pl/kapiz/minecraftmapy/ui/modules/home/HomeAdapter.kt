/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.ui.modules.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.kapiz.minecraftmapy.databinding.HomeRowBinding
import pl.kapiz.minecraftmapy.ui.modules.maps.MapRowAdapter
import pl.kapiz.minecraftmapy.ui.modules.maps.MapRowLoadStateAdapter
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class HomeAdapter(
    private val viewModel: HomeViewModel,
    private val lifecycleScope: CoroutineScope
) : RecyclerView.Adapter<BindingViewHolder<HomeRowBinding>>() {

    override fun getItemCount() = viewModel.rows.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<HomeRowBinding> {
        return BindingViewHolder(HomeRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<HomeRowBinding>, position: Int) {
        val b = holder.binding
        val rowViewModel = viewModel.rows[position]
        b.viewModel = rowViewModel

        val mapRowAdapter =
            MapRowAdapter(viewModel::onMapClicked)
        val loadStateAdapter =
            MapRowLoadStateAdapter(4)

        b.row.adapter = ConcatAdapter(
            loadStateAdapter,
            mapRowAdapter.apply {
                addLoadStateListener { loadStates ->
                    loadStateAdapter.loadState = loadStates.refresh
                }
            }
        )

        lifecycleScope.launch {
            rowViewModel.maps.collectLatest { pagingData ->
                mapRowAdapter.submitData(pagingData)
            }
        }

        b.moreButton.setOnClickListener { viewModel.onMoreButtonClicked(rowViewModel.mapQuery) }
    }
}
