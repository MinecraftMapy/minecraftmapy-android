/*
 * Copyright (c) Kacper Ziubryniewicz 2020-7-10.
 */

package pl.kapiz.minecraftmapy.data.paging

import androidx.recyclerview.widget.DiffUtil
import pl.kapiz.minecraftmapy.data.model.Map

object MapComparator : DiffUtil.ItemCallback<Map>() {
    override fun areItemsTheSame(oldItem: Map, newItem: Map) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Map, newItem: Map) = oldItem == newItem
}
