package com.sngs.swayam.business.adapters.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.smartapp.Activity.notification.NotificationActivity
import com.sngs.swayam.smartapp.Network.model.Notification.Notification
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_notification.view.tvTitle
import kotlinx.android.synthetic.main.notification_item_layout.view.*
import java.text.ParseException
import java.text.SimpleDateFormat


class NotificationListAdapter  (private var arrayList: List<Notification>, private val context: Context) :
    RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.tvTitle.setText(""+arrayList.get(position).notiType)
        holder.itemView.tvSubTitle.setText(""+arrayList.get(position).notiMessage)


        val date = ""+arrayList.get(position).getmNotiDate()
        val input = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val output = SimpleDateFormat("dd-MMM-YY   hh:mm aa")
        try {
            var oneWayTripDate = input.parse(date)
            var date_display = output.format(oneWayTripDate);
            holder.itemView.tv_date.setText(""+date_display)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val currentString = ""+arrayList.get(position).notiType
        val separated = currentString.split("-")
        var str1 = separated[0].toString()
        if(str1.equals(arrayList.get(position).notiType)){
            holder.itemView.iv_replay.visibility = View.GONE
        }
        else{
            if(str1.equals("Promotion Query Reply ")){
                holder.itemView.iv_replay.visibility = View.VISIBLE
            }
            else{
                holder.itemView.iv_replay.visibility = View.GONE
            }
        }

        holder.itemView.iv_delete.setOnClickListener {
            if (context is NotificationActivity) {
                (context as NotificationActivity).api_calling_for_delete_notification(arrayList.get(position).notiId)
            }
        }

        holder.itemView.iv_replay.setOnClickListener {
            if (context is NotificationActivity) {
                (context as NotificationActivity).open_replay_layout(arrayList.get(position).getmNotiEventId())
            }
        }

    }




}