package com.soussidev.viewmodel_livedata_fragment.Repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.soussidev.viewmodel_livedata_fragment.model.Channel;
import com.soussidev.viewmodel_livedata_fragment.server.Constant;
import com.soussidev.viewmodel_livedata_fragment.server.RequestInterface;
import com.soussidev.viewmodel_livedata_fragment.server.ServerRequest;
import com.soussidev.viewmodel_livedata_fragment.server.ServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.soussidev.viewmodel_livedata_fragment.server.Constant.TAG;

/**
 * Created by Soussi on 23/03/2018.
 */

public class Channel_Repository {

    /**
     * @auteur Soussi Mohamed
     * @see Channel_Repository
     *
     */
    public MutableLiveData<List<Channel>> getchannel() {
        final MutableLiveData<List<Channel>> ch = new MutableLiveData<>();

        RequestInterface requestInterface = Constant.getClient().create(RequestInterface.class);
        ServerRequest request = new ServerRequest();

        request.setOperation(Constant.GET_OPERATION.getChannel);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();


                Log.e(TAG,resp.getMessage());
                Log.e(TAG,resp.getResult());

                if(resp.getResult().equals(Constant.SUCCESS)){

                    int responseCode = response.code();
                    Log.d(TAG, String.valueOf(responseCode));
                    ch.postValue(resp.getChannel());




                }


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(TAG,"failed channel");
                Log.d(TAG,t.getLocalizedMessage());


            }
        });


        return ch;
    }

}
