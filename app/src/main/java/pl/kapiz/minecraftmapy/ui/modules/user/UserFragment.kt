package pl.kapiz.minecraftmapy.ui.modules.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import dagger.android.support.DaggerFragment
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.databinding.FragmentUserBinding
import pl.kapiz.minecraftmapy.ui.base.MapItem
import pl.kapiz.minecraftmapy.ui.base.ViewModelFactory
import pl.kapiz.minecraftmapy.utils.observeNonNull
import pl.kapiz.minecraftmapy.utils.setEndlessScrollListener
import javax.inject.Inject

class UserFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val userViewModel: UserViewModel by viewModels { viewModelFactory }

    private lateinit var b: FragmentUserBinding
    private val args: UserFragmentArgs by navArgs()

    private lateinit var mapsAdapter: ModelAdapter<Map, MapItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentUserBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mapsAdapter = ModelAdapter { MapItem(it) }

        userViewModel.apply {
            init(args.username)

            loading.observe(viewLifecycleOwner, Observer { loading ->
                b.userContainer.visibility = if (loading) View.GONE else View.VISIBLE
                b.userProgress.visibility = if (loading) View.VISIBLE else View.GONE
            })

            user.observeNonNull(viewLifecycleOwner, Observer { user ->
                b.apply {
                    userName.text = user.info.username
                    userDescription.text = user.info.description
                    userMapListTitle.text = getString(R.string.format_maps, user.stats.mapCount)
                }
            })

            mapsLoading.observe(viewLifecycleOwner, Observer { loading ->
                b.userMapList.visibility = if (loading) View.GONE else View.VISIBLE
                b.userMapListProgress.visibility = if (loading) View.VISIBLE else View.GONE
            })

            maps.observe(viewLifecycleOwner, Observer { maps ->
                mapsAdapter.setNewList(maps)
            })

            action.observe(viewLifecycleOwner, Observer { action ->
                findNavController().navigate(action)
            })
        }

        b.userMapList.apply {
            layoutManager = LinearLayoutManager(context)
            setEndlessScrollListener(20) {
                userViewModel.downloadNextMapsPage()
            }
            adapter = FastAdapter.with(mapsAdapter).apply {
                onClickListener = userViewModel::onItemClicked
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}
