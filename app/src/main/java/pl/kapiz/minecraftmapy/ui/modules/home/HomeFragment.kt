/*
 * Copyright (c) Kuba Szczodrzyński 2020-7-14.
 */

package pl.kapiz.minecraftmapy.ui.modules.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.data.model.enum.SortMode
import pl.kapiz.minecraftmapy.databinding.HomeFragmentBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(R.layout.home_fragment) {

    override val viewModel: HomeViewModel by viewModels()

    override fun initView() {
        val homeAdapter = HomeAdapter(
            lifecycleScope,
            viewModel.mapRepository,
            listOf(
                MapQuery.default(),
                MapQuery.withSortBy(SortMode.NEWEST),
                MapQuery.withSortBy(SortMode.MOST_DOWNLOADED)
            ),
            onMoreButtonClicked = viewModel::onMoreButtonClicked,
            onMapClicked = viewModel::onMapClicked
        )

        b.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}
