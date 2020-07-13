package pl.kapiz.minecraftmapy.ui.modules.user

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
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

    override val viewModel: UserViewModel by viewModels()
    private val args: UserFragmentArgs by navArgs()

    override fun initView() {
        val userAdapter = UserAdapter(viewModel, viewLifecycleOwner)
        val mapListAdapter = MapListAdapter(viewModel::onMapClicked)

        b.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ConcatAdapter(
                userAdapter,
                mapListAdapter.withLoadStateFooter(LoadStateAdapter())
            )
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.user.observe(viewLifecycleOwner) {

        }

        viewModel.userFetched.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewModel.maps.collectLatest { pagingData ->
                    mapListAdapter.submitData(pagingData)
                }
            }
        }

        lifecycleScope.launch {
            if (args.user != null)
                viewModel.loadUser(args.user!!)
            else if (args.username != null)
                viewModel.fetchUser(args.username!!)
            //else
            //    throw Exception("No user or username supplied.")
        }
    }
}
