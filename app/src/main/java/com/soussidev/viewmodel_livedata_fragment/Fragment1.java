package com.soussidev.viewmodel_livedata_fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.soussidev.viewmodel_livedata_fragment.RxPref.PrefItem;
import com.soussidev.viewmodel_livedata_fragment.RxPref.RxSharedPreferences;
import com.soussidev.viewmodel_livedata_fragment.adapter.AdapterChannel;
import com.soussidev.viewmodel_livedata_fragment.viewmodel.ChannelViewModel;

import io.reactivex.Observable;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    private static final String TAG =MainActivity.class.getSimpleName();

    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 3;

    private RecyclerView recyclerView;
    private AdapterChannel itemAdapter;
    private GridLayoutManager gridLayoutManager;

    private SharedPreferences sharedPreferences;
    private RxSharedPreferences rxShared;

    private ChannelViewModel channelViewModel;

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        channelViewModel = ViewModelProviders.of(getActivity()).get(ChannelViewModel.class);
        channelViewModel.getLiveChannel().observe(getActivity(), ch -> {

            itemAdapter = new AdapterChannel(ch, gridLayoutManager,getActivity());
            recyclerView.setAdapter(itemAdapter);

        });

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        sharedPreferences = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
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
                    gridLayoutManager = new GridLayoutManager(getActivity(), prefItem.getSpan_count());
                });

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(gridLayoutManager);


    }




}
