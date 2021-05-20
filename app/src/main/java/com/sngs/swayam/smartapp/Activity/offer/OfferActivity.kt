package com.sngs.swayam.smartapp.Activity.offer

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sngs.swayam.smartapp.Adapter.offer.OfferListAdapter
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.PromotionList.GetCustomerPromotionListBaseResponse
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_offer.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferActivity : AppCompatActivity() {
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


    private fun init()
    {
        val offer_rv_list = findViewById(R.id.offer_rv_list) as RecyclerView
        offer_rv_list.layoutManager = GridLayoutManager(applicationContext, 2)

        api_calling_for_user_promotion_list()
    }

    private fun click_fun()
    {
        ivBack.setOnClickListener {
            finish()
        }
    }


    private fun api_calling_for_user_promotion_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callUserPromotionList(this, auth_id, auth_token, Links.User_Type)
            .enqueue(object : Callback<GetCustomerPromotionListBaseResponse> {
                override fun onResponse(call: Call<GetCustomerPromotionListBaseResponse>, response: Response<GetCustomerPromotionListBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.PromotionResult_list.clear()
                        if (success_v?.toInt()==1)
                        {
                            if(response.body()!!.promotionListResult!=null){
                                Links.PromotionResult_list = response.body()!!.promotionListResult
                                offer_rv_list.adapter = OfferListAdapter(Links.PromotionResult_list,this@OfferActivity)
                            }
                        }
                        else {
                            offer_rv_list.adapter = OfferListAdapter(Links.PromotionResult_list,this@OfferActivity)
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

}