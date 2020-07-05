package pl.kapiz.minecraftmapy.ui.modules.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import dagger.hilt.android.AndroidEntryPoint
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.databinding.FragmentUserBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment
import pl.kapiz.minecraftmapy.ui.modules.maps.MapItem
import pl.kapiz.minecraftmapy.utils.observeNonNull
import pl.kapiz.minecraftmapy.utils.setEndlessScrollListener

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(R.layout.fragment_user) {

    override val viewmodel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()

    private lateinit var mapsAdapter: ModelAdapter<Map, MapItem>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.viewmodel = viewmodel
    }

    override fun initView() {
        mapsAdapter = ModelAdapter {
            MapItem(it)
        }

        viewmodel.apply {
            init(args.username)

            user.observeNonNull(viewLifecycleOwner, Observer { user ->
                b.userAvatar.load(user.info.avatarUrl)
            })

            maps.observe(viewLifecycleOwner, Observer { maps ->
                FastAdapterDiffUtil[mapsAdapter] = mapsAdapter.intercept(maps)
            })
        }

        b.userMapList.apply {
            layoutManager = LinearLayoutManager(context)
            setEndlessScrollListener(20) {
                viewmodel.downloadNextMapsPage()
            }
            adapter = FastAdapter.with(mapsAdapter).apply {
                onClickListener = viewmodel::onItemClicked
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            isNestedScrollingEnabled = false
        }
    }
}
