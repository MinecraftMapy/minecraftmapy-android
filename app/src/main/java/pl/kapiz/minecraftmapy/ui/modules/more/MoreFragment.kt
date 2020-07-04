package pl.kapiz.minecraftmapy.ui.modules.more

import androidx.fragment.app.viewModels
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.FragmentMoreBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment

class MoreFragment : BaseFragment<FragmentMoreBinding>(R.layout.fragment_more) {

    override val viewmodel: MoreViewModel by viewModels { viewModelFactory }
}
