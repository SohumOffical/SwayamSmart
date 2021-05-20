package com.sngs.swayam.smartapp.Activity.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sngs.swayam.smartapp.Adapter.usages.AppAdapter;
import com.sngs.swayam.smartapp.R;


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import bot.box.appusage.contract.UsageContracts;
import bot.box.appusage.handler.Monitor;
import bot.box.appusage.model.AppData;
import bot.box.appusage.utils.Duration;
import bot.box.appusage.utils.UsageUtils;


public class fragment_weekly extends Fragment implements UsageContracts.View, AdapterView.OnItemSelectedListener {

    private static fragment_weekly instance;
    PieChart pieChart;
    private AppAdapter mAdapter;
    RecyclerView mRecycler;
    int maxpack;
    ImageView maximg;
    TextView maxtext,apptext,date;
    CardView card;

    public fragment_weekly() {
        // Required empty public constructor
    }

    public static synchronized fragment_weekly getInstance() {
        if (instance == null)
            instance = new fragment_weekly();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_weekly, container, false);

        mRecycler = itemView.findViewById(R.id.recycler);
        pieChart = itemView.findViewById(R.id.piechart);
        maximg = itemView.findViewById(R.id.maximg);
        maxtext = itemView.findViewById(R.id.maxtext);
        card = itemView.findViewById(R.id.card);
        apptext = itemView.findViewById(R.id.apptext);
        date = itemView.findViewById(R.id.date);


        // Inflate the layout for this fragment
        return itemView;
    }


    @SuppressLint("WrongConstant")
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResume() {
        super.onResume();
        if (Monitor.hasUsagePermission()) {

            pieChart.setVisibility(View.INVISIBLE);
            card.setVisibility(View.GONE);
            apptext.setVisibility(View.GONE);
            date.setVisibility(View.GONE);

            Monitor.scan().getAppLists(this).fetchFor(Duration.WEEK);
            init();
        } else {
            Monitor.requestUsagePermission();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Monitor.scan().getAppLists(this,System.currentTimeMillis(),System.currentTimeMillis()).fetchFor(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    PieModel largest(ArrayList<PieModel> arr) {
        int i;
        PieModel max = arr.get(0);
        for (i = 1; i < arr.size(); i++)
            if (arr.get(i).getValue() > max.getValue()){
                max = arr.get(i);
                maxpack=i;
            }
        return max;
    }

    @Override
    public void getUsageData(List<AppData> usageData, long mTotalUsage, int duration) {
        mAdapter.updateData(usageData);

        pieChart.setVisibility(View.VISIBLE);

        Random rnd = new Random();
        ArrayList<PieModel> x=new ArrayList<>();
        for(AppData data:usageData) {
            PieModel p = new PieModel(
                    data.mPackageName,
                    data.mUsageTime,
                    Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            );

            x.add(p);
            pieChart.addPieSlice(p);
        }

            PieModel maxp = largest(x);

            String appname = usageData.get(maxpack).mName;
            maxtext.setText(appname);
            maxtext.setTextColor(maxp.getColor());
            Glide.with(getContext())
                    .load(UsageUtils.parsePackageIcon(usageData.get(maxpack).mPackageName, R.mipmap.ic_launcher))
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(maximg);

            card.setVisibility(View.VISIBLE);
            apptext.setVisibility(View.VISIBLE);
            date.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        long start,end;
        LocalDateTime now = LocalDateTime.now().minusWeeks(0).with(DayOfWeek.MONDAY); // current date and time
        LocalDateTime midnight = now.toLocalDate().atStartOfDay();
        Date d1 = Date.from(midnight.atZone(ZoneId.systemDefault()).toInstant());


        LocalDateTime now_1 = LocalDateTime.now(); // current date and time
        LocalDateTime midnight_1 = now_1.toLocalDate().atStartOfDay();
        Date d1_1 = Date.from(midnight_1.atZone(ZoneId.systemDefault()).toInstant());


        DateFormatSymbols dateFormatSymbolsobject = new DateFormatSymbols();
        String[] shortFormatMonthsNames = dateFormatSymbolsobject.getShortMonths();

        date.setText(d1.getDate()+"-"+shortFormatMonthsNames[d1.getMonth()]+" to "
                +d1_1.getDate()+"-"+shortFormatMonthsNames[d1_1.getMonth()]);

        start=d1.getTime();
        end= System.currentTimeMillis();

        mAdapter = new AppAdapter(getContext(),getContext(),start,end, Duration.WEEK);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
    }

}