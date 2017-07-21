package com.bnelson.triton.client;

import com.bnelson.triton.shared.rpc.GameInfoRPC;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import org.gwtbootstrap3.client.ui.Button;

/**
 * Created by brnel on 6/15/2017.
 */
public class GameActionButton extends Button {

    public GameActionButton(final GameInfoRPC game,
                            final String actionName) {
        super();
        setText(actionName);

        addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
//                GameRestService.App.getInstance().runAction(game, actionName, new AsyncCallback<Void>() {
//                    @Override
//                    public void onFailure(Throwable caught) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(Void result) {
//
//                    }
//                });
            }
        });
    }
}
