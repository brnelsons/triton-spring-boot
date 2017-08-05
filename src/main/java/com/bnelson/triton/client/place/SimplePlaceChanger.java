package com.bnelson.triton.client.place;

/**
 * Created by brnel on 8/4/2017.
 */
public class SimplePlaceChanger {

    private final PlaceChangeHandler placeChangeHandler;

    public SimplePlaceChanger(PlaceChangeHandler placeChangeHandler) {
        this.placeChangeHandler = placeChangeHandler;
    }

    public void changePlace(SimplePlace place){
        placeChangeHandler.onChange(place);
    }

    public interface PlaceChangeHandler {
        void onChange(SimplePlace place);
    }

}
