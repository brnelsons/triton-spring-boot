package com.bnelson.triton.client;

import com.bnelson.triton.client.place.LoginPlace;
import com.bnelson.triton.client.place.SimplePlace;
import com.bnelson.triton.client.place.SimplePlaceChanger;
import com.bnelson.triton.client.ui.MainPanel;
import com.bnelson.triton.client.ui.widget.LoginPanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import org.fusesource.restygwt.client.Defaults;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class ServerScriptRunner implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        useCorrectRequestBaseUrl();
//        SimplePlaceChanger placeChanger = new SimplePlaceChanger(new SimplePlaceChanger.PlaceChangeHandler() {
//            @Override
//            public void onChange(SimplePlace place) {
//                RootPanel.get().clear();
//                RootPanel.get().add(place.getComposite());
//            }
//        });
//        placeChanger.changePlace(new MainPanel());
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
