package com.bnelson.triton.client.ui.widget;

import com.bnelson.triton.client.place.GamePlace;
import com.bnelson.triton.client.place.SimplePlaceChanger;
import com.bnelson.triton.client.service.LoginRestService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.TextBox;

/**
 * Created by brnel on 8/4/2017.
 */
public class LoginPanel extends Composite{
    interface LoginPanelUiBinder extends UiBinder<Container, LoginPanel> {}
    private static LoginPanelUiBinder ourUiBinder = GWT.create(LoginPanelUiBinder.class);

    private static final LoginRestService loginRestService = GWT.create(LoginRestService.class);

    private final SimplePlaceChanger placeChanger;

    @UiField
    TextBox username;
    @UiField
    Input password;
    @UiField
    Button loginButton;

    @UiHandler("loginButton")
    public void loginButton(ClickEvent e){
        loginRestService.login(
                username.getText(),
                password.getText(),
                new MethodCallback<Boolean>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        //TODO handle exception
                    }

                    @Override
                    public void onSuccess(Method method, Boolean response) {
                        if(response){
                            placeChanger.changePlace(new GamePlace());
                        }
                    }
                });
    }

    public LoginPanel(SimplePlaceChanger placeChanger) {
        this.placeChanger = placeChanger;
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}