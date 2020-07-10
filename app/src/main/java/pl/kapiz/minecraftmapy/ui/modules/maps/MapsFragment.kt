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
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.FragmentMapsBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment
import pl.kapiz.minecraftmapy.ui.modules.main.MainActivity
import pl.kapiz.minecraftmapy.utils.ItemLoadStateAdapter

@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding>(R.layout.fragment_maps) {

    private lateinit var mapAdapter: MapAdapter

    private val activity by lazy { getActivity() as? MainActivity }
    private val searchManager by lazy { activity?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager }

    override val viewModel: MapsViewModel by viewModels()

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
        mapAdapter = MapAdapter(viewModel::onMapItemClick)

        lifecycleScope.launch {
            mapAdapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.onLoadStateFlow(loadStates)
            }
        }

        lifecycleScope.launch {
            mapAdapter.dataRefreshFlow.collectLatest {
                b.mapList.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            viewModel.mapFlow.collectLatest { pagingData ->
                mapAdapter.submitData(pagingData)
            }
        }

        b.mapList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mapAdapter.withLoadStateFooter(ItemLoadStateAdapter())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_maps, menu)

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
