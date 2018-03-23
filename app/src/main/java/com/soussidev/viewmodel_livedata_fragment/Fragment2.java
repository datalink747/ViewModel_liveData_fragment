package com.soussidev.viewmodel_livedata_fragment;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.soussidev.viewmodel_livedata_fragment.server.Constant;
import com.soussidev.viewmodel_livedata_fragment.viewmodel.ViewModelFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment {

    private ViewModelFragment model;
    private TextView name,category,language,quality;
    private ImageView image;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name =(TextView)view.findViewById(R.id.name);
        category =(TextView)view.findViewById(R.id.category);
        language =(TextView)view.findViewById(R.id.language);
        quality =(TextView)view.findViewById(R.id.quality);
        image =(ImageView)view.findViewById(R.id.image_channel);

     model = ViewModelProviders.of(getActivity()).get(ViewModelFragment.class);
     model.getSelected().observe(getActivity(), channel -> {

         name.setText(channel.getName_channel());
         category.setText(channel.getCategory());
         language.setText(channel.getLang());
         quality.setText(channel.getQuality_ch());
         Glide.with(getActivity())
                 .load(Constant.BASE_URL+Constant.NAME_PATH+channel.getUrl_them())
                 .into(image);

     });

    }

}
