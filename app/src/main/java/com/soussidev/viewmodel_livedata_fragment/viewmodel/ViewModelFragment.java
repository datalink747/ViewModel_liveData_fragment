package com.soussidev.viewmodel_livedata_fragment.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.soussidev.viewmodel_livedata_fragment.model.Channel;

/**
 * Created by Soussi on 23/03/2018.
 */

public class ViewModelFragment extends ViewModel {

    private final MutableLiveData<Channel> selected = new MutableLiveData<Channel>();

    public void select(Channel item) {
        selected.setValue(item);
    }

    public LiveData<Channel> getSelected() {
        return selected;
    }
}
