package com.soussidev.viewmodel_livedata_fragment.server;

import com.google.gson.annotations.SerializedName;
import com.soussidev.viewmodel_livedata_fragment.model.Channel;

/**
 * Created by Soussi on 23/03/2018.
 */

public class ServerRequest {

    private Constant.GET_OPERATION operation;

    @SerializedName(value="channel")
    private Channel channel;

    public void setOperation(Constant.GET_OPERATION operation) {
        this.operation = operation;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
