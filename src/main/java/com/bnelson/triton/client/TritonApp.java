package com.bnelson.triton.client;

import com.bnelson.triton.client.mvp.TritonPlaceHistoryMapper;
import com.bnelson.triton.client.mvp.place.LoginPlace;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import org.fusesource.restygwt.client.Defaults;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class TritonApp implements EntryPoint {

    private Place defaultPlace = new LoginPlace();

    private SimplePanel appWidget = new SimplePanel();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        useCorrectRequestBaseUrl();
        ClientFactory clientFactory = GWT.create(ClientFactory.class);
        EventBus eventBus = clientFactory.getEventBus();
        //this is for history
        PlaceController placeController = clientFactory.getPlaceController();

        ActivityMapper activityMapper = new TritonActivityMapper(clientFactory);
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(appWidget);

        TritonPlaceHistoryMapper historyMapper = GWT.create(TritonPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        RootPanel.get().add(appWidget);
        historyHandler.handleCurrentHistory();
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
