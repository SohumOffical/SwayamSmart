package com.sngs.swayam.smartapp.Adapter.datausage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.smartapp.Model.DataUsage
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.data_usage_item_layout.view.*

class DatauasgeAdapter  (private var arrayList: List<DataUsage>, private val context: Context) :
    RecyclerView.Adapter<DatauasgeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.data_usage_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {

        if(Links.data_usage_type.get(position).isIs_selected){
            holder.itemView.data_usage_txt.setTextColor( context.resources.getColor(R.color.white))
        }
        else{
            holder.itemView.data_usage_txt.setTextColor( context.resources.getColor(R.color.blue_color))
        }

        holder.itemView.data_usage_txt.setText(""+arrayList.get(position).data_usage_type)
        holder.itemView.data_usage_li.setOnClickListener {
            for (i in 0..(Links.data_usage_type.size-1)) {
               if(i==position){
                   Links.data_usage_type.get(i).isIs_selected = true
               }
               else{
                   Links.data_usage_type.get(i).isIs_selected = false
               }
            }
            notifyDataSetChanged()
        }

    }

}