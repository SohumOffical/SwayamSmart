package com.sngs.swayam.smartapp.User

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.shashank.sony.fancydialoglib.Animation
import com.shashank.sony.fancydialoglib.FancyAlertDialog
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener
import com.shashank.sony.fancydialoglib.Icon
import com.sngs.swayam.smartapp.Activity.area.State_City_List_Activity
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.MobileVerify.MobileVerifyBaseResponse
import com.sngs.swayam.smartapp.Network.model.UserSignUp.UserSignUpBaseResponse
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.et_Mobile
import kotlinx.android.synthetic.main.activity_signup.et_Name
import kotlinx.android.synthetic.main.activity_signup.et_cities
import kotlinx.android.synthetic.main.activity_signup.et_dob
import kotlinx.android.synthetic.main.activity_signup.et_email
import kotlinx.android.synthetic.main.activity_signup.et_service_area
import kotlinx.android.synthetic.main.activity_signup.et_state
import kotlinx.android.synthetic.main.activity_signup.main_layout
import kotlinx.android.synthetic.main.activity_signup.tl_Mobile
import kotlinx.android.synthetic.main.activity_signup.tl_Name
import kotlinx.android.synthetic.main.activity_signup.tl_cities
import kotlinx.android.synthetic.main.activity_signup.tl_dob
import kotlinx.android.synthetic.main.activity_signup.tl_email
import kotlinx.android.synthetic.main.activity_signup.tl_service_area
import kotlinx.android.synthetic.main.activity_signup.tl_state
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.android.synthetic.main.number_picker_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SignupActivity : AppCompatActivity() {

    var otp : String = ""
    var gender : String = ""
    var emailPattern : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    var fcm_token : String =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_signup)


        init()
        firebase_detail()
        click_fun()
    }

    private fun init()
    {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        picker_year.setMinValue(1900)
        picker_year.setMaxValue(3500)
        picker_year.setValue(year)

        et_dob.setText(""+year)

        et_state.setOnClickListener {
            val intent = Intent(this, State_City_List_Activity::class.java)
            intent.putExtra("page_type","0")
            startActivity(intent)
        }

        et_cities.setOnClickListener {
            val intent = Intent(this, State_City_List_Activity::class.java)
            intent.putExtra("page_type","1")
            startActivity(intent)
        }

        et_service_area.setOnClickListener {
            val intent = Intent(this, State_City_List_Activity::class.java)
            intent.putExtra("page_type","2")
            startActivity(intent)
        }

        et_dob.setOnClickListener {
            number_picker_rel.setVisibility(View.VISIBLE)
        }

        numberpicker_cancel_txt.setOnClickListener {
            number_picker_rel.setVisibility(View.GONE)
        }
        numberpicker_okay_txt.setOnClickListener {
            number_picker_rel.setVisibility(View.GONE)
        }

        picker_year.setOnValueChangedListener(NumberPicker.OnValueChangeListener { numberPicker, i, i1 ->
            et_dob.setText("" + numberPicker.value.toString())
        })
    }

    private fun click_fun() {

        btnContinue.setOnClickListener {
            validation()
        }

        login_in_rel.setOnClickListener {
            finish()
        }

        back_img.setOnClickListener {
            finish()
        }

        tv_female_rb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                tv_male_rb.isChecked = false
            }
        }

        tv_male_rb.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                tv_female_rb.isChecked = false
            }
        }

        terms_conidtion_txt.setOnClickListener {
            if(terms_conidtion_txt.isChecked){
                val intent = Intent(this, TermsconditionActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validation() {
        if(et_Name.text.toString().isEmpty()){
            clear_error()
            tl_Name.error = resources.getString(R.string.name_error)
        }
        else if(et_Mobile.text.toString().isEmpty()){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_error)
        }
        else if(et_Mobile.text.toString().length<10){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_lenght_error)
        }
        else if(et_email.text.toString().isEmpty()) {
            clear_error()
            tl_email.error = resources.getString(R.string.email_error)
        }
        if(!et_email.text.toString().isEmpty() && !et_email.text.toString().matches(Regex(emailPattern))){
            clear_error()
            tl_email.error = resources.getString(R.string.email_validation)
        }
        else if(et_dob.text.toString().isEmpty()) {
            clear_error()
            tl_dob.error = resources.getString(R.string.dob_error)
        }
        else if(et_state.text.toString().isEmpty()){
            clear_error()
            tl_state.error = resources.getString(R.string.state_error)
        }
        else if(et_cities.text.toString().isEmpty()){
            clear_error()
            tl_cities.error = resources.getString(R.string.cities_error)
        }
        else if(et_service_area.text.toString().isEmpty()){
            clear_error()
            tl_service_area.error = resources.getString(R.string.service_area_error)
        }
        else if(!terms_conidtion_txt.isChecked){
            clear_error()
            Links.snack_bar(this@SignupActivity, main_layout, resources.getString(R.string.agree_our_terms_condition))
        }
        else{
            clear_error()
            update_app_dilogbox(resources.getString(R.string.signup_mes))
        }
    }

    private fun clear_error() {
        tl_Name.error = null
        tl_Mobile.error = null
        tl_email.error = null
        tl_dob.error = null
        tl_state.error = null
        tl_cities.error = null
        tl_service_area.error = null
    }

    fun update_app_dilogbox(error: String?) {
        FancyAlertDialog.Builder(this@SignupActivity)
            .setTitle(resources.getString(R.string.text_you_want_to_swyam))
            .setMessage(error)
            .setPositiveBtnText(resources.getString(R.string.okay))
            .setNegativeBtnText(resources.getString(R.string.cancel))
            .setAnimation(Animation.POP)
            .isCancellable(false)
            .setIcon(R.drawable.app_logo, Icon.Visible)
            .OnPositiveClicked(object : FancyAlertDialogListener {
                override fun OnClick() {
                    api_calling_for_customer_sign_up()
                }
            })
            .OnNegativeClicked(object : FancyAlertDialogListener {
                override fun OnClick() {

                }
            })
            .build()
    }


    private fun api_calling_for_customer_sign_up()
    {
        loading_layout.setVisibility(View.VISIBLE)

        if(tv_female_rb.isChecked){
            gender = "female"
        }
        if(tv_male_rb.isChecked){
            gender = "male"
        }

        val device_id = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        ServiceCall.callUserSignUp(this,device_id,fcm_token, Links.User_Type, et_Name.text.toString(),
            et_Mobile.text.toString(),et_email.text.toString(),gender,et_dob.text.toString(),
            Links.selected_state_id, Links.selected_city_id, Links.selected_servies_id)
            .enqueue(object : Callback<UserSignUpBaseResponse> {
                override fun onResponse(call: Call<UserSignUpBaseResponse>, response: Response<UserSignUpBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1) {

                            Links.selected_state_id = ""
                            Links.selected_city_id = ""
                            Links.selected_servies_id = ""

                            Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())

                            val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
                            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                            editor.putString("Auth_ID",response.body()?.userDetail?.authId.toString())
                            editor.putString("Auth_Token",response.body()?.userDetail?.authToken)
                            editor.putString("User_name",response.body()?.userDetail?.userName)
                            editor.putString("User_no",response.body()?.userDetail?.userContactNumber)
                            editor.putString("Is_Login","True")
                            editor.commit()

                            api_calling_for_mobile_verify()

                        } else {
                            Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<UserSignUpBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@SignupActivity,main_layout,t.message)
                }
            })
    }


    private fun api_calling_for_mobile_verify()
    {
        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callMobileVerify(this, Links.User_Type,  et_Mobile.text.toString() )
            .enqueue(object : Callback<MobileVerifyBaseResponse> {
                override fun onResponse(call: Call<MobileVerifyBaseResponse>, response: Response<MobileVerifyBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success

                        if (success_v?.toInt()==1)
                        {
                            Links.snack_bar(this@SignupActivity,main_layout,response.body()?.otp.toString())
                            if(response.body()?.otp!=null){
                                otp = response.body()!!.otp.toString()
                            }
                            move_next_page()

                        } else {
                            Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@SignupActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<MobileVerifyBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@SignupActivity,main_layout,t.message)
                }
            })
    }

    private fun move_next_page() {
        val intent = Intent(this,   VerificationActivity::class.java)
        intent.putExtra("page_type","1")
        intent.putExtra("OTP",""+otp)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()

        et_state.setText(""+ Links.selected_state_name)
        et_cities.setText(""+ Links.selected_city_name)
        et_service_area.setText(""+ Links.selected_servies_name)

        if(!Links.selected_state_name.isEmpty()){
            city_li.visibility = View.VISIBLE
        }

        if(!Links.selected_city_name.isEmpty()){
            service_area_li.visibility = View.VISIBLE
        }

        if(Links.is_agree){
            terms_conidtion_txt.isChecked = true
        }
        else{
            terms_conidtion_txt.isChecked = false
        }
    }

    private fun firebase_detail()
    {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
            this
        ) { instanceIdResult: InstanceIdResult ->
            fcm_token = instanceIdResult.token
            Log.e("newToken", fcm_token)
        }
    }


}
