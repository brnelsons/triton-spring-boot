package com.bnelson.triton.client;

import com.bnelson.triton.shared.Game;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by brnel on 6/16/2017.
 */
public class MainPanel extends Composite implements HasWidgets{
    interface MainPanelUiBinder extends UiBinder<Row, MainPanel> {}

    private static MainPanelUiBinder ourUiBinder = GWT.create(MainPanelUiBinder.class);
    private static final GameRestService gameRestService = GWT.create(GameRestService.class);
    @UiField Row mainPanel;

    public MainPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
        gameRestService.getGames(new MethodCallback<List<Game>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                //TODO show errors
            }

            @Override
            public void onSuccess(Method method, List<Game> response) {
                for(Game game: response){
                    Column column = new Column(ColumnSize.LG_3, ColumnSize.MD_6, ColumnSize.SM_12);
                    column.add(new GamePanel(game));
                    mainPanel.add(
                            column
                    );
                }
            }
        });

    }

    @Override
    public void add(Widget w) {
        mainPanel.add(w);
    }

    @Override
    public void clear() {
        mainPanel.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return mainPanel.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        return mainPanel.remove(w);
    }
}