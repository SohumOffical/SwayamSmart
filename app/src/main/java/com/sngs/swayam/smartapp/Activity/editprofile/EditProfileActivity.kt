package com.sngs.swayam.smartapp.Activity.editprofile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.BaseResponse
import com.sngs.swayam.smartapp.Network.model.UserDetail.UserDetailBaseResponse
import com.sngs.swayam.smartapp.Network.model.UserDetail.UserResult
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import com.yalantis.ucrop.UCrop
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.main_layout
import kotlinx.android.synthetic.main.activity_login.et_Mobile
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.banner_item_layout.view.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.ArrayList

class EditProfileActivity : AppCompatActivity() {

    var profile_File : File? = null
    var gender : String = ""
    var MAX_ATTACHMENT_COUNT = 1
    var emailPattern : String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_edit_profile)

        init()
        click_fun()
    }


    private fun init()
    {
        api_calling_for_user_detail()
    }

    private fun click_fun()
    {
        ivBack.setOnClickListener {
            finish()
        }
        btn_submit.setOnClickListener {
            validation()
        }

        profile_rel2.setOnClickListener {
            onPickPhoto()
        }
    }

    private fun validation()
    {
        if(!et_email_detail.text.toString().isEmpty() && !et_email_detail.text.toString().matches(Regex(emailPattern))){
            tl_email_detail.error = resources.getString(R.string.email_validation)
        }
        else{
            tl_email_detail.error = null
            api_calling_for_edit_profile()
        }
    }


    private fun api_calling_for_edit_profile()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)


        ServiceCall.callEditUserProfile(this,auth_id, auth_token,
                Links.User_Type,et_Name_detail.text.toString(),et_Mobile_detail.text.toString(),
                et_email_detail.text.toString(),
                et_gender_detail.text.toString(), et_dob_detail.text.toString(), Links.selected_state_id,
                Links.selected_city_id, Links.selected_servies_id,profile_File)
            .enqueue(object : Callback<BaseResponse> {
                override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt()==1)
                        {
                            Links.snack_bar(this@EditProfileActivity,main_layout,response.body()?.message.toString())
                            withDelay(2000) {
                                finish()
                            }
                        }
                        else
                        {
                            Links.snack_bar(this@EditProfileActivity,main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@EditProfileActivity,main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@EditProfileActivity,main_layout, t.message.toString())
                }
            })
    }

    private fun api_calling_for_user_detail()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callUserDetail(this, auth_id, auth_token, Links.User_Type)
                .enqueue(object : Callback<UserDetailBaseResponse> {
                    override fun onResponse(call: Call<UserDetailBaseResponse>, response: Response<UserDetailBaseResponse>) {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1)
                            {
                                if(response.body()!!.userResult!=null){
                                    set_data(response.body()!!.userResult)
                                }
                            }
                            else {
                                Links.snack_bar(this@EditProfileActivity,main_layout,response.body()?.message.toString())
                            }
                        } else {
                            Links.snack_bar(this@EditProfileActivity,main_layout,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<UserDetailBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@EditProfileActivity,main_layout,t.message.toString())
                    }
                })
    }

    private fun set_data(userResult: UserResult?)
    {
        et_Name_detail.setText(""+ userResult!!.userName)
        et_Mobile_detail.setText(""+ userResult!!.userContactNumber)
        et_email_detail.setText(""+ userResult!!.userEmail)

        if(!userResult!!.userDateOfBirth.equals("0")){
            et_dob_detail.setText(""+ userResult!!.userDateOfBirth)
        }
        et_state_detail.setText(""+ userResult!!.userStateName)
        et_cities_detail.setText(""+ userResult!!.userCityName)
        et_service_area_detail.setText(""+ userResult!!.userAreaName)
        Links.selected_state_id = userResult!!.userStateId
        Links.selected_city_id = userResult!!.userCityId
        Links.selected_servies_id = userResult!!.userAreaId

        Glide.with(applicationContext).load(userResult!!.userProfilePicure)
                .placeholder(R.drawable.profile_icon)
                .into(img2);

        if(userResult!!.userGender.equals("male")){
            gender = "male"
            et_gender_detail.setText(""+gender)
        }
        else{
            gender = "female"
            et_gender_detail.setText(""+gender)
        }
    }


    fun onPickPhoto() {
        Links.selected_image_array_list.clear()
        try {
            val maxCount: Int = MAX_ATTACHMENT_COUNT
            if (Links.selected_image_array_list.size == MAX_ATTACHMENT_COUNT) {
                Toast.makeText(
                        this@EditProfileActivity,
                        "Cannot select more than $MAX_ATTACHMENT_COUNT items",
                        Toast.LENGTH_SHORT
                ).show()
            } else {
                FilePickerBuilder.instance
                        .setMaxCount(maxCount)
                        .setSelectedFiles(Links.selected_image_array_list)
                        .setActivityTheme(R.style.FilePickerTheme)
                        .setActivityTitle("Select Image / Video")
                        .showGifs(false)
                        .setMaxCount(1)
                        .enableImagePicker(true)
                        .enableVideoPicker(true)
                        .showFolderView(true)
                        .enableSelectAll(true)
                        .setCameraPlaceholder(R.drawable.ic_camera)
                        .withOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .pickPhoto(this)
            }
        } catch (e: java.lang.Exception) {}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            Links.selected_image_array_list.clear()
            Links.selected_image_array_list.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA)!!)

            addThemToView(Links.selected_image_array_list)
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            data?.let { handleCropError(it) };
        }
        else if (requestCode == UCrop.REQUEST_CROP) {
            data?.let { handleCropResult(it) };
        }

    }

    private fun addThemToView(imagePaths: ArrayList<String>?) {
        val filePaths = ArrayList<String>()

        if (imagePaths != null)
            filePaths.addAll(imagePaths)

        val mStringArray: Array<Any> = filePaths.toTypedArray()

        Log.e("selectedImagePath", "" + imagePaths!!.size)

        for (i in mStringArray.indices) {
            Log.e("selectedImagePath", "" + mStringArray[i] as String)
            val qc_report_string = mStringArray[i] as String

            profile_File = File(qc_report_string)
            if (profile_File != null) {
                Log.e("", "not null")
                val uri = Uri.fromFile(profile_File)
                startCrop(uri);
               // img2.setImageURI(uri)
            }
            else {
                Log.e("selectedImagePath", "null")
            }
        }
    }

    fun withDelay(delay: Long = 1000, block: () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
    }

    private fun startCrop(uri: Uri) {
        var destinationFileName: String = "Abc"
        destinationFileName += ".png"
        var uCrop: UCrop = UCrop.of(
            uri,
            Uri.fromFile(File(cacheDir, destinationFileName))
        )
        uCrop.start(this@EditProfileActivity)
    }

    private fun handleCropResult(@NonNull result: Intent) {
        val resultUri: Uri = UCrop.getOutput(result)!!
        if (resultUri != null) {
            profile_File = File(resultUri.getPath())
            img2.setImageURI(resultUri)
        } else {
            Toast.makeText(this@EditProfileActivity, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleCropError(@NonNull result: Intent) {
        val cropError: Throwable = UCrop.getError(result)!!
        if (cropError != null) {
            Log.e("Naimee", "handleCropError: ", cropError)
            Toast.makeText(this@EditProfileActivity, cropError.message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@EditProfileActivity, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show()
        }
    }


}