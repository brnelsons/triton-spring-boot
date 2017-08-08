package com.bnelson.triton.client.mvp.activity;

import com.bnelson.triton.client.ClientFactory;
import com.bnelson.triton.client.mvp.place.LoginPlace;
import com.bnelson.triton.client.mvp.view.LoginView;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

/**
 * Created by brnel on 8/7/2017.
 */
public class LoginActivity  extends AbstractActivity {

    private ClientFactory clientFactory;

    public LoginActivity(LoginPlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        LoginView view = clientFactory.getLoginView();
        panel.setWidget(view.asWidget());
    }

    @Override
    public String mayStop() {
        return super.mayStop();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}