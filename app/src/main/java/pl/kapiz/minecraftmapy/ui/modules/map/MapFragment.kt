package pl.kapiz.minecraftmapy.ui.modules.map

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.FragmentMapBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment
import pl.kapiz.minecraftmapy.utils.observeNonNull
import pl.kapiz.minecraftmapy.utils.setUnderlined

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    override val viewModel: MapViewModel by viewModels()
    private val args: MapFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.viewModel = viewModel
    }

    override fun initView() {
        viewModel.apply {
            setMap(args.map)

            map.observeNonNull(viewLifecycleOwner, Observer { map ->
                b.mapCarousel.apply {
                    setImageListener { position, imageView ->
                        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                        imageView.load(map.images[position])
                    }
                    pageCount = map.images.size
                }
            })
        }

        b.mapAuthor.setUnderlined()
    }
}
