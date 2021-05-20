package com.sngs.swayam.smartapp.Adapter.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.smartapp.Network.model.AdvertismentList.PromotionBannerListResult
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.notes_item_layout.view.*


class NotesListAdapter  (private var arrayList: List<PromotionBannerListResult>, private val context: Context) :
    RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.notes_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.notes_txt.setText(""+arrayList.get(position).advertisement)

    }

}