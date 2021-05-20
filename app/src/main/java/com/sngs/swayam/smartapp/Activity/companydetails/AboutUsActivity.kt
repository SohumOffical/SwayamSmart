package com.sngs.swayam.smartapp.Activity.companydetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sngs.swayam.smartapp.R
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_about_us)

        init()
        click_fun()
    }


    private fun init() {
        tv_about_us.setText("Sohum Next Gen Solutions is a startup android app development company in Bhubaneswar registered under Startup Odisha that delivers sophisticated app solutions.\n" +
                "We offer full-stack IOS and Android mobile app development services and have a team of skilled professionals that design, develop and support innovative custom mobile applications. We can develop native apps in IOS and Android or choose the cross-platform mobile app development route like React Native and Flutter offering diversity and cost-effective solutions to our clients. We adopt a user-focused design thinking process and agile methodology in our projects and focus on minimal UX while developing IOS or Android apps .We ensure your end-users to experience the industry-best technologies incorporated into the apps we build. Being a well-versed software entity, we are efficient at developing visually pleasing apps with seamless user navigational flows. We understand your business processes and incubate them into scalable and reliable mobile apps to let you vision multi-fold business growth.")
    }

    private fun click_fun() {
        ivBack.setOnClickListener {
            finish()
        }
    }

}