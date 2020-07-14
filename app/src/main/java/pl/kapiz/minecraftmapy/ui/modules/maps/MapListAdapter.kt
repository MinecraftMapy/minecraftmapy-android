/*
 * Copyright (c) Kacper Ziubryniewicz 2020-7-10.
 */

package pl.kapiz.minecraftmapy.ui.modules.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.paging.MapComparator
import pl.kapiz.minecraftmapy.databinding.MapListItemBinding
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class MapListAdapter(
    private val onMapItemClick: (map: Map) -> Unit
) : PagingDataAdapter<Map, BindingViewHolder<MapListItemBinding>>(MapComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<MapListItemBinding> {
        return BindingViewHolder(MapListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<MapListItemBinding>, position: Int) {
        val b = holder.binding
        getItem(position)?.also { item ->
            b.map = item
            b.mapItem.setOnClickListener { onMapItemClick(item) }
        }
    }
}
