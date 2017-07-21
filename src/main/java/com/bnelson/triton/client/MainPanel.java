package com.bnelson.triton.client;

import com.bnelson.triton.client.service.GameRestService;
import com.bnelson.triton.shared.Game;
import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;

import java.util.Iterator;
import java.util.List;

/**
 * Created by brnel on 6/16/2017.
 */
public class MainPanel extends Composite implements HasWidgets{
    interface MainPanelUiBinder extends UiBinder<Container, MainPanel> {}

    private static MainPanelUiBinder ourUiBinder = GWT.create(MainPanelUiBinder.class);
    private static final GameRestService gameRestService = GWT.create(GameRestService.class);
    @UiField Row row;

    public MainPanel() {
        initWidget(ourUiBinder.createAndBindUi(this));
        gameRestService.getGames(new MethodCallback<List<GameInfoRPC>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                //TODO show errors
                System.out.println("something bad happened");
            }

            @Override
            public void onSuccess(Method method, List<GameInfoRPC> response) {
                for(GameInfoRPC game: response){
                    Column column = new Column(ColumnSize.LG_3, ColumnSize.MD_6, ColumnSize.SM_12);
                    column.add(new GamePanel(game));
                    row.add(
                            column
                    );
                }
            }
        });

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