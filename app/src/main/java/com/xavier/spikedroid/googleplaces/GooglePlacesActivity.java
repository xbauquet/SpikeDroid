package com.xavier.spikedroid.googleplaces;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.xavier.spikedroid.R;

public class GooglePlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_places);

        // https://developers.google.com/places/android-api/autocomplete
        //
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
               displayPlace(place);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(GooglePlacesActivity.this, "Error : " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displayPlace(Place place){
        TextView id = (TextView) findViewById(R.id.search_location_response_id);
        assert id != null;
        id.setText("Id : " + place.getId());

        TextView name = (TextView) findViewById(R.id.search_location_response_name);
        assert name != null;
        name.setText("Name : " + place.getName());

        TextView lat = (TextView) findViewById(R.id.search_location_response_lat);
        assert lat != null;
        lat.setText("Latitude : " + place.getLatLng().latitude);

        TextView lon = (TextView) findViewById(R.id.search_location_response_long);
        assert lon != null;
        lon.setText("Longitude : " + place.getLatLng().longitude);

        /*TextView address = (TextView) findViewById(R.id.search_location_response_address);
        assert address != null;
        address.setText("Address : " + place.getAddress());

        TextView attributions = (TextView) findViewById(R.id.search_location_response_attributions);
        assert attributions != null;
        attributions.setText("Attributions : " + place.getAttributions());

        TextView locale = (TextView) findViewById(R.id.search_location_response_locale);
        assert locale != null;
        locale.setText("Locale : " + place.getLocale());

        TextView phone = (TextView) findViewById(R.id.search_location_response_phonenumber);
        assert phone != null;
        phone.setText("Phone : " + place.getPhoneNumber());

        TextView placeTypes = (TextView) findViewById(R.id.search_location_response_placetypes);
        assert placeTypes != null;
        placeTypes.setText("Place Types : " + place.getPlaceTypes());

        TextView priceLevel = (TextView) findViewById(R.id.search_location_response_pricelevel);
        assert priceLevel != null;
        priceLevel.setText("Price level : " + place.getPriceLevel());

        TextView rating = (TextView) findViewById(R.id.search_location_response_rating);
        assert rating != null;
        rating.setText("Rating : " + place.getRating());

        TextView viewPort = (TextView) findViewById(R.id.search_location_response_viewport);
        assert viewPort != null;
        viewPort.setText("View port : " + place.getViewport());

        TextView webSite = (TextView) findViewById(R.id.search_location_response_website);
        assert webSite != null;
        webSite.setText("WebSite : " + place.getWebsiteUri());*/
    }
}
