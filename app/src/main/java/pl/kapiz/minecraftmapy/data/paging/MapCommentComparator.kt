/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.data.paging

import androidx.recyclerview.widget.DiffUtil
import pl.kapiz.minecraftmapy.data.model.Comment

object MapCommentComparator : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Comment, newItem: Comment) = oldItem == newItem
}
