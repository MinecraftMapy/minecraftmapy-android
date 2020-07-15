/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-15.
 */

package pl.kapiz.minecraftmapy.ui.modules.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.paging.MapComparator
import pl.kapiz.minecraftmapy.databinding.MapRowItemBinding
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class MapRowAdapter(
    private val onMapClicked: (map: Map) -> Unit
) : PagingDataAdapter<Map, BindingViewHolder<MapRowItemBinding>>(MapComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<MapRowItemBinding> {
        return BindingViewHolder(MapRowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<MapRowItemBinding>, position: Int) {
        val b = holder.binding
        getItem(position)?.let { item ->
            b.map = item
            b.root.setOnClickListener { onMapClicked(item) }
        }
    }
}
