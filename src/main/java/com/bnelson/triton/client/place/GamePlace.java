package com.bnelson.triton.client.place;

import com.bnelson.triton.client.ui.MainPanel;
import com.google.gwt.core.client.GWT;

/**
 * Created by brnel on 8/4/2017.
 */
public class GamePlace extends SimplePlace {

    private static final MainPanel composite = GWT.create(MainPanel.class);

    public GamePlace() {
        super("GamePlace", composite);
    }
}
