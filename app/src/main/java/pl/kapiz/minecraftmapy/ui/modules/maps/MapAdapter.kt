package pl.kapiz.minecraftmapy.ui.modules.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.databinding.ItemMapBinding
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class MapAdapter(
    private val onMapItemClick: (map: Map) -> Unit
) : PagingDataAdapter<Map, BindingViewHolder<ItemMapBinding>>(MapComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ItemMapBinding> {
        val binding = ItemMapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ItemMapBinding>, position: Int) {
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
