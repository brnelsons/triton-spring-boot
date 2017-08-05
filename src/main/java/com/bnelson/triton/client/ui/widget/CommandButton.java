package com.bnelson.triton.client.ui.widget;

import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.InputGroupButton;

/**
 * Created by brnel on 7/21/2017.
 */
public class CommandButton extends Composite {
    interface CommandButtonUiBinder extends UiBinder<InputGroupButton, CommandButton> {
    }

    private static CommandButtonUiBinder ourUiBinder = GWT.create(CommandButtonUiBinder.class);

    @UiField
    Button button;

    public CommandButton(CommandInfoRPC commandInfoRPC) {
        initWidget(ourUiBinder.createAndBindUi(this));
        button.setText(commandInfoRPC.getName());
    }

    public void addClickHandler(ClickHandler clickHandler){
         button.addClickHandler(clickHandler);
    }

}