package com.sngs.swayam.smartapp.Activity.usages

import android.Manifest
import android.annotation.TargetApi
import android.app.AppOpsManager
import android.app.AppOpsManager.OnOpChangedListener
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import bot.box.appusage.contract.UsageContracts
import bot.box.appusage.model.AppData
import com.google.android.material.tabs.TabLayout
import com.sngs.swayam.smartapp.Activity.fragments.fragment_monthly
import com.sngs.swayam.smartapp.Activity.fragments.fragment_today
import com.sngs.swayam.smartapp.Activity.fragments.fragment_weekly
import com.sngs.swayam.smartapp.Adapter.usages.AppAdapter
import com.sngs.swayam.smartapp.R
import java.util.*

class AppDataUsagesActivity : AppCompatActivity() , UsageContracts.View, OnItemSelectedListener {

    private val mAdapter: AppAdapter? = null
    private val READ_PHONE_STATE_REQUEST = 37
    val EXTRA_PACKAGE = "ExtraPackage"
    var x: Boolean? = null

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

    private val tabIcons = arrayOf(
        "Today",
        "This Week",
        "This Month"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_app_data_usages)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                100
            )
        }


        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById<View>(R.id.view_pager) as ViewPager
        viewPager!!.offscreenPageLimit = 3

        viewPagerAdapter.addFragment(fragment_today())
        viewPagerAdapter.addFragment(fragment_weekly())
        viewPagerAdapter.addFragment(fragment_monthly())

        viewPager!!.adapter = viewPagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)
        tabLayout!!.setSelectedTabIndicatorColor(Color.parseColor("#fad1b6"))

        setupTabIcons()
        x = false
    }

    private fun setupTabIcons() {
        tabLayout!!.getTabAt(0)!!.text = tabIcons[0]
        tabLayout!!.getTabAt(1)!!.text = tabIcons[1]
        tabLayout!!.getTabAt(2)!!.text = tabIcons[2]
    }

    override fun onResume() {
        super.onResume()
        if (!hasPermissions()) {
            return
        }
    }


    override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
//        Monitor.scan().getAppLists(this,System.currentTimeMillis(),System.currentTimeMillis()).fetchFor(i);
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {}

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun getUsageData(usageData: List<AppData?>?, mTotalUsage: Long, duration: Int) {
        mAdapter!!.updateData(usageData)
    }

    private fun hasPermissions(): Boolean {
        if (hasPermissionToReadNetworkHistory() && hasPermissionToReadPhoneStats()) {
            x = true
        }
        return hasPermissionToReadNetworkHistory() && hasPermissionToReadPhoneStats()
    }

    private fun hasPermissionToReadPhoneStats(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            false
        } else {
            true
        }
    }

    private fun hasPermissionToReadNetworkHistory(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), packageName)
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true
        }
        appOps.startWatchingMode(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationContext.packageName,
            object : OnOpChangedListener {
                @TargetApi(Build.VERSION_CODES.M)
                override fun onOpChanged(op: String, packageName: String) {
                    val mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), getPackageName())
                    if (mode != AppOpsManager.MODE_ALLOWED) {
                        return
                    }
                    appOps.stopWatchingMode(this)
                    val intent = Intent(this@AppDataUsagesActivity, AppDataUsagesActivity::class.java)
                    if (getIntent().extras != null) {
                        intent.putExtras(getIntent().extras!!)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    applicationContext.startActivity(intent)
                }
            })
        requestReadNetworkHistoryAccess()
        return false
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun requestReadNetworkHistoryAccess() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }


    class ViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {
        private val fragments: ArrayList<Fragment>
        var position_1 = 0

        override fun getItem(position: Int): Fragment {
            position_1 = position
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // Yet another bug in FragmentStatePagerAdapter that destroyItem is called on fragment that hasnt been added. Need to catch
            try {
                super.destroyItem(container, position, `object`)
            } catch (ex: IllegalStateException) {
                ex.printStackTrace()
            }
        }

        fun addFragment(fragment: Fragment) {
            fragments.add(fragment)
        }

        init {
            fragments = ArrayList()
        }
    }
}