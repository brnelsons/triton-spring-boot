package com.bnelson.triton.client;

import com.bnelson.triton.client.service.GameRestService;
import com.bnelson.triton.shared.rpc.GameStatusRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Panel;

import java.util.HashSet;

/**
 * Created by brnel on 6/15/2017.
 */
public class GamePanel extends Composite {
    interface GamePanelUiBinder extends UiBinder<Panel, GamePanel> {}
    private static GamePanelUiBinder ourUiBinder = GWT.create(GamePanelUiBinder.class);

    private final GameInfoRPC game;
    private final GameRestService gameService;

    private AsyncCallback<String> statusAsync;
    private boolean buttonsActive = true;

    @UiField Panel panel;
    @UiField Label name;
    @UiField Label status;
    @UiField HorizontalPanel buttons;
    @UiField TextArea outputBox;
//    @UiField TextBox inputBox;
//    @UiField Button sendButton;

    public GamePanel(final GameRestService gameService,
                     final GameInfoRPC game) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.gameService = gameService;
        this.game = game;
        this.name.setText(game.getName());
        this.status.setText(GameStatusRPC.UNKNOWN.name());

        //set update schedule
        Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
            @Override
            public boolean execute() {
                gameService.getGameStatus(game, new MethodCallback<GameStatusRPC>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        //todo disable buttons change color of the border for panel
                    }

                    @Override
                    public void onSuccess(Method method, GameStatusRPC response) {
                        handleStatusChange(response);
                    }
                });
                return true;
            }
        }, 250);
    }

    private void handleStatusChange(GameStatusRPC response) {
        switch(response){
            case RUNNING:
                break;
            case STOPPED:
                break;
            case UNKNOWN:
                break;
        }
    }

    private void lockAllButtons() {
    }
}