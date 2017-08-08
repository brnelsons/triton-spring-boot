package com.bnelson.triton.client.mvp.view;

import com.bnelson.triton.client.mvp.place.GameManagerPlace;
import com.bnelson.triton.client.service.LoginRestService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.PlaceController;
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

public class LoginView extends Composite{
    interface LoginViewUiBinder extends UiBinder<Container, LoginView> {}

    private static LoginViewUiBinder ourUiBinder = GWT.create(LoginViewUiBinder.class);
    private static final LoginRestService loginRestService = GWT.create(LoginRestService.class);

    private final PlaceController placeController;

    @UiField Container container;
    @UiField TextBox username;
    @UiField Input password;
    @UiField Button loginButton;

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
                            placeController.goTo(new GameManagerPlace());
                        }else{
                            password.setText("");
                        }
                    }
                });
    }

    public LoginView(PlaceController placeController) {
        this.placeController = placeController;
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}