package com.sngs.swayam.smartapp.Activity.companydetails

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.BaseResponse
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_contactus.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_contactus)

        init()
        click_fun()
    }


    private fun init() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val user_name = sharedPreferences.getString("User_name","")
        val user_no = sharedPreferences.getString("User_no","")

        et_name.setText(""+user_name)
        et_mobile.setText(""+user_no)
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }
        submit_img.setOnClickListener {
            finish()
        }

        phone1_rel.setOnClickListener {
            try{
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:7483475272")
                startActivity(intent)
            }
            catch (e: Exception){ }
        }

        phone2_rel.setOnClickListener {
            try{
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:6302519834")
                startActivity(intent)
            }
            catch (e: Exception){ }
        }

        email_id1_rel.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(tv_email_no1.text.toString()))
            i.putExtra(Intent.EXTRA_SUBJECT, "subject of email")
            i.putExtra(Intent.EXTRA_TEXT, "body of email")
            try {
                startActivity(Intent.createChooser(i, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this@ContactusActivity, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
            }
        }

        email_id2_rel.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(tv_email_no2.text.toString()))
            i.putExtra(Intent.EXTRA_SUBJECT, "subject of email")
            i.putExtra(Intent.EXTRA_TEXT, "body of email")
            try {
                startActivity(Intent.createChooser(i, "Send mail..."))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this@ContactusActivity, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validation() {
        if(et_name.text.toString().isEmpty()){
            clear_error()
            tl_name.error = resources.getString(R.string.name_error)
        }
        else if(et_mobile.text.toString().isEmpty()){
            clear_error()
            tl_mobile.error = resources.getString(R.string.mobile_error)
        }
        else if(et_messgae.text.toString().isEmpty()){
            clear_error()
            tl_messgae.error = resources.getString(R.string.message_error)
        }
        else{
            clear_error()
            api_calling_for_contact_us()
        }
    }

    private fun clear_error() {
        tl_name.error = null
        tl_mobile.error = null
        tl_email_id.error = null
        tl_messgae.error = null
    }

    private fun api_calling_for_contact_us()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callContactUS(this, auth_id , auth_token ,
            Links.User_Type,et_name.text.toString(), et_mobile.text.toString(), et_email_id.text.toString(), et_messgae.text.toString())
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {
                            Links.snack_bar(this@ContactusActivity,main_layout,response.body()?.message.toString())
                            withDelay(2000){
                                finish()
                            }
                        }
                        else {
                            Links.snack_bar(this@ContactusActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@ContactusActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@ContactusActivity,main_layout,t.message.toString())
                    Snackbar.make(main_layout, "" + t.message, Snackbar.LENGTH_LONG).show()
                }
            })
    }

    fun withDelay(delay: Long = 1000, block: () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
    }
}