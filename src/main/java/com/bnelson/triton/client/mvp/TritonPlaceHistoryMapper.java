package com.bnelson.triton.client.mvp;

import com.bnelson.triton.client.mvp.place.GameManagerPlace;
import com.bnelson.triton.client.mvp.place.LoginPlace;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({GameManagerPlace.Tokenizer.class, LoginPlace.Tokenizer.class})
public interface TritonPlaceHistoryMapper extends PlaceHistoryMapper {
}
