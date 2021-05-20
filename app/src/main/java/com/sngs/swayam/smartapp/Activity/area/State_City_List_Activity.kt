package com.sngs.swayam.smartapp.Activity.area

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sngs.swayam.smartapp.Adapter.area.CityListAdapter
import com.sngs.swayam.business.adapters.area.ServicesListAdapter
import com.sngs.swayam.business.adapters.area.StateListAdapter
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.Area.GetAreaListBaseResponse
import com.sngs.swayam.smartapp.Network.model.City.GetCityListBaseResponse
import com.sngs.swayam.smartapp.Network.model.State.GetStateListBaseResponse
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_state_city_list.*

import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class State_City_List_Activity : AppCompatActivity() {

    var page_type : String = "0"
    var state_id : String = "0"
    var city_id : String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_state_city_list)

        init()
        click_fun()
    }


    private fun init()
    {
        page_type = intent.getStringExtra("page_type").toString()

        val state_city_rv_list = findViewById(R.id.state_city_rv_list) as RecyclerView
        state_city_rv_list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

        if(page_type.equals("0")){
            tvTitle.setText(resources.getString(R.string.lbl_select_state))
            api_calling_for_state_list()
        }
        else if(page_type.equals("1")){
            tvTitle.setText(resources.getString(R.string.lbl_select_city))
            api_calling_for_city_list()
        }
        else{
            tvTitle.setText(resources.getString(R.string.lbl_select_service_area))
            api_calling_for_area_list()
        }

    }

    private fun click_fun()
    {
        ivBack.setOnClickListener {
            finish()
        }

        next_item_img.setOnClickListener {
            finish()
        }
    }

    private fun api_calling_for_state_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetStateList(this, auth_id, auth_token, Links.User_Type)
            .enqueue(object : Callback<GetStateListBaseResponse> {
                override fun onResponse(call: Call<GetStateListBaseResponse>, response: Response<GetStateListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.state_list.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.state_list = response.body()!!.stateListData
                            for (i in 0..(Links.state_list.size-1)) {
                                Links.state_list.get(i).isIs_selected = false
                            }

                            state_city_rv_list.adapter =
                                StateListAdapter(
                                    Links.state_list,
                                    this@State_City_List_Activity
                                )
                        }
                        else {
                            Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                        }
                    } else {
                        Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<GetStateListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Snackbar.make(main_layout, "" + t.message, Snackbar.LENGTH_LONG).show()
                }
            })
    }

    private fun api_calling_for_city_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        for (i in 0..(Links.state_list.size-1)) {
            if(Links.state_list.get(i).isIs_selected)
                state_id = Links.state_list.get(i).stateId
        }

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetCityList(this, auth_id, auth_token, Links.User_Type, state_id)
            .enqueue(object : Callback<GetCityListBaseResponse> {
                override fun onResponse(call: Call<GetCityListBaseResponse>, response: Response<GetCityListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.city_list.clear()
                        if (success_v?.toInt()==1)
                        {
                             Links.city_list = response.body()!!.cityListData

                            for (i in 0..(Links.city_list.size-1)) {
                                Links.city_list.get(i).isIs_selected = false
                            }
                            state_city_rv_list.adapter =
                                CityListAdapter(
                                    Links.city_list,
                                    this@State_City_List_Activity
                                )
                        }
                        else {
                            Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                        }
                    } else {
                        Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<GetCityListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Snackbar.make(main_layout, "" + t.message, Snackbar.LENGTH_LONG).show()
                }
            })
    }

    private fun api_calling_for_area_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        for (i in 0..(Links.state_list.size-1)) {
            if(Links.state_list.get(i).isIs_selected)
                state_id = Links.state_list.get(i).stateId
        }

        for (i in 0..(Links.city_list.size-1)) {
            if(Links.city_list.get(i).isIs_selected)
                city_id = Links.city_list.get(i).cityId
        }

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetAreaList(this, auth_id, auth_token, Links.User_Type, state_id ,city_id)
            .enqueue(object : Callback<GetAreaListBaseResponse> {
                override fun onResponse(call: Call<GetAreaListBaseResponse>, response: Response<GetAreaListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.area_list.clear()
                        if (success_v?.toInt()==1)
                        {
                             Links.area_list = response.body()!!.areaListData

                            for (i in 0..(Links.area_list.size-1)) {
                                Links.area_list.get(i).isIs_selected = false
                            }

                            state_city_rv_list.adapter =
                                ServicesListAdapter(
                                    Links.area_list,
                                    this@State_City_List_Activity
                                )
                        }
                        else {
                            Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                        }
                    } else {
                        Snackbar.make(main_layout, "" + response.body()?.message, Snackbar.LENGTH_LONG).show()
                    }
                }
                override fun onFailure(call: Call<GetAreaListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Snackbar.make(main_layout, "" + t.message, Snackbar.LENGTH_LONG).show()
                }
            })
    }
}
