/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.ui.modules.map

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.kapiz.minecraftmapy.databinding.MapItemBinding
import pl.kapiz.minecraftmapy.ui.modules.maps.MapRowAdapter
import pl.kapiz.minecraftmapy.ui.modules.maps.MapRowLoadStateAdapter
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class MapAdapter(
    private val viewModel: MapViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<BindingViewHolder<MapItemBinding>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<MapItemBinding> {
        return BindingViewHolder(MapItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: BindingViewHolder<MapItemBinding>, position: Int) {
        val b = holder.binding
        val context = holder.itemView.context
        b.viewModel = viewModel
        b.lifecycleOwner = viewLifecycleOwner

        val mapRowAdapter = MapRowAdapter(viewModel::onMapClicked)
        val mapRowLoadStateAdapter = MapRowLoadStateAdapter(4)

        b.similarMaps.adapter = ConcatAdapter(
            mapRowLoadStateAdapter,
            mapRowAdapter.apply {
                addLoadStateListener { loadStates ->
                    mapRowLoadStateAdapter.loadState = loadStates.refresh
                }
            }
        )

        viewModel.map.observe(viewLifecycleOwner) { map ->
            b.downloadCount.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(map.info.downloadUrl)
                context.startActivity(i)
            }

            b.mapCarousel.apply {
                setImageListener { position, imageView ->
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    imageView.load(map.images[position])
                }
                pageCount = map.images.size
            }

            val rowViewModel = viewModel.similarMapsViewModel
            rowViewModel.viewModelScope.launch {
                rowViewModel.maps.collectLatest { pagingData ->
                    mapRowAdapter.submitData(pagingData)
                }
            }
        }
    }
}
