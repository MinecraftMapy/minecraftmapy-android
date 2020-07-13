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
import pl.kapiz.minecraftmapy.databinding.UserFragmentBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment
import pl.kapiz.minecraftmapy.ui.modules.maps.MapListAdapter
import pl.kapiz.minecraftmapy.utils.LoadStateAdapter

@AndroidEntryPoint
class UserFragment : BaseFragment<UserFragmentBinding>(R.layout.user_fragment) {

    private lateinit var mapListAdapter: MapListAdapter

    override val viewModel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.viewModel = viewModel
    }

    @ExperimentalPagingApi
    override fun initView() {
        mapListAdapter = MapListAdapter(viewModel::onMapItemClick)

        viewModel.init(args.username)

        lifecycleScope.launch {
            mapListAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.onMapsLoadStateFlow(loadStates)
            }
        }

        lifecycleScope.launch {
            mapListAdapter.dataRefreshFlow.collectLatest {
                b.userMapList.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            viewModel.mapFlow.collectLatest { pagingData ->
                mapListAdapter.submitData(pagingData)
            }
        }

        b.userMapList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ConcatAdapter(mapListAdapter.withLoadStateFooter(LoadStateAdapter()))
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            isNestedScrollingEnabled = false
        }
    }
}
