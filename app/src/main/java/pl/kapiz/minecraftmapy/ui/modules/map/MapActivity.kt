package pl.kapiz.minecraftmapy.ui.modules.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import coil.api.load
import dagger.android.support.DaggerAppCompatActivity
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.pojo.Map
import pl.kapiz.minecraftmapy.databinding.ActivityMapBinding

class MapActivity : DaggerAppCompatActivity() {

    companion object {
        private const val EXTRA_MAP = "EXTRA_MAP"

        fun getStartIntent(context: Context, map: Map) = Intent(context, MapActivity::class.java)
            .putExtra(EXTRA_MAP, map)
    }

    private lateinit var b: ActivityMapBinding

    private var map: Map? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityMapBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.mapActionBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        map = intent.getSerializableExtra(EXTRA_MAP) as? Map

        initView()
    }

    private fun initView() {
        map?.also { map ->
            supportActionBar?.title = map.info.title

            b.apply {
                mapTitle.text = map.info.title
                mapDescription.text = map.info.description
                mapAuthor.text = getString(R.string.format_author, map.author.username)

                mapCarousel.pageCount = map.images.size
                mapCarousel.setImageListener { position, imageView ->
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    imageView.load(map.images[position]) {
                        crossfade(true)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return false
        }
        return true
    }
}
