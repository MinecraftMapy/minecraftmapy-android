package pl.kapiz.minecraftmapy.ui.modules.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import coil.api.load
import dagger.android.support.DaggerFragment
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.databinding.FragmentMapBinding

class MapFragment : DaggerFragment() {

    private lateinit var b: FragmentMapBinding
    private val args: MapFragmentArgs by navArgs()

    private lateinit var map: Map

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
        map = args.map
        initView()
    }

    private fun initView() {
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
    }
}
