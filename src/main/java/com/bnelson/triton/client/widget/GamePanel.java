package com.bnelson.triton.client.widget;

import com.bnelson.triton.client.service.GameRestService;
import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.ConnectionStatusRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Panel;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by brnel on 6/15/2017.
 */
public class GamePanel extends Composite {
    interface GamePanelUiBinder extends UiBinder<Panel, GamePanel> {}

    private static GamePanelUiBinder ourUiBinder = GWT.create(GamePanelUiBinder.class);

    private final GameRestService gameService;

    private AsyncCallback<String> statusAsync;
    private boolean buttonsActive = true;

    @UiField Panel panel;
    @UiField Label name;
    @UiField Label status;
    @UiField CommandGroup commandGroup;
    @UiField TextArea outputBox;
//    @UiField TextBox inputBox;
//    @UiField Button sendButton;

    public GamePanel(final GameRestService gameService,
                     final GameInfoRPC game) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.gameService = gameService;
        this.name.setText(game.getName());
        this.status.setText(ConnectionStatusRPC.UNKNOWN.name());

        gameService.getGameCommands(game.getId(), new MethodCallback<List<CommandInfoRPC>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                //todo
            }

            @Override
            public void onSuccess(Method method, List<CommandInfoRPC> response) {
                updateCommandButtons(response);
            }
        });

//        gameService.get

        //set update schedule
        Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
            @Override
            public boolean execute() {
                gameService.getGameStatus(
                        game.getId(),
                        new MethodCallback<ConnectionStatusRPC>() {
                            @Override
                            public void onFailure(Method method, Throwable exception) {
                                //todo disable buttons change color of the border for panel
                            }

                            @Override
                            public void onSuccess(Method method, ConnectionStatusRPC response) {
                                handleStatusChange(response);
                            }
                        });
                gameService.getConnectionOutput(
                        game.getId(),
                        new MethodCallback<String>() {
                            @Override
                            public void onFailure(Method method, Throwable exception) {
                                //todo
                            }

                            @Override
                            public void onSuccess(Method method, String response) {
                                handleConnectionOutput(response);
                            }
                        });
                return true;
            }
        }, 5000);
    }

    private void handleConnectionOutput(String response) {
        String current = outputBox.getText();
        outputBox.setText(current + response);
    }

    private void updateCommandButtons(List<CommandInfoRPC> response) {
        commandGroup.clear();
        for (CommandInfoRPC infoRPC : response) {
            commandGroup.add(infoRPC);
        }
    }

    private void handleStatusChange(ConnectionStatusRPC response) {
        if (response != null) {
            this.status.setText(response.name());
            switch (response) {
                case RUNNING:
                    break;
                case STOPPED:
                    break;
                case UNKNOWN:
                    break;
            }
        }
    }

    private void lockAllButtons() {
    }
}