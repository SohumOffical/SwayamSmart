package com.sngs.swayam.smartapp.Adapter.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sngs.swayam.smartapp.Network.model.BannerList.BannerListResult
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.banner_item_layout.view.*


class BannerListAdapter  (private var arrayList: List<BannerListResult>, private val context: Context) :
    RecyclerView.Adapter<BannerListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.banner_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(arrayList.get(position).banner).into(holder.itemView.banner_img);

    }

}