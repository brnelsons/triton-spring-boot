package com.bnelson.triton.client;

import com.bnelson.triton.client.mvp.activity.GameManagerActivity;
import com.bnelson.triton.client.mvp.activity.LoginActivity;
import com.bnelson.triton.client.mvp.place.GameManagerPlace;
import com.bnelson.triton.client.mvp.place.LoginPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

/**
 * Created by brnel on 8/7/2017.
 */
public class TritonActivityMapper implements ActivityMapper {

    private ClientFactory clientFactory;

    public TritonActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {

        if(place instanceof LoginPlace){
            return new LoginActivity((LoginPlace)place, clientFactory);
        }else if(place instanceof GameManagerPlace){
            return new GameManagerActivity((GameManagerPlace)place, clientFactory);
        }
        return null;
    }
}
