package com.sngs.swayam.smartapp.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.naimee.swayam.utlis.theme3bottomnavigation.BottomNavigation
import com.sngs.swayam.smartapp.Activity.companydetails.AboutUsActivity
import com.sngs.swayam.smartapp.Activity.companydetails.ContactusActivity
import com.sngs.swayam.smartapp.Activity.datausage.DataUsagesActivity
import com.sngs.swayam.smartapp.Activity.editprofile.EditProfileActivity
import com.sngs.swayam.smartapp.Activity.notification.NotificationActivity
import com.sngs.swayam.smartapp.Activity.offer.OfferActivity
import com.sngs.swayam.smartapp.Activity.usages.AppDataUsagesActivity
import com.sngs.swayam.smartapp.Adapter.home.BannerListAdapter
import com.sngs.swayam.smartapp.Adapter.notes.NotesListAdapter
import com.sngs.swayam.smartapp.Adapter.offer.OfferListAdapter
import com.sngs.swayam.smartapp.Network.WebUtlis.Links
import com.sngs.swayam.smartapp.Network.model.AdvertismentList.AdvertismentListBaseResponse
import com.sngs.swayam.smartapp.Network.model.BannerList.BannerListBaseResponse
import com.sngs.swayam.smartapp.Network.model.PromotionList.GetCustomerPromotionListBaseResponse
import com.sngs.swayam.smartapp.Network.model.UserDetail.UserDetailBaseResponse
import com.sngs.swayam.smartapp.Network.model.UserDetail.UserResult
import com.sngs.swayam.smartapp.Network.servicecall.ServiceCall
import com.sngs.swayam.smartapp.R
import com.sngs.swayam.smartapp.User.LoginActivity
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    var speedScroll : Long = 50000;

    companion object {
        private const val ID_HOME = 1
        private const val ID_MARKET = 2
        private const val ID_UTILITIES = 3
        private const val ID_NOTIFICATION = 4
        private const val ID_SHARE = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_drawer)

        init()
        set_drawer()
        click_fun()
        profile_data()
    }

    private fun profile_data()
    {
        val notes_rv = findViewById(R.id.notes_rv) as RecyclerView
        notes_rv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

        var mb_used = Links.networkUsage(this@HomeActivity)
        net_used_txt.setText(""+mb_used)

      /*  val packageManager: PackageManager = getPackageManager()
        val info: ApplicationInfo = packageManager.getApplicationInfo("com.sngs.swayam.smartapp", 0)
        val uid: Int = info.uid

        var data_usage = android.net.TrafficStats.getMobileRxBytes().toString();
        Log.e("App_Data_Usage"," "+android.net.TrafficStats.getMobileRxBytes().toString()+"Bytes")
        var app_data_usage = Links.getFileSize(data_usage.toLong())*/

    }

    private fun init()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                100
            )
        }

        Links.dummy_list.clear()
        Links.dummy_list.add("1")
        Links.dummy_list.add("2")
        Links.dummy_list.add("3")
        tabLayout_1.setTabData(Links.mTitles);

        val your_offer_rv = findViewById(R.id.your_offer_rv) as RecyclerView
        your_offer_rv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,false)

        val banner_rv = findViewById(R.id.banner_rv) as RecyclerView
        banner_rv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL,false)

        bottomNavigation.add(
            BottomNavigation.Model(
                ID_HOME,
                R.drawable.home_icon
            ))
        bottomNavigation.add(
            BottomNavigation.Model(
                ID_MARKET,
                R.drawable.market_icon
            ))
        bottomNavigation.add(
            BottomNavigation.Model(
                ID_UTILITIES,
                R.drawable.utility_icon
            ))
        bottomNavigation.add(
            BottomNavigation.Model(
                ID_NOTIFICATION,
                R.drawable.ic_notification
            ))
        bottomNavigation.add(
            BottomNavigation.Model(
                ID_SHARE,
                R.drawable.ic_share_icon
            ))

        bottomNavigation.setOnShowListener {
            val name = when (it.id) {
                ID_HOME -> "HOME"
                ID_MARKET -> "MARKET"
                ID_UTILITIES -> "UTILITIES"
                ID_NOTIFICATION -> "NOTIFICATION"
                ID_SHARE -> "SHARE"
                else -> ""
            }
        }


        bottomNavigation.setOnClickMenuListener {
            val name = when (it.id) {
                ID_HOME -> "HOME"
                ID_MARKET -> "MARKET"
                ID_UTILITIES -> "UTILITIES"
                ID_NOTIFICATION -> "NOTIFICATION"
                ID_SHARE -> "SHARE"
                else -> ""
            }

            if(name.equals("MARKET")){ }

            if(name.equals("UTILITIES")){ }

            if(name.equals("NOTIFICATION")){
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
            }

        }

        Handler().postDelayed(Runnable {
            bottomNavigation.show(ID_HOME,true)
        },800)


        api_calling_for_user_promotion_list()
        api_calling_for_banner_list()
        api_calling_for_advertisment_list()
    }


    private fun click_fun() {
        logout_li1.setOnClickListener {
            logout()
        }

        profile_li.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        view_more_rel.setOnClickListener {
            val intent = Intent(this, OfferActivity::class.java)
            startActivity(intent)
        }

        data_usage_rel.setOnClickListener {
            val intent = Intent(this, AppDataUsagesActivity::class.java)
            startActivity(intent)
        }

        app_usage_rel.setOnClickListener {
            val intent = Intent(this, AppDataUsagesActivity::class.java)
            startActivity(intent)
        }

        about_us_li1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }


        contact_us_li1.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, ContactusActivity::class.java)
            startActivity(intent)
        }

        notification_li.setOnClickListener {
            drawerlayout.closeDrawer(GravityCompat.START)
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(Gravity.LEFT)
        } else {
            back_dialog_box()
        }
    }

    private fun back_dialog_box(){
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(R.string.app_name)

        //set message for alert dialog
        builder.setMessage(R.string.back_mes)
        builder.setIcon(R.drawable.app_logo)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            finish()
        }

        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->

        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun set_drawer() {
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerlayout,
            R.string.open,
            R.string.close
        ) {
            private val scaleFactor = 10f
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = drawerView.width * slideOffset
                content.translationX = slideX
                content.scaleX = 1 - slideOffset / scaleFactor
                content.scaleY = 1 - slideOffset / scaleFactor
            }
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                btnMenu.setImageResource(R.drawable.ic_drawer)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                btnMenu.setImageResource(R.drawable.ic_back_arrow)
            }
        }

        drawerlayout.setScrimColor(Color.TRANSPARENT)
        drawerlayout.drawerElevation = 0f
        drawerlayout.addDrawerListener(actionBarDrawerToggle)

        btnMenu.setOnClickListener {
            drawerlayout.openDrawer(GravityCompat.START);
            setStatusBarGradient()
        }
    }

    fun setStatusBarGradient(color: Int = R.color.white) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val window = window
                var flags = window.decorView.systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.decorView.systemUiVisibility = flags
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(this, color)
            }
            else -> {
                window.statusBarColor =  ContextCompat.getColor(this,
                    R.color.view_color
                )
            }
        }
    }

    private  fun logout() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("Auth_ID","")
        editor.putString("Auth_Token","")
        editor.putString("User_name","")
        editor.putString("User_no","")
        editor.putString("Is_Login","false")
        editor.commit()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun api_calling_for_banner_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetBannerList(this, auth_id, auth_token, Links.User_Type)
                .enqueue(object : Callback<BannerListBaseResponse> {
                    override fun onResponse(call: Call<BannerListBaseResponse>, response: Response<BannerListBaseResponse>) {
                        loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            Links.Banner_list.clear()
                            if (success_v?.toInt()==1)
                            {
                                if(response.body()!!.bannerListResult!=null){
                                    Links.Banner_list = response.body()!!.bannerListResult
                                    banner_rv.adapter = BannerListAdapter(Links.Banner_list,this@HomeActivity)
                                }
                            }
                            else {
                                Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                            }
                        } else {
                            Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<BannerListBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@HomeActivity,content,t.message.toString())
                    }
                })
    }

    private fun api_calling_for_advertisment_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

      //  loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callAdvertismentList(this, auth_id, auth_token, Links.User_Type)
                .enqueue(object : Callback<AdvertismentListBaseResponse> {
                    override fun onResponse(call: Call<AdvertismentListBaseResponse>, response: Response<AdvertismentListBaseResponse>) {
                   //     loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            Links.Advertisment_list.clear()
                            if (success_v?.toInt()==1)
                            {
                                if(response.body()!!.promotionBannerListResult!=null){
                                    Links.Advertisment_list = response.body()!!.promotionBannerListResult
                                    notes_rv.adapter = NotesListAdapter(Links.Advertisment_list,this@HomeActivity)
                                }
                            }
                            else {
                                Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                            }
                        } else {
                            Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<AdvertismentListBaseResponse>, t: Throwable) {
                     //   loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@HomeActivity,content,t.message.toString())
                    }
                })
    }

    private fun api_calling_for_user_promotion_list()
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID","")
        val auth_token = sharedPreferences.getString("Auth_Token","")

        //loading_layout.setVisibility(View.VISIBLE)
        ServiceCall.callUserPromotionList(this, auth_id, auth_token, Links.User_Type,"0")
                .enqueue(object : Callback<GetCustomerPromotionListBaseResponse> {
                    override fun onResponse(call: Call<GetCustomerPromotionListBaseResponse>, response: Response<GetCustomerPromotionListBaseResponse>) {
                        //loading_layout.setVisibility(View.GONE)
                        if (response.isSuccessful()) {
                            val success_v = response.body()?.success
                            if (success_v?.toInt()==1)
                            {
                                if(response.body()!!.promotionListResult!=null){
                                    Links.PromotionResult_list.clear()
                                    Links.PromotionResult_list = response.body()!!.promotionListResult
                                    your_offer_rv.adapter = OfferListAdapter(Links.PromotionResult_list,this@HomeActivity,"0")
                                }
                            }
                            else {
                              //  Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                            }
                        } else {
                           // Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<GetCustomerPromotionListBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@HomeActivity,content,t.message.toString())
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
                                Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                            }
                        } else {
                            Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                        }
                    }
                    override fun onFailure(call: Call<UserDetailBaseResponse>, t: Throwable) {
                        loading_layout.setVisibility(View.GONE)
                        Links.snack_bar(this@HomeActivity,content,t.message.toString())
                    }
                })
    }

    private fun set_data(userResult: UserResult?) {
        user_name_txt.setText(""+ userResult!!.userName)
        tv_user_name.setText(""+userResult!!.userName)
        tv_user_mobile.setText(""+userResult!!.userContactNumber)
        coins.setText(""+userResult!!.getmUserAvailableCoins())
        Glide.with(applicationContext).load(userResult!!.userProfilePicure).placeholder(
            R.drawable.profile_icon).into(profile_img);
        Glide.with(applicationContext).load(userResult!!.userProfilePicure)
            .placeholder(R.drawable.profile_icon)
                .into(profile_user_img);
    }

    override fun onResume() {
        super.onResume()
        api_calling_for_user_promotion_list()
        api_calling_for_banner_list()
        api_calling_for_advertisment_list()
        api_calling_for_user_detail()
    }

}
