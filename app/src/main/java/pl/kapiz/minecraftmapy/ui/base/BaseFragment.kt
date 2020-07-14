package pl.kapiz.minecraftmapy.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import pl.kapiz.minecraftmapy.ui.modules.main.MainViewModel
import pl.kapiz.minecraftmapy.utils.observeNonNull

abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment() {

    protected lateinit var b: B
    protected abstract val viewModel: BaseViewModel
    protected val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = DataBindingUtil.inflate(inflater, layoutId, container, false)
        b.lifecycleOwner = viewLifecycleOwner
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            init()

            navCommand.observeNonNull(viewLifecycleOwner, Observer { action ->
                findNavController().navigate(action)
            })

            toastText.observeNonNull(viewLifecycleOwner, Observer { toast ->
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            })
        }

        initView()
    }

    protected fun setToolbarTitle(title: CharSequence) {
        activityViewModel.toolbarTitle.postValue(title)
    }

    protected open fun initView() {}
}
