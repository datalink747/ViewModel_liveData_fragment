package com.soussidev.viewmodel_livedata_fragment.server;

import com.google.gson.annotations.SerializedName;
import com.soussidev.viewmodel_livedata_fragment.model.Channel;

import java.util.List;

/**
 * Created by Soussi on 23/03/2018.
 */

public class ServerResponse {

    private String result;
    private String message;

    @SerializedName(value="channel")
    private List<Channel> channel;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public List<Channel> getChannel() {
        return channel;
    }
}
