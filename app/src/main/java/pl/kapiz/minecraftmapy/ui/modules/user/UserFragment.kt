package pl.kapiz.minecraftmapy.ui.modules.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.FragmentUserBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment
import pl.kapiz.minecraftmapy.ui.modules.maps.MapAdapter
import pl.kapiz.minecraftmapy.utils.ItemLoadStateAdapter

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(R.layout.fragment_user) {

    private lateinit var mapAdapter: MapAdapter

    override val viewModel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.viewModel = viewModel
    }

    @ExperimentalPagingApi
    override fun initView() {
        mapAdapter = MapAdapter(viewModel::onMapItemClick)

        viewModel.init(args.username)

        lifecycleScope.launch {
            mapAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.onMapsLoadStateFlow(loadStates)
            }
        }

        lifecycleScope.launch {
            mapAdapter.dataRefreshFlow.collectLatest {
                b.userMapList.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            viewModel.mapFlow.collectLatest { pagingData ->
                mapAdapter.submitData(pagingData)
            }
        }

        b.userMapList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ConcatAdapter(mapAdapter.withLoadStateFooter(ItemLoadStateAdapter()))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            isNestedScrollingEnabled = false
        }
    }
}
