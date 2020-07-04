package pl.kapiz.minecraftmapy.ui.modules.map

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.FragmentMapBinding
import pl.kapiz.minecraftmapy.ui.base.BaseFragment
import pl.kapiz.minecraftmapy.utils.observeNonNull
import pl.kapiz.minecraftmapy.utils.setUnderlined

class MapFragment : BaseFragment<FragmentMapBinding>(R.layout.fragment_map) {

    override val viewmodel: MapViewModel by viewModels { viewModelFactory }
    private val args: MapFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        b.viewmodel = viewmodel
    }

    override fun initView() {
        viewmodel.apply {
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
