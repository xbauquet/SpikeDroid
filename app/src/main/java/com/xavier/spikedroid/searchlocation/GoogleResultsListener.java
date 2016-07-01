package com.xavier.spikedroid.searchlocation;

import java.util.List;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 30/06/2016.
 */
public interface GoogleResultsListener {
    void onPredictionsFound(List<Spot> spots);
    void onPlaceFound(List<Spot> spots);
}
