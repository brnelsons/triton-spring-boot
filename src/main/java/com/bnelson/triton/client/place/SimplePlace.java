package com.bnelson.triton.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Composite;

/**
 * Created by brnel on 8/4/2017.
 */
public abstract class SimplePlace extends Place{

    private final String placeName;
    private final Composite composite;

    public SimplePlace(String placeName, Composite composite) {
        this.placeName = placeName;
        this.composite = composite;
    }

    public String getPlaceName() {
        return placeName;
    }

    public Composite getComposite() {
        return composite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimplePlace that = (SimplePlace) o;

        if (placeName != null ? !placeName.equals(that.placeName) : that.placeName != null) return false;
        return composite != null ? composite.equals(that.composite) : that.composite == null;

    }

    @Override
    public int hashCode() {
        int result = placeName != null ? placeName.hashCode() : 0;
        result = 31 * result + (composite != null ? composite.hashCode() : 0);
        return result;
    }
}
