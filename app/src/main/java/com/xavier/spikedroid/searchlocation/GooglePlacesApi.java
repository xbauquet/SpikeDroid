package com.xavier.spikedroid.searchlocation;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 30/06/2016.
 */
public class GooglePlacesApi {

    private GoogleApiClient googleApiClient;
    private PendingResult<AutocompletePredictionBuffer> googlePrediction;
    private GoogleResultsListener googleResultsListener;

    public GooglePlacesApi(Context context, GoogleResultsListener googleResultsListener){
        this.googleResultsListener = googleResultsListener;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
    }

    void connectGoogleClient(){
        googleApiClient.connect();
    }

    void disconnectGoogleClient(){
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    void getPredictions(String string) {
        googlePrediction = Places.GeoDataApi.getAutocompletePredictions(googleApiClient,
                string, null, null);

        googlePrediction.setResultCallback(predictionResultCallback);
    }

    void getPlaceById(String id){
        PendingResult<PlaceBuffer> placeResult =
                Places.GeoDataApi.getPlaceById(googleApiClient, id);
        placeResult.setResultCallback(placeResultCallback);
    }


    private ResultCallback<AutocompletePredictionBuffer> predictionResultCallback = new ResultCallback<AutocompletePredictionBuffer>() {

        @Override
        public void onResult(@NonNull AutocompletePredictionBuffer autocompletePredictions) {
            List<Spot> list = new ArrayList<>();
            for (AutocompletePrediction ap : autocompletePredictions) {
                list.add(new Spot(ap.getPrimaryText(null).toString(), null, null, Type.GOOGLE_SUGGESTION, ap.getPlaceId()));
            }
            googleResultsListener.onPredictionsFound(list);
        }
    };

    private ResultCallback<PlaceBuffer> placeResultCallback = new ResultCallback<PlaceBuffer>() {

        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            List<Spot> list = new ArrayList<>();
            for (Place place : places) {
                list.add(new Spot(place.getName().toString(), place.getLatLng().latitude, place.getLatLng().longitude, Type.GOOGLE_SUGGESTION, place.getId()));
            }
            googleResultsListener.onPlaceFound(list);

        }
    };
}
