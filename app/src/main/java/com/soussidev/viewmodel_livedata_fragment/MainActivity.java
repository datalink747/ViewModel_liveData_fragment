package com.soussidev.viewmodel_livedata_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.soussidev.viewmodel_livedata_fragment.RxPref.PrefItem;
import com.soussidev.viewmodel_livedata_fragment.RxPref.RxSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.soussidev.viewmodel_livedata_fragment.Fragment1.SPAN_COUNT_ONE;
import static com.soussidev.viewmodel_livedata_fragment.Fragment1.SPAN_COUNT_THREE;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private SharedPreferences sharedPreferences;
    private RxSharedPreferences rxShared;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(viewPager);


        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        rxShared = RxSharedPreferences.with(sharedPreferences);

        rxShared.getInt("span", 0)
                .subscribe(span_count -> {
                    //Get Span Count
                    Log.d("Get pref", "SPAN: " + span_count);
                    if(span_count.equals(0))
                    {
                        // If Span Count 0 Replace With 1
                        rxShared.putString("app_name","layout_switch_RXShared_Pref")
                                .flatMap(span_c ->rxShared.putInteger("span",SPAN_COUNT_ONE))
                                .flatMap(span_name ->rxShared.putString("span.name","Single"))
                                .flatMap(span_item -> rxShared.getAll())
                                .flatMap(integerMap -> Observable.fromIterable(integerMap.entrySet()))
                                .map(Object::toString)

                                .subscribe(s -> Log.d("TAG 1", s));
                    }
                });

        Observable.just(new PrefItem())
                .flatMap(prefItem -> rxShared.getInt("span", 1), (prefItem, sp) -> {
                    prefItem.setSpan_count(sp);
                    return prefItem;
                })

                .flatMap(prefItem -> rxShared.getString("span.name", ""), (prefItem, sp) -> {
                    prefItem.setSpan_name(sp);
                    return prefItem;
                })

                .subscribe(prefItem -> {
                    Log.d("TAG 3 NUM:", String.valueOf(prefItem.getSpan_count()));
                    Log.d("TAG 3 NAME:", prefItem.getSpan_name());
                    //Get span From RXPref to Layout Manager
                    gridLayoutManager = new GridLayoutManager(this, prefItem.getSpan_count());
                });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new
                ViewPagerAdapter(getSupportFragmentManager());

        //change the fragmentName as per your need

        adapter.addFragment(new Fragment1(), "Fragment 1");
        adapter.addFragment(new Fragment2(), "Fragment 2");


        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_switch_layout) {
            switchLayout();
            switchIcon(item);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchLayout() {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_ONE) {
            gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);

            rxShared.putString("app_name","layout_switch_RXShared_Pref")
                    .flatMap(span_count ->rxShared.putInteger("span",SPAN_COUNT_THREE))
                    .flatMap(span_name ->rxShared.putString("span.name","Multiple"))
                    .flatMap(span_item -> rxShared.getAll())
                    .flatMap(integerMap -> Observable.fromIterable(integerMap.entrySet()))
                    .map(Object::toString)

                    .subscribe(s -> Log.d("Switch_layout 3:", s));


        } else {
            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);

            rxShared.putString("app_name","layout_switch_RXShared_Pref")
                    .flatMap(span_count ->rxShared.putInteger("span",SPAN_COUNT_ONE))
                    .flatMap(span_name ->rxShared.putString("span.name","Single"))
                    .flatMap(span_item -> rxShared.getAll())
                    .flatMap(integerMap -> Observable.fromIterable(integerMap.entrySet()))
                    .map(Object::toString)

                    .subscribe(s -> Log.d("Switch_layout 1:", s));


        }
       // itemAdapter.notifyItemRangeChanged(0, itemAdapter.getItemCount());
    }

    private void switchIcon(MenuItem item) {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_THREE) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_1));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_3));
        }
    }
}
