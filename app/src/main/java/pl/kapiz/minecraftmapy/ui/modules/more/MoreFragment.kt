package pl.kapiz.minecraftmapy.ui.modules.more

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.MoreFragmentBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment

@AndroidEntryPoint
class MoreFragment : BaseFragment<MoreFragmentBinding>(R.layout.more_fragment) {

    override val viewModel: MoreViewModel by viewModels()
}
