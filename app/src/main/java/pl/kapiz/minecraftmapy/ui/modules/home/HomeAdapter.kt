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
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.databinding.HomeRowBinding
import pl.kapiz.minecraftmapy.ui.modules.home.row.HomeRowAdapter
import pl.kapiz.minecraftmapy.ui.modules.home.row.HomeRowLoadStateAdapter
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class HomeAdapter(
    private val lifecycleScope: CoroutineScope,
    private val mapRepository: MapRepository,
    private val items: List<MapQuery>,
    private val onMoreButtonClicked: (mapQuery: MapQuery) -> Unit,
    private val onMapClicked: (map: Map) -> Unit
) : RecyclerView.Adapter<BindingViewHolder<HomeRowBinding>>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<HomeRowBinding> {
        return BindingViewHolder(HomeRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<HomeRowBinding>, position: Int) {
        val b = holder.binding
        items[position].let { item ->
            b.mapQuery = item

            val adapter = HomeRowAdapter(mapRepository, item, onMapClicked)
            b.row.adapter = adapter.run {
                val header = HomeRowLoadStateAdapter(4)
                addLoadStateListener { loadStates ->
                    header.loadState = loadStates.refresh
                }
                return@run ConcatAdapter(header, this)
            }
            lifecycleScope.launch {
                adapter.maps.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }

            b.moreButton.setOnClickListener { onMoreButtonClicked(item) }
        }
    }
}
