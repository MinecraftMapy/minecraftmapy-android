package pl.kapiz.minecraftmapy.ui.modules.maps

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.model.MapQuery
import pl.kapiz.minecraftmapy.databinding.MapListFragmentBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment
import pl.kapiz.minecraftmapy.ui.modules.main.MainActivity
import pl.kapiz.minecraftmapy.utils.LoadStateAdapter

@AndroidEntryPoint
class MapListFragment : BaseFragment<MapListFragmentBinding>(R.layout.map_list_fragment) {

    private lateinit var mapListAdapter: MapListAdapter

    private val activity by lazy { getActivity() as? MainActivity }
    private val searchManager by lazy { activity?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager }

    override val viewModel: MapListViewModel by viewModels()
    private val args: MapListFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.viewModel = viewModel
    }

    @ExperimentalPagingApi
    override fun initView() {
        mapListAdapter = MapListAdapter(viewModel::onMapClicked)

        viewModel.mapQuery.observe(viewLifecycleOwner) {
            setToolbarTitle(it.getTitle(b.root.context))
        }

        viewModel.submitMapQuery(args.mapQuery ?: MapQuery.default())

        lifecycleScope.launch {
            mapListAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.onLoadStateFlow(loadStates)
            }
        }

        lifecycleScope.launch {
            mapListAdapter.dataRefreshFlow.collectLatest {
                b.mapList.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            viewModel.mapFlow.collectLatest { pagingData ->
                mapListAdapter.submitData(pagingData)
            }
        }

        b.mapList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mapListAdapter.withLoadStateFooter(LoadStateAdapter())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_list_menu, menu)

        val searchItem = menu.findItem(R.id.menu_maps_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.apply {
            setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?) = false
                override fun onQueryTextSubmit(query: String?) =
                    viewModel.onQueryTextSubmit(query)
            })
        }

        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean = true
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean =
                viewModel.onSearchCollapse()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return viewModel.onOptionsItemSelected(item)
    }
}
