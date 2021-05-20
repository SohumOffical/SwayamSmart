package com.sngs.swayam.smartapp.Adapter.area

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.City.CityListDatum
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.state_city_item_layout.view.*

class CityListAdapter  (private var arrayList: List<CityListDatum>, private val context: Context) :
    RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.state_city_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.state_name_txt.setText(""+arrayList.get(position).cityName)

        if(arrayList.get(position).isIs_selected){
            holder.itemView.state_name_txt.isChecked = true
        }
        else{
            holder.itemView.state_name_txt.isChecked = false
        }

        holder.itemView.state_name_txt.setOnClickListener{
            for (i in 0..(arrayList.size-1)) {
                if(i==position){
                    arrayList.get(i).isIs_selected = true
                    Links.selected_city_name = arrayList.get(position).cityName
                    Links.selected_city_id = arrayList.get(position).cityId
                }
                else{
                    arrayList.get(i).isIs_selected = false
                }
            }
            notifyDataSetChanged()
        }
    }

}