package com.example.searchviewtest;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class PartThreeActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList dates = new ArrayList();
    ArrayList<Upcoming> upcomings = new ArrayList<>();
    Upcoming upcoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_three);
        initToolbar();
        initViewPagerAndTabs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeApiCall();
        if(isPackageInstalled("tv.twitch.android.app",this)) ((IsInstalled)(getApplicationContext())).setTwitch(true);
        else ((IsInstalled)(getApplicationContext())).setTwitch(false);
    }

    private void makeApiCall(){
        ApiCall.make(this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(response);
                JsonArray jsonArray = element.getAsJsonArray();
                StringBuffer stringBuffer = new StringBuffer();
                for(int i = 0; i < jsonArray.size(); i++){
                    JsonElement name = jsonArray.get(i).getAsJsonObject();
                    String gameName = ((JsonObject) name).get("videogame").getAsJsonObject().get("slug").getAsString();
                    upcoming = new Upcoming();
                    upcoming.setGameName(gameName);
                }
                Log.d("slug",upcoming.getGameName());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PartThreeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-3);
        Date date = calendar.getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("M월dd일");

        String days = dayFormat.format(date);
        dates.add(days);
        for(int i =0; i<6; i++){
            calendar.add(calendar.DAY_OF_MONTH,1);
            date = calendar.getTime();
            days = dayFormat.format(date);
            dates.add(days);
        }
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
    }
    private void initViewPagerAndTabs(){
        ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        initCalendar();
        if(dates.size() >0){
            Iterator it = dates.iterator();
            while (it.hasNext()){
                pagerAdapter.addFragment(PartThreeFragment.createInstance(10),(String)(it.next()));
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
