package com.sngs.swayam.smartapp.Activity.offer

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.jsibbold.zoomage.ZoomageView
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.BaseResponse
import com.sngs.swayam.smartapp.Network.model.CustomerDetail.CustomerDetailBaseResponse
import com.sngs.swayam.smartapp.Network.model.PromotionList.GetCustomerPromotionListBaseResponse
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_offer_detail.*
import kotlinx.android.synthetic.main.activity_offer_detail.ivBack
import kotlinx.android.synthetic.main.activity_offer_detail.main_layout
import kotlinx.android.synthetic.main.activity_state_city_list.*
import kotlinx.android.synthetic.main.img_details_layout.*
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.android.synthetic.main.query_details_layout.*
import kotlinx.android.synthetic.main.query_details_layout.btnContinue_query
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferDetailActivity : AppCompatActivity()
{
    var selected_pos : Int = 0
    var promotion_Id : String = ""
    var cutsomer_id : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_offer_detail)

        init()
        click_fun()
    }


    private fun init()
    {
        selected_pos = intent.getStringExtra("selected_pos").toString().toInt()

        api_calling_for_user_promotion_list()
    }


    private fun click_fun()
    {
        ivBack.setOnClickListener {
            finish()
        }

        chat_with_service_provider_txt.setOnClickListener {
            query_detail_txt.setText("")
            query_detail_layout.visibility = View.VISIBLE
        }

        btnContinue_query.setOnClickListener {
            query_detail_layout.visibility = View.GONE

            if(query_detail_txt.text.toString().isEmpty()){
                Links.snack_bar(this@OfferDetailActivity,main_layout,resources.getString(R.string.empty_query_detail))
            }
            else{
                api_calling_for_query_detail(query_detail_txt.text.toString())
            }
        }

        like_rel.setOnClickListener {
            api_calling_for_PromotionLikeDislike("1")
        }
        dislike_rel.setOnClickListener {
            api_calling_for_PromotionLikeDislike("0")
        }

        img_icon1.setOnClickListener {
            img_detail_layout.visibility = View.VISIBLE
        }

        btn_close_img.setOnClickListener {
            img_detail_layout.visibility = View.GONE
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
                                set_data()
                            }
                        }
                        else {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<GetCustomerPromotionListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@OfferDetailActivity,main_layout,t.message.toString())
                }
            })
    }

    private fun set_data()
    {
        Glide.with(applicationContext).load(Links.PromotionResult_list.get(selected_pos).promotionAttachment)
                .placeholder(R.drawable.app_logo).into(img_icon1);

        Glide.with(applicationContext).load(Links.PromotionResult_list.get(selected_pos).promotionAttachment)
            .placeholder(R.drawable.app_logo).into(img_detail);

        prom_title_txt.setText(""+Links.PromotionResult_list.get(selected_pos).promotionTitle)

        original_price_detail_txt.setText(""+Links.PromotionResult_list.get(selected_pos).promotionPrice)
        offer_price_detail_txt.setText("₹"+Links.PromotionResult_list.get(selected_pos).promotionAdditionalOffer)
        additional_offer_detail_txt.setText(""+Links.PromotionResult_list.get(selected_pos).getmPromotionFinalRate())

        category_detail_txt.setText(""+Links.PromotionResult_list.get(selected_pos).categoryName)
        promo_start_date_detail_txt.setText(""+Links.PromotionResult_list.get(selected_pos).promotionStartDate)
        promo_expries_detail_txt.setText(""+Links.PromotionResult_list.get(selected_pos).promotionDaysLimit+" Days")
        promo_code_detail_txt.setText(""+Links.PromotionResult_list.get(selected_pos).getmPromotionCode())

        var m_no = Links.PromotionResult_list.get(selected_pos).getmCustomerContactNumber()
        m_no = m_no.get(0)+"x"+m_no.get(2)+"x"+m_no.get(4)+"x"+m_no.get(6)+"x"+m_no.get(8)+"x"
        whats_app_no_txt.setText(""+m_no)

        service_desc_txt.setText(""+Links.PromotionResult_list.get(selected_pos).promotionDescription)

       /* like_txt.setText(""+resources.getString(R.string.likes)+"("+
                Links.PromotionResult_list.get(selected_pos).getmTotalLike()+")")
        dislike_txt.setText(""+resources.getString(R.string.dislikes)+"("+
                Links.PromotionResult_list.get(selected_pos).getmTotalDeslike()+")")*/
        viwer_count_txt.setText(""+resources.getString(R.string.views)+" "
                +Links.PromotionResult_list.get(selected_pos).getmTotalViewed())


        if(Links.PromotionResult_list.get(selected_pos).subCategoryName!=null){
            if(!Links.PromotionResult_list.get(selected_pos).subCategoryName.isEmpty()){
                sub_category_detail_txt.setText(""+ Links.PromotionResult_list.get(selected_pos).subCategoryName)
            }
            else{
                sub_category_li.visibility= View.GONE
            }
        }
        else{
            sub_category_li.visibility= View.GONE
        }


        if(!Links.PromotionResult_list.get(selected_pos).getmPromotionCouponOfferPrice().equals("0")){
            coupon_offer_txt.visibility = View.VISIBLE
            coupon_offer_txt.setText("You get Additional Offer : ₹ "+Links.PromotionResult_list.get(selected_pos).getmPromotionCouponOfferPrice())
        }

        promotion_Id = Links.PromotionResult_list.get(selected_pos).promotionId.toString()
        cutsomer_id = Links.PromotionResult_list.get(selected_pos).getmCustomerId().toString()

        api_calling_for_customer_detail(Links.PromotionResult_list.get(selected_pos).getmCustomerId())

        api_calling_for_view_promotion()
    }

    private fun api_calling_for_user_purchase_promotion()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callUserPurchaseDetail(this, auth_id, auth_token, Links.User_Type,"")
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.PromotionResult_list.clear()
                        if (success_v?.toInt()==1)
                        {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                        else {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@OfferDetailActivity,main_layout,t.message.toString())
                }
            })
    }

    private fun api_calling_for_view_promotion()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callViewPromotion(this, auth_id, auth_token, Links.User_Type,
                Links.PromotionResult_list.get(selected_pos).promotionId)
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1) {}
                            else {}
                        } else {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@OfferDetailActivity,main_layout,t.message.toString())
                    }
                })
    }

    private fun api_calling_for_customer_detail(cutsomer_id : String = "")
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callCustomerDetail(this, auth_id , auth_token , Links.User_Type,cutsomer_id)
                .enqueue(object : Callback<CustomerDetailBaseResponse> {
                    override fun onResponse(call: Call<CustomerDetailBaseResponse>, response: Response<CustomerDetailBaseResponse>) {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1) {
                                set_data_profile(response)
                            }
                            else {
                                Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                            }
                        } else {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<CustomerDetailBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@OfferDetailActivity,main_layout,t.message)
                    }
                })
    }

    private fun set_data_profile(response: Response<CustomerDetailBaseResponse>)
    {
        whats_app_no_txt.setText(""+ response.body()!!.customerResult.customerContactNumber)

        working_days_detail_txt.setText(""+ response.body()!!.customerResult.customerShopWorkingDays)

        timings_detail_txt.setText(""+ response.body()!!.customerResult.customerShopStartTime +" - "+
                response.body()!!.customerResult.customerShopEndTime)

        lunch_timings_detail_txt.setText(""+ response.body()!!.customerResult.getmCustomerShopLunchStartTime() +" - "+
                response.body()!!.customerResult.getmCustomerShopLunchEndTime())

        operating_since_detail_txt.setText(""+ response.body()!!.customerResult.customerShopYear)

        other_service_txt.setText("Other Service - "+response.body()!!.customerResult.customerShopAbout)

    }

    private fun api_calling_for_query_detail(query_mes : String = "")
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callAddPromotionQuery(this, auth_id , auth_token , Links.User_Type,promotion_Id,
            cutsomer_id,query_mes)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                        else {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@OfferDetailActivity,main_layout,t.message)
                }
            })
    }


    private fun api_calling_for_PromotionLikeDislike(likeDislike :String = "")
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        // 1 = Like, 0 = Dislike
        ServiceCall.callPromotionLikeDislike(this, auth_id , auth_token , Links.User_Type,promotion_Id,
            likeDislike)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                        else {
                            Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@OfferDetailActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@OfferDetailActivity,main_layout,t.message)
                }
            })
    }

    override fun onBackPressed()
    {
        if(img_detail_layout.visibility==View.VISIBLE){
            img_detail_layout.visibility = View.GONE
        }
        else if(query_detail_layout.visibility==View.VISIBLE){
            query_detail_layout.visibility = View.GONE
        }
        else{
            finish()
        }
    }

}