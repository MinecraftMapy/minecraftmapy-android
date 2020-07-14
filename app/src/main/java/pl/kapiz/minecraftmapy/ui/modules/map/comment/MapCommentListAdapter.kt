/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.ui.modules.map.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import pl.kapiz.minecraftmapy.data.model.Comment
import pl.kapiz.minecraftmapy.databinding.MapCommentListItemBinding
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class MapCommentListAdapter(
        diffCallback: DiffUtil.ItemCallback<Comment>,
        private val onItemClick: (item: Comment) -> Unit
) : PagingDataAdapter<Comment, BindingViewHolder<MapCommentListItemBinding>>(diffCallback) {

    var originalPosterUsername: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<MapCommentListItemBinding> {
        return BindingViewHolder(MapCommentListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: BindingViewHolder<MapCommentListItemBinding>, position: Int) {
        val b = holder.binding
        val item = getItem(position)
        b.comment = item
        b.isOP = item?.author?.username == originalPosterUsername
        b.root.setOnClickListener { item?.let(onItemClick) }
    }
}
