package pl.kapiz.minecraftmapy.ui.modules.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import dagger.android.support.DaggerFragment
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.databinding.FragmentMapBinding
import pl.kapiz.minecraftmapy.ui.base.ViewModelFactory
import pl.kapiz.minecraftmapy.utils.observeNonNull
import javax.inject.Inject

class MapFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mapViewModel: MapViewModel by viewModels { viewModelFactory }

    private lateinit var b: FragmentMapBinding
    private val args: MapFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentMapBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        mapViewModel.apply {
            init(args.map)

            map.observeNonNull(viewLifecycleOwner, Observer { map ->
                b.apply {
                    mapTitle.text = map.info.title
                    mapDescription.text = map.info.description
                    mapAuthor.text = getString(R.string.format_author, map.author.username)

                    mapCarousel.apply {
                        setImageListener { position, imageView ->
                            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                            imageView.load(map.images[position])
                        }
                        pageCount = map.images.size
                    }
                }
            })

            action.observe(viewLifecycleOwner, Observer { action ->
                findNavController().navigate(action)
            })
        }

        b.mapAuthor.setOnClickListener(mapViewModel::onAuthorClicked)
    }
}
