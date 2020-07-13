/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-13.
 */

package pl.kapiz.minecraftmapy.ui.modules.user

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.UserItemBinding
import pl.kapiz.minecraftmapy.utils.BindingViewHolder

class UserAdapter(
    private val viewModel: UserViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<BindingViewHolder<UserItemBinding>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<UserItemBinding> {
        return BindingViewHolder(UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: BindingViewHolder<UserItemBinding>, position: Int) {
        val b = holder.binding
        val context = holder.itemView.context
        b.viewModel = viewModel
        b.lifecycleOwner = viewLifecycleOwner

        viewModel.user.observe(viewLifecycleOwner) { user ->
            b.starsTotal.setOnClickListener { Toast.makeText(context, R.string.user_stars_total_hint, Toast.LENGTH_LONG).show() }
            b.downloadsTotal.setOnClickListener { Toast.makeText(context, R.string.user_downloads_total_hint, Toast.LENGTH_LONG).show() }
            b.mapCount.setOnClickListener { Toast.makeText(context, R.string.user_map_count_hint, Toast.LENGTH_LONG).show() }
            b.commentsWritten.setOnClickListener { Toast.makeText(context, R.string.user_comments_written_hint, Toast.LENGTH_LONG).show() }
        }
    }
}
