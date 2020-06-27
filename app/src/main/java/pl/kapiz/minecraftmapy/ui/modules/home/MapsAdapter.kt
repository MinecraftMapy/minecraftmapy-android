package pl.kapiz.minecraftmapy.ui.modules.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.kapiz.minecraftmapy.R
import pl.kapiz.minecraftmapy.data.pojo.Map

class MapsAdapter(private var maps: List<Map>) : RecyclerView.Adapter<MapsAdapter.ViewHolder>() {

    fun setList(maps: List<Map>) {
        this.maps = maps
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = maps.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val map = maps[position]

        holder.apply {
            mapTitleView.text = map.info.title
        }
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_map, parent, false)) {

        val mapTitleView: TextView = itemView.findViewById(R.id.map_title)
    }
}
