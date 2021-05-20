package com.sngs.swayam.smartapp.Activity.area

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_select_locality.*

class SelectLocalityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_select_locality)

        click_fun()
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }

        next_business_img.setOnClickListener {
            finish()
        }

    }
}
