package com.bnelson.triton.client.ui.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.constants.AlertType;
import org.gwtbootstrap3.client.ui.html.Strong;
import org.gwtbootstrap3.client.ui.html.Text;

/**
 * Created by brnel on 8/1/2017.
 */
public class AlertListWidget extends Composite{
    interface AlertListWidgetUiBinder extends UiBinder<Container, AlertListWidget> {}
    private static AlertListWidgetUiBinder ourUiBinder = GWT.create(AlertListWidgetUiBinder.class);

    @UiField Container container;

    public AlertListWidget() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public void addAlert(String strong, String details, AlertType type){
        Alert alert = new Alert();
        alert.add(new Strong(strong));
        alert.add(new Text(details));
        alert.setType(type);
        alert.setDismissable(true);
        container.add(alert);
    }
}