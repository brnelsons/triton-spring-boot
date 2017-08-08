package com.bnelson.triton.client.mvp.view;

import com.bnelson.triton.client.ui.OnAlert;
import com.bnelson.triton.client.ui.widget.GamePanel;
import com.bnelson.triton.client.service.GameRestService;
import com.bnelson.triton.client.ui.widget.AlertListWidget;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.AlertType;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class GameManagerView extends Composite implements HasWidgets{
    interface MainPanelUiBinder extends UiBinder<Container, GameManagerView> {}

    private static MainPanelUiBinder ourUiBinder = GWT.create(MainPanelUiBinder.class);
    private static final GameRestService gameRestService = GWT.create(GameRestService.class);

    @UiField Button refresh;
    @UiField AlertListWidget alertListWidget;
    @UiField Row row;
    @UiField Container externalLinks;

    private final List<GamePanel> gamePanels;

    @UiHandler("refresh")
    public void refreshButtonHandler(ClickEvent event){
        gameRestService.refreshGames(new MethodCallback<Boolean>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                alertListWidget.addAlert("Refresh Games", exception.getMessage(), AlertType.DANGER);
            }

            @Override
            public void onSuccess(Method method, Boolean response) {
                initGamePanels();
            }
        });
    }

    public GameManagerView() {
        initWidget(ourUiBinder.createAndBindUi(this));
        gamePanels = new ArrayList<>();
        initGamePanels();

    }

    private void initGamePanels() {
        clearGamePanels();
        gameRestService.getGames(new MethodCallback<List<GameInfoRPC>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                alertListWidget.addAlert("Get Games", exception.getMessage(), AlertType.DANGER);
            }

            @Override
            public void onSuccess(Method method, List<GameInfoRPC> response) {
                for(GameInfoRPC game: response){
                    GamePanel panel = new GamePanel(gameRestService, game, new OnAlert() {
                        @Override
                        public void alert(String strong, String details, AlertType type) {
                            alertListWidget.addAlert(strong, details, type);
                        }
                    });
                    gamePanels.add(panel);
                    Column column = new Column(ColumnSize.LG_4, ColumnSize.MD_6, ColumnSize.SM_12);
                    column.add(panel);
                    row.add(column);
                }
            }
        });
    }

    private void clearGamePanels() {
        row.clear();
        for(GamePanel panel : gamePanels){
            panel.stop();
        }
        gamePanels.clear();
    }

    @Override
    public void add(Widget w) {
        row.add(w);
    }

    @Override
    public void clear() {
        row.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return row.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        return row.remove(w);
    }
}