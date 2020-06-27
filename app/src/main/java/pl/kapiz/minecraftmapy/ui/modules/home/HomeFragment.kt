package pl.kapiz.minecraftmapy.ui.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.scroll.EndlessRecyclerOnScrollListener
import dagger.android.support.DaggerFragment
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.databinding.FragmentHomeBinding
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }

    private lateinit var b: FragmentHomeBinding

    private lateinit var mapsAdapter: ModelAdapter<Map, MapItem>
    private lateinit var adapter: FastAdapter<MapItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentHomeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        mapsAdapter = ModelAdapter { MapItem(it) }

        homeViewModel.init()
        homeViewModel.maps.observe(viewLifecycleOwner, Observer { maps ->
            b.mapProgress.visibility = View.GONE
            b.mapList.visibility = View.VISIBLE
            mapsAdapter.setNewList(maps)
        })

        adapter = FastAdapter.with(mapsAdapter)

        b.mapList.apply {
            layoutManager = LinearLayoutManager(context).apply {
                addOnScrollListener(object : EndlessRecyclerOnScrollListener(this, 20) {
                    override fun onLoadMore(currentPage: Int) {
                        homeViewModel.downloadNextPage()
                    }
                })
            }
            adapter = this@HomeFragment.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}
