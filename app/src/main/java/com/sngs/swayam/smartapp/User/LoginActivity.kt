package com.sngs.swayam.smartapp.User

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sngs.swayam.smartapp.Activity.HomeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.UserSignIn.UserSignInBaseResponse
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    var otp : String = ""
    var fcm_token : String =""

    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_login)

        init()
        firebase_detail()
        click_fun()

        if(checkAndRequestPermissions()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return
            }
        }
    }

    private fun init() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val is_login = sharedPreferences.getString("Is_Login","false")
        if(is_login.equals("true")){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun click_fun() {

        btnContinue.setOnClickListener {
            validation()
        }


        btnsignup.setOnClickListener {
            clear_error()
            et_Mobile.text?.clear()
            et_Mpin.text?.clear()
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validation() {
        if(et_Mobile.text.toString().isEmpty()){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_error)
        }
        else if(et_Mobile.text.toString().length<10){
            clear_error()
            tl_Mobile.error = resources.getString(R.string.mobile_lenght_error)
        }
        else{
            clear_error()
            api_calling_for_customer_sign_in()
        }
    }

    private fun clear_error() {
        tl_Mobile.error = null
    }

    private fun api_calling_for_customer_sign_in()
    {
        val device_id = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callUserSignIn(this, device_id,fcm_token,
            Links.User_Type, et_Mobile.text.toString())
            .enqueue(object : Callback<UserSignInBaseResponse> {
                override fun onResponse(call: Call<UserSignInBaseResponse>, response: Response<UserSignInBaseResponse>) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success

                        if (success_v?.toInt()==1)
                        {
                            if(response.body()?.userData!!.otp!=null){
                                otp = response.body()?.userData!!.otp.toString()
                            }
                            Links.snack_bar(this@LoginActivity,login_main_layout,response.body()?.message.toString())
                            move_next_page(response)
                        } else {
                            Links.snack_bar(this@LoginActivity,login_main_layout,response.body()?.message.toString())
                        }
                    } else {
                        Links.snack_bar(this@LoginActivity,login_main_layout,response.body()?.message.toString())
                    }
                }
                override fun onFailure(call: Call<UserSignInBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@LoginActivity,login_main_layout, t.message.toString())
                }
            })
    }

    private fun move_next_page(response: Response<UserSignInBaseResponse>) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("Auth_ID",response.body()?.userData?.authId)
        editor.putString("Auth_Token",response.body()?.userData?.authToken)
        editor.putString("User_name",response.body()?.userData?.name)
        editor.putString("User_no",response.body()?.userData?.contactNumber)
        editor.putString("Is_Login","false")
        editor.commit()

        val intent = Intent(this, VerificationActivity::class.java)
        intent.putExtra("page_type","0")
        intent.putExtra("OTP",""+otp)
        startActivity(intent)
    }

    private fun checkAndRequestPermissions(): Boolean {
        val permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val read_permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val camera_Permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val listPermissionsNeeded: MutableList<String> = ArrayList()

        if (camera_Permission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }

        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (read_permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.e("Permission", "Permission callback called-------")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                val perms: MutableMap<String, Int> = HashMap()
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                if (grantResults.size > 0) {
                    var i = 0
                    while (i < permissions.size) {
                        perms[permissions[i]] = grantResults[i]
                        i++
                    }
                    if (perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED &&
                        perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED &&
                        perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED){
                        Log.e("Permission", "sms & location services permission granted")
                    } else {
                        Log.e("Permission", "Some permissions are not granted ask again ")
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
                        {
                            showDialogOK("Storage and Camera Permission required for this app",
                                DialogInterface.OnClickListener { dialog, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                        DialogInterface.BUTTON_NEGATIVE -> {
                                            Snackbar.make(main_layout, "Go to settings and enable permissions" , Snackbar.LENGTH_LONG).show()
                                            withDelay(3000) {}
                                            finish()
                                        }
                                    }
                                })
                        } else {
                            Snackbar.make(main_layout, "Go to settings and enable permissions" , Snackbar.LENGTH_LONG).show()
                            withDelay(1000) {}
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

    fun withDelay(delay: Long = 1000, block: () -> Unit) {
        Handler().postDelayed(Runnable(block), delay)
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
