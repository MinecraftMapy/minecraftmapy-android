package pl.kapiz.minecraftmapy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.kapiz.minecraftmapy.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val mapsAdapter = MapsAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        homeViewModel.init()
        homeViewModel.maps.observe(viewLifecycleOwner, Observer { maps ->
            mapsAdapter.setList(maps)
        })

        val recyclerView = root.findViewById<RecyclerView>(R.id.map_list)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mapsAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}
