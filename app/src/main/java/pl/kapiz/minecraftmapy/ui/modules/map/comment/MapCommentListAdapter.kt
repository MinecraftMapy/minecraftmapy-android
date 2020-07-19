/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.ui.modules.map.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import pl.kapiz.minecraftmapy.data.model.Comment
import pl.kapiz.minecraftmapy.data.paging.MapCommentComparator
import pl.kapiz.minecraftmapy.databinding.MapCommentListItemBinding
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class MapCommentListAdapter(
    private val onCommentClicked: (item: Comment) -> Unit
) : PagingDataAdapter<Comment, BindingViewHolder<MapCommentListItemBinding>>(MapCommentComparator) {

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
        getItem(position)?.let { item ->
            b.comment = item
            b.root.setOnClickListener { onCommentClicked(item) }
            b.isOP = item.author.username == originalPosterUsername
        }
    }
}
