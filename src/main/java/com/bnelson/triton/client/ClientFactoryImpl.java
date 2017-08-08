package com.bnelson.triton.client;

import com.bnelson.triton.client.mvp.view.GameManagerView;
import com.bnelson.triton.client.mvp.view.LoginView;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * Created by brnel on 8/7/2017.
 */
public class ClientFactoryImpl implements ClientFactory {

    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(eventBus);

    private final GameManagerView gameManagerView = new GameManagerView();
    private final LoginView loginView = new LoginView(placeController);

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public GameManagerView getGameManagerView() {
        return gameManagerView;
    }

    @Override
    public LoginView getLoginView() {
        return loginView;
    }
}
