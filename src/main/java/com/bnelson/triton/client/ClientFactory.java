package com.bnelson.triton.client;

import com.bnelson.triton.client.mvp.view.GameManagerView;
import com.bnelson.triton.client.mvp.view.LoginView;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Created by brnel on 8/7/2017.
 */
public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();

    GameManagerView getGameManagerView();
    LoginView getLoginView();
}