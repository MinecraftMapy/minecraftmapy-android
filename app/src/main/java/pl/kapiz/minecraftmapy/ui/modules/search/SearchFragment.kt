package pl.kapiz.minecraftmapy.ui.modules.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.android.support.DaggerFragment
import pl.kapiz.minecraftmapy.databinding.FragmentSearchBinding
import pl.kapiz.minecraftmapy.ui.base.ViewModelFactory
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    companion object {

        fun newInstance() = SearchFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val searchViewModel: SearchViewModel by viewModels { viewModelFactory }

    private lateinit var b: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentSearchBinding.inflate(inflater, container, false)
        return b.root
    }
}
