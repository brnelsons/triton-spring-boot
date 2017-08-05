package com.bnelson.triton.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Injector;

/**
 * Created by brnel on 8/4/2017.
 */
@GinModules(GinModule.class)
public interface GinInjector extends Ginjector {
    public static final Injector INSTANCE = GWT.create(GinInjector.class);

    public EventBus getEventBus();
}
