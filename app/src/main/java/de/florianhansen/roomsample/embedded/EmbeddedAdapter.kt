package de.florianhansen.roomsample.embedded

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import de.florianhansen.roomsample.R

class EmbeddedAdapter : RecyclerView.Adapter<EmbeddedAdapter.EmbeddedViewHolder>() {

    var items = listOf<EmbeddedListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmbeddedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.embedded_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: EmbeddedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class EmbeddedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.embedded_item_username)
        lateinit var user: TextView

        @BindView(R.id.embedded_item_city)
        lateinit var city: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun bind(item: EmbeddedListItem) {
            user.text = item.name
            city.text = item.city
        }
    }
}