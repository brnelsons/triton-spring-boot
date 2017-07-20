package com.bnelson.triton.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by brnel on 6/23/2017.
 */
public abstract class SimpleCallback implements AsyncCallback<Void> {

    public abstract void onSuccessOrFailure(Throwable throwable);

    @Override
    public void onFailure(Throwable caught) {
        onSuccessOrFailure(caught);
    }

    @Override
    public void onSuccess(Void result) {
        onSuccessOrFailure(null);
    }
}
