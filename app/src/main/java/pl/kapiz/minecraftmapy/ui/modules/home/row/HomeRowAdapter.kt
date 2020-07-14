/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.ui.modules.home.row

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingDataAdapter
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.data.paging.MapComparator
import pl.kapiz.minecraftmapy.data.paging.MapPagingSource
import pl.kapiz.minecraftmapy.data.repository.MapRepository
import pl.kapiz.minecraftmapy.databinding.HomeRowItemBinding
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class HomeRowAdapter(
    private val mapRepository: MapRepository,
    private val mapQuery: MapQuery,
    private val onMapClicked: (map: Map) -> Unit
) : PagingDataAdapter<Map, BindingViewHolder<HomeRowItemBinding>>(MapComparator) {

    val maps = Pager(
        PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10)
    ) {
        MapPagingSource(mapRepository, mapQuery)
    }.flow

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<HomeRowItemBinding> {
        return BindingViewHolder(HomeRowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<HomeRowItemBinding>, position: Int) {
        val b = holder.binding
        getItem(position)?.let { item ->
            b.map = item
            b.root.setOnClickListener { onMapClicked(item) }
        }
    }
}
