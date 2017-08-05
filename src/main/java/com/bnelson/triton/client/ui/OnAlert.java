package com.bnelson.triton.client.ui;

import org.gwtbootstrap3.client.ui.constants.AlertType;

/**
 * Created by brnel on 8/1/2017.
 */
public interface OnAlert {
    void alert(String strong, String details, AlertType type);
}
