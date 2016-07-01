package com.xavier.spikedroid.searchlocation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.xavier.spikedroid.R;

import java.util.List;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 28/06/2016.
 */
public class SearchLocation extends AppCompatActivity implements TextWatcher, GoogleResultsListener, AdapterView.OnItemClickListener {

    private AutoCompleteAdapter adapter;
    private GooglePlacesApi googlePlacesApi;
    private CharSequence charSequence;
    private TextView id;
    private TextView name;
    private TextView lat;
    private TextView lng;
    private TextView type;
    private TextView ts;
    private TextView count;
    private TextView sourceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);

        googlePlacesApi = new GooglePlacesApi(this, this);

        adapter = new AutoCompleteAdapter(this);

        // init DB Flow
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());
        Delete.table(Spot.class);

        initAutoCompleteTextView();

        initSpotsInDb();

        initTextViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        googlePlacesApi.connectGoogleClient();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googlePlacesApi.disconnectGoogleClient();
    }

    // --------------------------------------------------
    // AutoCompleteTextView callBack
    // --------------------------------------------------
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        this.charSequence = charSequence;
        if(SQLite.select()
                .from(Spot.class)
                .where(Spot_Table.name.like("%"+charSequence.toString()+"%"))
                .and(Spot_Table.type.eq(Type.GOOGLE_SUGGESTION)).count() == 0 && !charSequence.toString().isEmpty()){
            googlePlacesApi.getPredictions(charSequence.toString());
        }
        adapter.setSpotList(getSpotsFromDb(charSequence.toString()));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Spot spot = (Spot) parent.getItemAtPosition(position);
        if(spot.getLatitude() == null){
            googlePlacesApi.getPlaceById(spot.getSourceId());
        }else{
            Toast.makeText(this, "display spot", Toast.LENGTH_SHORT).show();
            displaySpot(spot);
        }
    }

    // --------------------------------------------------
    // GoogleResultsListener callBack
    // --------------------------------------------------
    @Override
    public void onPredictionsFound(final List<Spot> spots) {

        ProcessModelTransaction<Spot> processModelTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<Spot>() {
                    @Override
                    public void processModel(Spot model) {
                        model.save();
                    }
                }).processListener(new ProcessModelTransaction.OnModelProcessListener<Spot>() {
                    @Override
                    public void onModelProcessed(long current, long total, Spot modifiedModel) {
                        if(current == total - 1){
                            adapter.setSpotList(getSpotsFromDb(charSequence.toString()));

                        }
                    }
                }).addAll(spots).build();
        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(processModelTransaction)
                .build()
                .execute();

    }

    @Override
    public void onPlaceFound(List<Spot> spots) {
        for(Spot spot: spots){
            spot.save();
            displaySpot(spot);
        }

        adapter.setSpotList(getSpotsFromDb(charSequence.toString()));
    }

    // --------------------------------------------------
    // Other Methods
    // --------------------------------------------------
    private void initAutoCompleteTextView(){
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.search);
        assert autoCompleteTextView != null;
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.addTextChangedListener(this);
        autoCompleteTextView.setOnItemClickListener(this);
        autoCompleteTextView.setAdapter(adapter);
    }

    public List<Spot> getSpotsFromDb(String string){
        return SQLite.select()
                .from(Spot.class)
                .where(Spot_Table.name.like(string + "%"))
                .or(Spot_Table.name.like("% " + string + "%"))
                .queryList();
    }

    // --------------------------------------------------
    // Non implemented Methods
    // --------------------------------------------------
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    // --------------------------------------------------
    // Dummy Methods
    // --------------------------------------------------
    private void displaySpot(Spot spot){
        id.setText(String.valueOf(spot.getId()));
        name.setText(spot.getName());
        lat.setText(String.valueOf(spot.getLatitude()));
        lng.setText(String.valueOf(spot.getLongitude()));
        type.setText(spot.getType().toString());
        ts.setText(String.valueOf(spot.getTimestamp()));
        count.setText(String.valueOf(spot.getCount()));
        sourceId.setText(String.valueOf(spot.getSourceId()));
    }

    private void initSpotsInDb(){
        new Spot("Paris", 0.0, 0.0, Type.FAVORITE, null).save();
        new Spot("Montpellier", 0.0, 0.0, Type.FAVORITE, null).save();
        new Spot("Avignon", 0.0, 0.0, Type.FAVORITE, null).save();
        new Spot("Shannon", 0.0, 0.0, Type.FAVORITE, null).save();
    }

    private void initTextViews(){
        id = (TextView) findViewById(R.id.place_id);
        name = (TextView) findViewById(R.id.place_name);
        lat = (TextView) findViewById(R.id.place_lat);
        lng = (TextView) findViewById(R.id.place_lng);
        type = (TextView) findViewById(R.id.place_type);
        ts = (TextView) findViewById(R.id.place_timestamp);
        count = (TextView) findViewById(R.id.place_count);
        sourceId = (TextView) findViewById(R.id.place_source_id);
    }
}
