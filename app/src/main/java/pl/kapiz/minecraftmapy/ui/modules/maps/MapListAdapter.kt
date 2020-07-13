package pl.kapiz.minecraftmapy.ui.modules.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import pl.kapiz.minecraftmapy.data.model.Map
import pl.kapiz.minecraftmapy.databinding.MapListItemBinding
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class MapListAdapter(
    private val onMapItemClick: (map: Map) -> Unit
) : PagingDataAdapter<Map, BindingViewHolder<MapListItemBinding>>(MapComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<MapListItemBinding> {
        val binding = MapListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<MapListItemBinding>, position: Int) {
        getItem(position)?.also { item ->
            with(holder.binding) {
                map = item
                mapItem.setOnClickListener { onMapItemClick(item) }
            }
        }
    }

    object MapComparator : DiffUtil.ItemCallback<Map>() {
        override fun areItemsTheSame(oldItem: Map, newItem: Map) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Map, newItem: Map) = oldItem == newItem
    }
}
