package pl.kapiz.minecraftmapy.ui.modules.map

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.MapFragmentBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment
import pl.kapiz.minecraftmapy.ui.modules.map.comment.MapCommentListAdapter
import pl.kapiz.minecraftmapy.utils.LoadStateAdapter

@AndroidEntryPoint
class MapFragment : BaseFragment<MapFragmentBinding>(R.layout.map_fragment) {

    override val viewModel: MapViewModel by viewModels()
    private val args: MapFragmentArgs by navArgs()

    override fun initView() {
        val mapAdapter = MapAdapter(viewModel, viewLifecycleOwner)
        val commentListAdapter = MapCommentListAdapter() {
            viewModel.onCommentClicked(it)
        }

        b.list.apply {
            adapter = ConcatAdapter(
                mapAdapter,
                commentListAdapter.withLoadStateFooter(LoadStateAdapter())
            )
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.map.observe(viewLifecycleOwner) { map ->
            // update action bar title
            setToolbarTitle(map.info.title)
            commentListAdapter.originalPosterUsername = map.author.username



            if (map.stats.commentCount == 0)
                return@observe
            lifecycleScope.launch {
                viewModel.comments.collectLatest { pagingData ->
                    commentListAdapter.submitData(pagingData)
                }
            }
        }

        lifecycleScope.launch {
            if (args.map != null)
                viewModel.loadMap(args.map!!)
            else if (args.mapCode != null)
                viewModel.loadMap(args.mapCode!!)
            //else
            //    throw Exception("No map or mapCode supplied.")
        }
    }
}
