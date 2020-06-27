package pl.kapiz.minecraftmapy.ui.modules.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.android.support.DaggerFragment
import pl.kapiz.minecraftmapy.databinding.FragmentMoreBinding
import pl.kapiz.minecraftmapy.ui.base.ViewModelFactory
import javax.inject.Inject

class MoreFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val moreViewModel: MoreViewModel by viewModels { viewModelFactory }

    private lateinit var b: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentMoreBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        moreViewModel.init()
        moreViewModel.text.observe(viewLifecycleOwner, Observer {
            b.textNotifications.text = it
        })
    }
}
