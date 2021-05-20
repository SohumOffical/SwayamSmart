package com.sngs.swayam.smartapp.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sngs.swayam.smartapp.Network.WebUtlis.Links;
import com.sngs.swayam.smartapp.R;

public class TermsconditionActivity extends AppCompatActivity {

    //loading
    RelativeLayout main_layout,loading_layout;

    // for PDF view.
    TextView term_condition_txt;
    ImageView ivBack;
    LinearLayout select_continue_li;
    RadioButton agree_txt,disagree_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_termscondition);

        init();
        click_fun();
    }

    private void init() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        term_condition_txt = (TextView) findViewById(R.id.term_condition_txt);
        main_layout = findViewById(R.id.main_layout);
        loading_layout = findViewById(R.id.loading_layout);
        select_continue_li = findViewById(R.id.select_continue_li);
        agree_txt = findViewById(R.id.agree_txt);
        disagree_txt = findViewById(R.id.disagree_txt);
    }

    private void click_fun() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        select_continue_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!agree_txt.isChecked()){
                    Links.snack_bar(TermsconditionActivity.this,main_layout,getResources().getString(R.string.agree_our_terms_condition));
                }
                else{
                    Links.is_agree = true;
                    finish();
                }
            }
        });

        agree_txt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    disagree_txt.setChecked(false);
                }
            }
        });
        disagree_txt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    agree_txt.setChecked(false);
                }
            }
        });


        term_condition_txt.setText("This Privacy Policy governs the manner in which SOHUM NEXT GEN SOLUTIONS PVT LTD. collects, uses, maintains and discloses information collected from users (each, a “User”) of the Sohumngs website (“Site”) or our mobile applications (“Apps”). This privacy policy applies to the Site, Apps, and all products and services offered by Sohum next gen solutions Pvt Ltd.\n" +
                "\n" +
                "Non-personal identification information\n" +
                "We may collect non-personal identification information about Users whenever they interact with our Site or our Apps. Non-personal identification information may include the browser name, the type of computer or mobile device, and technical information about Users means of connection to our Site or use of our Apps, such as the operating system and the Internet service providers utilized and other similar information.\n" +
                "Personal identification information\n" +
                "We do not collect and/or transmit your personal identification information to our Site.\n" +
                "Location information\n" +
                "Some of our Apps may have the ability read your location from your mobile device and may use this information to enhance the functionality of the app. However, your location information won't be transmitted to our Site.\n" +
                "How we use collected information\n" +
                "SOHUM NEXT GEN SOLUTIONS PVT LTD collects and uses Users information for the following purposes:\n" +
                "To personalize user experience\n" +
                "We may use information in the aggregate to understand how our Users as a group use the services and resources provided on our Site or in our Apps.\n" +
                "To improve our Site and Apps\n" +
                "We continually strive to improve our website offerings and our Apps based on the information and feedback we receive from you.\n" +
                "To improve customer service\n" +
                "Your information helps us to more effectively respond to your customer service requests and support needs.\n" +
                "To administer a content, promotion, survey or other Site or App feature\n" +
                "To send Users information they agreed to receive about topics we think will be of interest to them.\n" +
                "Third party websites\n" +
                "Users may find advertising or other content on our Site or in our Apps that link to the sites and services of our partners, suppliers, advertisers, sponsors, licensors and other third parties. We do not control the content or links that appear on these sites and are not responsible for the practices employed by websites linked to or from our Site. In addition, these sites or services, including their content and links, may be constantly changing. These sites and services may have their own privacy policies and customer service policies. Browsing and interaction on any other website, including websites which have a link to our Site, is subject to that website’s own terms and policies. Here are links to the privacy policies of a few of the distributors we use:\n" +
                "Analytics\n" +
                "We use Google Analytics and/or third parties to collect data from, control and/or monitor computers and devices running or interacting with the Apps or our Site. The collected data is anonymously and used to help us understand how customers engage with this application. It reports application trends without identifying individual customers. SOHUM NEXT GEN SOLUTIONS PVT LTD reserve the right to anonymously track and report your activity inside of our applications. SOHUM NEXT GEN SOLUTIONS PVT LTD will not share this information with other customers, or any third parties.\n" +
                "Advertising\n" +
                "For users in the India, only non-personalized ads will be displayed on our Site. Non-personalized ads are targeted using contextual information rather than the past behavior of you. Although these ads don’t use cookies for ads personalization, they do use cookies to allow for frequency capping, aggregated ad reporting, and to combat fraud and abuse. Consent is therefore required to use cookies for those purposes. Ads appearing in our Apps, a consent dialog will be displayed for India users to select personalized or non-personalized ads type. Ads appearing on our Site or in our Apps may be delivered to Users by advertising partners, who may use of an advertising id on your mobile device. These ids allow the ad server to recognize your computer or mobile device each time they send you an online advertisement to compile non personal identification information about you or others who use your computer or mobile device. This information allows ad networks to, among other things, deliver targeted advertisements that they believe will be of most interest to you. This privacy policy does not cover the use of ids by any advertisers. Apps published on Google Play may make use of the advertising identifier on your mobile device for advertising purposes in accordance with Google Play Policy. The advertising identifier may be used by advertisers to track user interaction with ads unless the user opts out of this capability using their device’s advertising identifier settings. Apps published on Google Play on or after August 1st 2014, will make exclusive use of the advertising identifier on your mobile device for all advertising purposes.\n" +
                "Changes to this privacy policy\n" +
                "SOHUM NEXT GEN SOLUTIONS PVT LTD has the discretion to update this privacy policy at any time. When we do, we will revise the updated date at the bottom of this page. We encourage Users to frequently check this page for any changes to stay informed about how we are helping to protect the personal information we collect. You acknowledge and agree that it is your responsibility to review this privacy policy periodically and become aware of modifications.\n" +
                "Your acceptance of these terms\n" +
                "By using this Site or our Apps, you signify your acceptance of this policy. If you do not agree to this policy, please do not use our Site or Apps. Your continued use of the Site and/or Apps following the posting of changes to this policy will be deemed your acceptance of those changes.\n" +
                "Contacting us\n" +
                "If you have any questions about this Privacy Policy, the practices of this site, or your dealings with this site, please contact us at: info@sohumngs.com.\n");
    }

}