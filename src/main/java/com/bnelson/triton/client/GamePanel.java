package com.bnelson.triton.client;

import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.GameStatus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Panel;

import java.util.HashSet;

/**
 * Created by brnel on 6/15/2017.
 */
public class GamePanel extends Composite {

    private AsyncCallback<Void> VOID = new AsyncCallback<Void>() {
        @Override
        public void onFailure(Throwable caught) {

        }

        @Override
        public void onSuccess(Void result) {

        }
    };

    interface GamePanelUiBinder extends UiBinder<Panel, GamePanel> {
    }

    private static GamePanelUiBinder ourUiBinder = GWT.create(GamePanelUiBinder.class);

    private final Game game;

    private AsyncCallback<String> statusAsync;
    private boolean buttonsActive = true;

    @UiField
    Label name;
    @UiField
    Label status;
    @UiField
    HorizontalPanel buttons;
    @UiField
    TextArea outputBox;
    @UiField
    TextBox inputBox;
    @UiField
    Button sendButton;

    public GamePanel(final Game game) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.game = game;
        this.name.setText(game.getName());
        this.status.setText(GameStatus.UNKNOWN.name());

        //set update schedule
        Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
            @Override
            public boolean execute() {
                ServerScriptRunnerService.App.getInstance().getGameStatus(game, STATUS_CALLBACK);
                ServerScriptRunnerService.App.getInstance().getGameActions(game, ACTION_CALLBACK);
                statusAsync = new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //no op
                    }

                    @Override
                    public void onSuccess(String result) {
//                        if(statusAsync == this) {
                            outputBox.setText(result);
//                        }
                    }
                };
                ServerScriptRunnerService.App.getInstance().getConnectionOutput(game, statusAsync);
                return true;
            }
        },250);

        sendButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String message = inputBox.getText();
                if(message != null && !"".equals(message)){
                    inputBox.setText("");
                    ServerScriptRunnerService.App.getInstance().sendMessageToConnection(game, message, new SimpleCallback() {
                        @Override
                        public void onSuccessOrFailure(Throwable throwable) {

                        }
                    });
                }
            }
        });
    }

    private final AsyncCallback<HashSet<String>> ACTION_CALLBACK = new AsyncCallback<HashSet<String>>() {
        @Override
        public void onFailure(Throwable caught) {
            //do nothing...
        }

        @Override
        public void onSuccess(HashSet<String> result) {
            buttons.clear();
            for (String gameActionType : result) {
                GameActionButton button = new GameActionButton(game, gameActionType);
                button.setVisible(true);
                buttons.add(button);
            }
        }
    };

    private final AsyncCallback<GameStatus> STATUS_CALLBACK = new AsyncCallback<GameStatus>() {
        @Override
        public void onFailure(Throwable caught) {
            status.setText(GameStatus.UNKNOWN.name());
        }

        @Override
        public void onSuccess(GameStatus result) {
            status.setText(result.name());
        }
    };

    private void lockAllButtons(){
    }
}