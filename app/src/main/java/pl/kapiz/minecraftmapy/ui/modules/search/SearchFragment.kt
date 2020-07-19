package pl.kapiz.minecraftmapy.ui.modules.search

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.SearchFragmentBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchFragmentBinding>(R.layout.search_fragment) {

    override val viewModel: SearchViewModel by viewModels()
}
