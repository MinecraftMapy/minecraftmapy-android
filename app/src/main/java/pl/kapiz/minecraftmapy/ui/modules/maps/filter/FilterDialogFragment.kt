package pl.kapiz.minecraftmapy.ui.modules.maps.filter

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.DialogFilterBinding

@AndroidEntryPoint
class FilterDialogFragment : DialogFragment() {

    companion object {

        fun newInstance() = FilterDialogFragment()
    }

    private val viewmodel: FilterDialogViewModel by viewModels()
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
