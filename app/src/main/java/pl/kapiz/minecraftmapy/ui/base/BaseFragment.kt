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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import pl.kapiz.minecraftmapy.utils.observeNonNull

abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes val layoutId: Int) : Fragment() {

    protected abstract val viewModel: BaseViewModel

    protected lateinit var b: B

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

            action.observeNonNull(viewLifecycleOwner, Observer { action ->
                findNavController().navigate(action)
            })

            toast.observeNonNull(viewLifecycleOwner, Observer { toast ->
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            })
        }

        initView()
    }

    protected open fun initView() {}
}
