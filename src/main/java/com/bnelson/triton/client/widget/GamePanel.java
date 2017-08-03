package com.bnelson.triton.client.widget;

import com.bnelson.triton.client.OnAlert;
import com.bnelson.triton.client.service.GameRestService;
import com.bnelson.triton.shared.rpc.CommandInfoRPC;
import com.bnelson.triton.shared.rpc.ConnectionStatusRPC;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.constants.AlertType;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by brnel on 6/15/2017.
 */
public class GamePanel extends Composite {
    interface GamePanelUiBinder extends UiBinder<Panel, GamePanel> {}

    private static final Logger LOGGER = Logger.getLogger("GamePanel");

    private static GamePanelUiBinder ourUiBinder = GWT.create(GamePanelUiBinder.class);

    private final GameRestService gameService;
    private final GameInfoRPC game;
    private final OnAlert onAlert;

    @UiField Panel panel;
    @UiField Label name;
    @UiField StopLight stopLight;
    @UiField CommandGroup commandGroup;
    @UiField TextArea outputBox;

    public GamePanel(final GameRestService gameService,
                     final GameInfoRPC game,
                     final OnAlert onAlert) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.gameService = gameService;
        this.onAlert = onAlert;
        this.game = game;
        this.name.setText(game.getName());

        gameService.getGameCommands(game.getId(), new MethodCallback<List<CommandInfoRPC>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                onAlert.alert("Get Game Commands", exception.getMessage(), AlertType.DANGER);
            }

            @Override
            public void onSuccess(Method method, List<CommandInfoRPC> response) {
                updateCommandButtons(response);
            }
        });

        //set update schedule
        Scheduler.get().scheduleFixedDelay(() -> {
            gameService.getGameStatus(
                    game.getId(),
                    new MethodCallback<ConnectionStatusRPC>() {
                        @Override
                        public void onFailure(Method method, Throwable exception) {
                            onAlert.alert("Get Game Status", exception.getMessage(), AlertType.DANGER);
                        }

                        @Override
                        public void onSuccess(Method method, ConnectionStatusRPC response) {
                            handleStatusChange(response);
                        }
                    });
            gameService.getGameOutput(
                    game.getId(),
                    new MethodCallback<String>() {
                        @Override
                        public void onFailure(Method method, Throwable exception) {
                            onAlert.alert("Get Game Output", exception.getMessage(), AlertType.DANGER);
                        }

                        @Override
                        public void onSuccess(Method method, String response) {
                            handleConnectionOutput(response);
                        }
                    });
            return true;
        }, 5000);
    }

    private void handleConnectionOutput(String response) {
        outputBox.setText(response);
    }

    private void updateCommandButtons(List<CommandInfoRPC> response) {
        commandGroup.clear();
        for (final CommandInfoRPC infoRPC : response) {
            commandGroup.add(infoRPC, new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    gameService.runCommand(game.getId(), infoRPC.getName(), new MethodCallback<String>() {
                        @Override
                        public void onFailure(Method method, Throwable exception) {
                            onAlert.alert("Run Command " + game.getName(), exception.getMessage(), AlertType.DANGER);
                        }

                        @Override
                        public void onSuccess(Method method, String response) {
                            //no op
                        }
                    });
                }
            });
        }
    }

    private void handleStatusChange(ConnectionStatusRPC response) {
        if (response != null) {
            switch (response) {
                case RUNNING:
                    stopLight.setLight(StopLight.Light.GREEN);
                    break;
                case STOPPED:
                    stopLight.setLight(StopLight.Light.RED);
                    break;
                case UNKNOWN:
                    stopLight.setLight(StopLight.Light.YELLOW);
                    break;
                default:
                    stopLight.setLight(StopLight.Light.BLACK);
            }
        }
    }

    private void lockAllButtons() {
    }
}