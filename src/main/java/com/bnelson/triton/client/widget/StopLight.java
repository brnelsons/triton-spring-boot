package com.bnelson.triton.client.widget;

import com.bnelson.triton.shared.MaterialColor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import org.gwtbootstrap3.client.ui.Icon;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.html.Span;

/**
 * Created by brnel on 7/24/2017.
 */
public class StopLight extends Composite{
    interface StopLightUiBinder extends UiBinder<Span, StopLight> {}

    private static StopLightUiBinder ourUiBinder = GWT.create(StopLightUiBinder.class);

    @UiField (provided = true)Icon red;
    @UiField (provided = true)Icon yellow;
    @UiField (provided = true)Icon green;
    @UiField (provided = true)Icon black;

    private Light light;

    public enum Light{
        GREEN, RED, YELLOW, BLACK;
    }

    public StopLight(){
        this(Light.BLACK);
    }

    public StopLight(Light defaultLight) {
        red = new Icon(IconType.CIRCLE);
        red.setColor(MaterialColor.RED_500.getColor());
        yellow = new Icon(IconType.CIRCLE);
        yellow.setColor(MaterialColor.YELLOW_500.getColor());
        green = new Icon(IconType.CIRCLE);
        green.setColor(MaterialColor.GREEN_500.getColor());
        black = new Icon(IconType.CIRCLE);
        black.setColor(MaterialColor.BLACK.getColor());
        initWidget(ourUiBinder.createAndBindUi(this));
        setLight(defaultLight);
    }

    public void setLight(Light light) {
        this.light = light;
        hideAll();
        switch(light){
            case GREEN:
                green.setVisible(true);
                break;
            case YELLOW:
                yellow.setVisible(true);
                break;
            case RED:
                red.setVisible(true);
                break;
            case BLACK:
                black.setVisible(true);
                break;
        }
    }

    private void hideAll() {
        green.setVisible(false);
        yellow.setVisible(false);
        red.setVisible(false);
        black.setVisible(false);
    }

    public Light getLight(){
        return light;
    }


}