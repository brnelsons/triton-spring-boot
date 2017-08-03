package com.bnelson.triton.client.widget;

import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.InputGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by brnel on 7/21/2017.
 */
public class CommandGroup extends Composite{
    interface CommandGroupUiBinder extends UiBinder<Container, CommandGroup> {}

    private static CommandGroupUiBinder ourUiBinder = GWT.create(CommandGroupUiBinder.class);

    @UiField InputGroup buttons;

    private final Map<CommandInfoRPC, CommandButton> infoButtonMap;

    public CommandGroup() {
        initWidget(ourUiBinder.createAndBindUi(this));
        infoButtonMap = new HashMap<>();
    }

    public void add(CommandInfoRPC commandInfoRPC, ClickHandler clickHandler){
        CommandButton button = new CommandButton(commandInfoRPC);
        infoButtonMap.put(commandInfoRPC, button);
        buttons.add(button);
        button.addClickHandler(clickHandler);
    }

    public void remove(CommandInfoRPC commandInfoRPC){
        CommandButton button = infoButtonMap.get(commandInfoRPC);
        buttons.remove(button);
    }

    public void clear(){
        buttons.clear();
    }
}