package pl.kapiz.minecraftmapy.ui.modules.search

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.FragmentSearchBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    override val viewmodel: SearchViewModel by viewModels()
}
