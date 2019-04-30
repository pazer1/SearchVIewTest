package com.example.searchviewtest;

import android.app.SearchManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.LocaleData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class PartThreeActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList dates = new ArrayList();
    LinkedHashMap<String, ArrayList> leagues = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_three);

    }

    @Override
    protected void onResume() {
        super.onResume();
        makeApiCall();
        //initViewPagerAndTabs();
        //트위치 저장되어 있는지 확인하고 글로벌에 유무 저장
        if(isPackageInstalled("tv.twitch.android.app",this)) ((IsInstalled)(getApplicationContext())).setTwitch(true);
        else ((IsInstalled)(getApplicationContext())).setTwitch(false);
        //League Match정보를 Json으로 불러옴 league Arraylist에 저장;
        //league Arraylist에 저장된 mateches 정보를 캘린더와 연결
    }


//    private void whenStart(){
//        Iterator it = leagues.iterator();
//        while (it.hasNext()){
//            Match match = (Match)it.next();
//          utcToLocaltime(match.getBegin_at(),match.getName());
//        }
//    }
//
//    public void utcToLocaltime(String datetime, String league){
//        String locTime = null;
//        TimeZone tz = TimeZone.getDefault();
////        "2019-04-24T21:00:00Z"
//        try {
//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//            SimpleDateFormat outputFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//            Date date = inputFormat.parse(datetime);
//            long milliseconds = date.getTime();
//            long now = System.currentTimeMillis();
//            int offset = tz.getOffset(milliseconds);
////            milliseconds+offset
//            locTime= outputFormat.format(milliseconds+offset);
//            Log.d("oldtime",outputFormat.format(new Date(now)));
//            Log.d("currenttime",locTime +","+offset);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }


    //League Match정보를 Json으로 불러옴
    private void makeApiCall(){
        ApiCall.make(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(response);
                JsonArray jsonArray = element.getAsJsonArray();
                for(int i = 0; i < jsonArray.size(); i++){
                    Gson gson = new Gson();
                    Match match = gson.fromJson(jsonArray.get(i), Match.class);
                    String locTime = utcToLocal(match.getBegin_at());

                    if(leagues.containsKey(locTime)){ //똑같은 키가 있으면 새로운 배열을 만드렁야ㅗ디는데
                      leagues.get(locTime).add(match);

                    }else{
                        leagues.put(locTime,new ArrayList());
                        leagues.get(locTime).add(match);
                    }
                }


                initToolbar();
                //initCalendar();
                //whenStart(); // 작업이 끝나면 whenStart 시작
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PartThreeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String utcToLocal(String changeTime){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy-MM-dd");
        TimeZone tz = TimeZone.getDefault();
        String locTime;
        try {
            Date date = inputFormat.parse(changeTime);
            long milliseconds = date.getTime();
            long now = System.currentTimeMillis();
            int offset = tz.getOffset(milliseconds);
            locTime= outputFormat.format(milliseconds+offset);
            return locTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    //캘린더를 불러옴
    private void initCalendar(){

//        일주일 표시

//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_MONTH,-3);
//        Date date = calendar.getTime();
//        SimpleDateFormat dayFormat = new SimpleDateFormat("M월dd일");
//
//        String days = dayFormat.format(date);
//        dates.add(days);
//        for(int i =0; i<6; i++){
//            calendar.add(calendar.DAY_OF_MONTH,1);
//            date = calendar.getTime();
//            days = dayFormat.format(date);
//            dates.add(days);
//        }
//        Iterator it = leagues.iterator();
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yy-MM-dd");
//        TimeZone tz = TimeZone.getDefault();
//        String locTime;
//        String matchStart;
//        while(it.hasNext()){
//
//            Match match  = (Match)it.next();
//            matchStart = match.getBegin_at();
//
//            try {
//            Date date = inputFormat.parse(matchStart);
//            long milliseconds = date.getTime();
//            long now = System.currentTimeMillis();
//            int offset = tz.getOffset(milliseconds);
//            locTime= outputFormat.format(milliseconds+offset);
//
//            if(!dates.contains(locTime)){
//                dates.add(locTime);
//            }
//
//
//            Log.d("sadds",matchStart);
//            Log.d("currenttime",locTime +","+offset);
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        initToolbar();
//        initViewPagerAndTabs();
    }


    private void initToolbar(){
        Toolbar mToolbar = findViewById(R.id.toolbar);
        SearchView searchView = findViewById(R.id.saerchView);
        SearchManager searchManager = (SearchManager)getSystemService(this.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        setSupportActionBar(mToolbar);
        findViewById(R.id.tv_title).setOnClickListener(this);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        initViewPagerAndTabs();
    }
    private void initViewPagerAndTabs(){
        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        if(leagues.size() >0){
            Iterator it = leagues.keySet().iterator();
            String keycode;
            while (it.hasNext()){
                keycode = (String)it.next();
                pagerAdapter.addFragment(PartThreeFragment.createInstance(leagues.get(keycode).size(),leagues.get(keycode)),keycode);
            }
        }
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(3);
        tab.setCustomView(R.layout.today_tab);
        TextView tv= findViewById(R.id.tv_tab);
        tv.setText("오늘");
        tab.select();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title:
                finish();
                startActivity(getIntent());
            default:
                break;
        }
    }

    //OnResume 에서 트위치가 깔려 있는지 체크하고 글로벌 변수로 boolean을 저장, 저장되어 있으면 yes or no를 알려주는 메소드
    private boolean isPackageInstalled(String pakagename, Context context){

        PackageManager pm = context.getPackageManager();
        try{
            pm.getPackageInfo(pakagename, PackageManager.GET_ACTIVITIES);
            Toast.makeText(context, "yes", Toast.LENGTH_SHORT).show();
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "no", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    static class PagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();


        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
