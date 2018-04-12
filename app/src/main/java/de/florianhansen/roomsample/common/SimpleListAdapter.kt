package de.florianhansen.roomsample.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import de.florianhansen.roomsample.R

class SimpleListAdapter : RecyclerView.Adapter<SimpleListAdapter.SimpleViewHolder>() {

    var items = listOf<SimpleListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.embedded_item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class SimpleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.embedded_item_username)
        lateinit var user: TextView

        @BindView(R.id.embedded_item_city)
        lateinit var city: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun bind(item: SimpleListItem) {
            user.text = item.headline
            city.text = item.subtitle
        }
    }
}