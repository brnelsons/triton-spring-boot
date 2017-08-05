package com.bnelson.triton.client.place;

import com.bnelson.triton.client.ui.widget.LoginPanel;
import com.google.gwt.core.client.GWT;

/**
 * Created by bnelson on 7/21/2017.
 */
public class LoginPlace extends SimplePlace {

    private static final LoginPanel composite = GWT.create(LoginPanel.class);

    public LoginPlace() {
        super("LoginPlace", composite);
    }
}
