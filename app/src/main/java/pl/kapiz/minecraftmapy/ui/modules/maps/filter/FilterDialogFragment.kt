package pl.kapiz.minecraftmapy.ui.modules.maps.filter

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import dagger.android.support.DaggerAppCompatDialogFragment
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.DialogFilterBinding
import pl.kapiz.minecraftmapy.ui.base.ViewModelFactory
import javax.inject.Inject

class FilterDialogFragment : DaggerAppCompatDialogFragment() {

    companion object {

        fun newInstance() = FilterDialogFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val filterDialogViewModel: FilterDialogViewModel by viewModels { viewModelFactory }

    private lateinit var b: DialogFilterBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            b = DialogFilterBinding.inflate(it.layoutInflater)

            AlertDialog.Builder(it)
                .setTitle(R.string.menu_maps_filter)
                .setView(b.root)
                .create()
        } ?: throw IllegalStateException("Activity must not be null.")
    }
}
