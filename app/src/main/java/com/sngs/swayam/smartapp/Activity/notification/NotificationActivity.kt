package com.sngs.swayam.smartapp.Activity.notification

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.business.adapters.notifications.NotificationListAdapter
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.Notification.NotificationBaseResponse
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_notification)

        init()
        set_data()
        click()

    }



    private fun init() {
        val rv_notification_list = findViewById(R.id.rv_notification_list) as RecyclerView
        rv_notification_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }

    private fun set_data() {
        api_calling_for_notification()
    }

    private fun click() {
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun api_calling_for_notification()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetNotificationList(this, auth_id,auth_token, Links.User_Type)
            .enqueue(object : Callback<NotificationBaseResponse> {
                override fun onResponse(call: Call<NotificationBaseResponse>, response: Response<NotificationBaseResponse>)
                {
                    Links.notification_List.clear()
                    rv_notification_list.adapter = NotificationListAdapter(Links.notification_List,this@NotificationActivity)

                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful())
                    {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1)
                        {
                            loading_layout.setVisibility(View.GONE)

                            if(response.body()!!.notification!=null)
                            {
                                Links.notification_List = response.body()!!.notification
                                rv_notification_list.adapter = NotificationListAdapter( Links.notification_List,this@NotificationActivity)
                            }
                        }
                        else
                        {
                            loading_layout.setVisibility(View.GONE)
                            Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    else
                    {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@NotificationActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<NotificationBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@NotificationActivity,main_layout, t.message.toString())
                }
            })
    }



}