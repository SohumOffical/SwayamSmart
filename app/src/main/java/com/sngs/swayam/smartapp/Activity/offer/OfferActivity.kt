package com.sngs.swayam.smartapp.Activity.offer

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.smartapp.Adapter.offer.OfferListAdapter
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.Category.GetCategoryListBaseResponse
import com.sngs.swayam.smartapp.Network.model.PromotionList.GetCustomerPromotionListBaseResponse
import com.sngs.swayam.smartapp.Network.model.PromotionList.PromotionListResult
import com.sngs.swayam.smartapp.Network.model.ServiceProvider.GetServiceProviderBaseResponse
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_offer.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferActivity : AppCompatActivity() {

    var selected_service_id = "0"
    var selected_category_id = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_offer)

        init()
        click_fun()
    }

    private fun init() {
        val offer_rv_list = findViewById(R.id.offer_rv_list) as RecyclerView
        offer_rv_list.layoutManager = GridLayoutManager(applicationContext, 2)

        api_calling_for_user_promotion_list()
        api_calling_for_service_provide_detail()
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun api_calling_for_user_promotion_list() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callUserPromotionList(this, auth_id, auth_token, Links.User_Type,selected_category_id)
            .enqueue(object : Callback<GetCustomerPromotionListBaseResponse> {
                override fun onResponse(call: Call<GetCustomerPromotionListBaseResponse>, response: Response<GetCustomerPromotionListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {

                        var PromotionResult_list : ArrayList<PromotionListResult> = ArrayList()
                        offer_rv_list.adapter = OfferListAdapter(PromotionResult_list,this@OfferActivity,selected_category_id)

                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {
                            if(response.body()!!.promotionListResult!=null){
                                var PromotionResult_list = response.body()!!.promotionListResult
                                offer_rv_list.adapter = OfferListAdapter(PromotionResult_list,this@OfferActivity,selected_category_id)
                            }
                        }
                        else {
                            Links.snack_bar(this@OfferActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@OfferActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<GetCustomerPromotionListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@OfferActivity,main_layout,t.message.toString())
                }
            })
    }

    private fun api_calling_for_service_provide_detail() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        ServiceCall.callGetServiceProviderList(this, auth_id, auth_token, Links.User_Type)
                .enqueue(object : Callback<GetServiceProviderBaseResponse> {
                    override fun onResponse(call: Call<GetServiceProviderBaseResponse>, response: Response<GetServiceProviderBaseResponse>) {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            Links.service_list.clear()
                            Links.service_sp_list.clear()
                            Links.service_sp_list.add("All")
                            if (success_v?.toInt()==1) {
                                Links.service_list = response.body()!!.serviceListData
                                for (i in 0..(Links.service_list.size-1)) {
                                    Links.service_sp_list.add(Links.service_list.get(i).serviceName)
                                }
                                set_service_provider()
                            }
                        }
                    }
                    override fun onFailure(call: Call<GetServiceProviderBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                    }
                })
    }

    private fun set_service_provider() {
        if (service_sp != null) {
            val arrayAdapter = ArrayAdapter(applicationContext,R.layout.spinnerlayout,R.id.txt_spinner,
                    Links.service_sp_list)
            service_sp.adapter = arrayAdapter
            service_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    if(position==0) {
                        selected_service_id = "0"
                    }
                    else{
                        selected_service_id = Links.service_list.get(position).serviceId
                    }
                    api_calling_for_category_list()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun api_calling_for_category_list() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetCategoryList(this, auth_id, auth_token, Links.User_Type, selected_service_id)
                .enqueue(object : Callback<GetCategoryListBaseResponse> {
                    override fun onResponse(call: Call<GetCategoryListBaseResponse>, response: Response<GetCategoryListBaseResponse>) {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            Links.category_list.clear()
                            Links.category_sp_list.clear()
                            if (success_v?.toInt()==1)
                            {
                                Links.category_list = response.body()!!.categoryListData
                                for (i in 0..(Links.category_list.size-1)) {
                                    Links.category_sp_list.add(Links.category_list.get(i).categoryName)
                                }

                                set_spiner_catergory()
                            }
                            else {
                                set_spiner_catergory()
                                Links.snack_bar(this@OfferActivity,main_layout,response.body()?.message.toString())
                            }
                        } else {
                            Links.snack_bar(this@OfferActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<GetCategoryListBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@OfferActivity,main_layout, t.message)
                    }
                })
    }

    private fun set_spiner_catergory() {
        if (category_sp != null) {
            val arrayAdapter = ArrayAdapter(applicationContext,R.layout.spinnerlayout,R.id.txt_spinner,
                    Links.category_sp_list)
            category_sp.adapter = arrayAdapter
            category_sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selected_category_id = Links.category_list.get(position).categoryId
                    api_calling_for_user_promotion_list()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }
}