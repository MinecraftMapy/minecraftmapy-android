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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.databinding.FragmentMapsBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment
import pl.kapiz.minecraftmapy.ui.modules.main.MainActivity
import pl.kapiz.minecraftmapy.utils.observeNonNull
import pl.kapiz.minecraftmapy.utils.setEndlessScrollListener

class MapsFragment : BaseFragment<FragmentMapsBinding>(R.layout.fragment_maps) {

    private lateinit var mapsAdapter: ModelAdapter<Map, MapItem>

    private val activity by lazy { getActivity() as? MainActivity }
    private val searchManager by lazy { activity?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager }

    override val viewmodel: MapsViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.viewmodel = viewmodel
    }

    override fun initView() {
        mapsAdapter = ModelAdapter {
            MapItem(it)
        }

        viewmodel.maps.observeNonNull(viewLifecycleOwner, Observer { maps ->
            mapsAdapter.setNewList(maps)
        })

        viewmodel.searchString.observe(viewLifecycleOwner, Observer {
            b.mapList.scrollToPosition(0)
        })

        b.mapList.apply {
            layoutManager = LinearLayoutManager(context)
            setEndlessScrollListener(20) {
                viewmodel.downloadNextPage()
            }
            adapter = FastAdapter.with(mapsAdapter).apply {
                onClickListener = viewmodel::onItemClicked
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_maps, menu)

        val searchView = (menu.findItem(R.id.menu_maps_search).actionView as? SearchView)
        searchView?.apply {
            setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?) = false
                override fun onQueryTextSubmit(query: String?) =
                    viewmodel.onQueryTextSubmit(query)
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return viewmodel.onOptionsItemSelected(item)
    }
}
