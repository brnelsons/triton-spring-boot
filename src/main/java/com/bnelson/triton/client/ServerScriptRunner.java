package com.bnelson.triton.client;

import com.bnelson.triton.shared.Game;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import org.fusesource.restygwt.client.Defaults;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;

import java.util.ArrayList;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class ServerScriptRunner implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        useCorrectRequestBaseUrl();
        RootPanel.get().add(new MainPanel());
    }

    private void useCorrectRequestBaseUrl() {
        if (isDevelopmentMode()) {
            // our spring boot server is running at port 80. If we don't change the url
            // resty gwt would use the gwt servlet port
            Defaults.setServiceRoot("http://localhost:8080");
        } else {
            // in production our compiled javascript code gets copied into
            // a 'springbootgwt' folder into the static resources. So if we
            // dont change the default url resty gwt would put the folders name
            // to the base url
            Defaults.setServiceRoot(GWT.getHostPageBaseURL());
        }
    }

    /**
     * Detect if the app is in development mode.
     *
     * @return true if in development mode
     */
    private static native boolean isDevelopmentMode()/*-{
        return typeof $wnd.__gwt_sdm !== 'undefined';
    }-*/;
}
