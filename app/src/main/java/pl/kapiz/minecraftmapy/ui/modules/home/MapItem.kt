package pl.kapiz.minecraftmapy.ui.modules.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.ModelAbstractBindingItem
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.databinding.ItemMapBinding

class MapItem(map: Map) : ModelAbstractBindingItem<Map, ItemMapBinding>(map) {
    override val type: Int
        get() = R.id.map_item

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemMapBinding {
        return ItemMapBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMapBinding, payloads: List<Any>) {
        binding.apply {
            mapTitle.text = model.info.title
        }
    }
}
