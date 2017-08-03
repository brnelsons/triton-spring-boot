package com.bnelson.triton.shared;

/**
 * Created by brnel on 7/24/2017.
 */
public enum MaterialColor {
    BLACK("#000000"),
    GREEN_500("#4CAF50"),
    YELLOW_500("#FFEB3B"),
    RED_500("#F44336");

    private final String color;

    MaterialColor(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
