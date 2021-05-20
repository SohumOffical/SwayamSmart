package com.sngs.swayam.smartapp.User

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sngs.swayam.smartapp.Activity.HomeActivity
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_verification.*


class VerificationActivity : AppCompatActivity() {

    var otp : String = "1234"
    var page_type : String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_verification)

        init()
        click_fun()
    }

    private fun init() {
        otp = intent.getStringExtra("OTP").toString()
        otp_view.otp = otp
    }

    private fun click_fun() {

        llVerify.setOnClickListener {
            if(otp_view.otp.isEmpty()) {
                Links.snack_bar(this@VerificationActivity,main_layout,""+resources.getString(R.string.empty_otp).toString())
            }
            else  if(!otp_view.otp.equals(otp)) {
                Links.snack_bar(this@VerificationActivity,main_layout,""+resources.getString(R.string.valid_otp).toString())
            }
            else {
                move_next_page()
            }
        }

        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun move_next_page() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("Is_Login","true")
        editor.commit()

        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

}
