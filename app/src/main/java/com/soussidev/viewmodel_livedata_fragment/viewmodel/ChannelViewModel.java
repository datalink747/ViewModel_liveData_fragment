package com.soussidev.viewmodel_livedata_fragment.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.soussidev.viewmodel_livedata_fragment.Repository.Channel_Repository;
import com.soussidev.viewmodel_livedata_fragment.model.Channel;

import java.util.List;

/**
 * Created by Soussi on 23/03/2018.
 */

public class ChannelViewModel extends ViewModel {

    private MutableLiveData<List<Channel>> liveChannel;

    private Channel_Repository channelRepository = new Channel_Repository();
    /**
     * @auteur Soussi Mohamed
     * @see ChannelViewModel
     *
     */

    //using livedata
    public MutableLiveData<List<Channel>> getLiveChannel() {
        if(liveChannel == null){
            liveChannel = channelRepository.getchannel();
        }
        return liveChannel;
    }
}

