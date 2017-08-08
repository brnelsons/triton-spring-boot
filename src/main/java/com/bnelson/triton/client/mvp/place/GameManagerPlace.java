package com.bnelson.triton.client.mvp.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created by brnel on 8/7/2017.
 */
public class GameManagerPlace extends Place {


    public static class Tokenizer implements PlaceTokenizer<GameManagerPlace>{

        @Override
        public GameManagerPlace getPlace(String token) {
            return new GameManagerPlace();
        }

        @Override
        public String getToken(GameManagerPlace place) {
            return "game-manager";
        }
    }
}
