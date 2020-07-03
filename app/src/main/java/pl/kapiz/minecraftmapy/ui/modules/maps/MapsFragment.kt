package pl.kapiz.minecraftmapy.ui.modules.maps

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.*
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.databinding.FragmentMapsBinding
import pl.kapiz.minecraftmapy.ui.base.OverridesOnBackPressed
import pl.kapiz.minecraftmapy.utils.observeNonNull
import pl.kapiz.minecraftmapy.utils.setEndlessScrollListener
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MapsFragment : DaggerFragment(), OverridesOnBackPressed, CoroutineScope {

    companion object {

        fun newInstance() = MapsFragment()
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mapsViewModel: MapsViewModel by viewModels { viewModelFactory }

    private lateinit var b: FragmentMapsBinding
    private lateinit var mapsAdapter: ModelAdapter<Map, MapItem>

    private val searchManager by lazy { activity?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentMapsBinding.inflate(inflater, container, false).apply {
            viewmodel = mapsViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mapsAdapter = ModelAdapter {
            MapItem(
                it
            )
        }

        mapsViewModel.apply {
            init()

            maps.observe(viewLifecycleOwner, Observer { maps ->
                mapsAdapter.setNewList(maps)
            })

            action.observeNonNull(viewLifecycleOwner, Observer { action ->
                findNavController().navigate(action)
            })

            searchString.observe(viewLifecycleOwner, Observer {
                b.mapList.scrollToPosition(0)
            })
        }

        b.mapList.apply {
            layoutManager = LinearLayoutManager(context)
            setEndlessScrollListener(20) {
                mapsViewModel.downloadNextPage()
            }
            adapter = FastAdapter.with(mapsAdapter).apply {
                onClickListener = mapsViewModel::onItemClicked
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
                    mapsViewModel.onQueryTextSubmit(query)
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mapsViewModel.onOptionsItemSelected(item)
    }

    override fun onBackPressed(default: () -> Unit) {
        b.mapList.apply {
            if (computeVerticalScrollOffset() > height / 4 || mapsViewModel.searchString.value != null) {
                smoothScrollToPosition(0)
                launch(Dispatchers.Main) {
                    withTimeout(2000) {
                        while (layoutManager?.isSmoothScrolling == true) {
                            delay(100)
                        }
                        delay(500)
                    }
                    mapsViewModel.refresh()
                }
            } else default()
        }
    }
}
