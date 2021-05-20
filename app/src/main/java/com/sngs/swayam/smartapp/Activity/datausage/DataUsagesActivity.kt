package com.sngs.swayam.smartapp.Activity.datausage

import android.app.usage.NetworkStats
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.smartapp.Adapter.datausage.DatauasgeAdapter
import com.sngs.swayam.smartapp.Model.DataUsage
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.R


class DataUsagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
                R.anim.slide_in_right,
                R.anim.slide_out_left
        );
        setContentView(R.layout.activity_data_usages)

        init()
        set_data()
    }

    private fun set_data()
    {
        Links.data_usage_type.clear()
        Links.data_usage_type.add(DataUsage("TODAY",true))
        Links.data_usage_type.add(DataUsage("DAILY",false))
        Links.data_usage_type.add(DataUsage("WEEKLY",false))
        Links.data_usage_type.add(DataUsage("MONTHLY",false))
        Links.data_usage_type.add(DataUsage("YEARLY",false))
        Links.data_usage_type.add(DataUsage("TOTAL",false))
        Links.data_usage_type.add(DataUsage("CUSTOM",false))
    }

    private fun init()
    {
        val data_usage_type = findViewById(R.id.data_usage_type) as RecyclerView
        data_usage_type.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,false)
        data_usage_type.adapter = DatauasgeAdapter(Links.data_usage_type,this@DataUsagesActivity)

        val data_usage_detail = findViewById(R.id.data_usage_detail) as RecyclerView
        data_usage_detail.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
    }
}