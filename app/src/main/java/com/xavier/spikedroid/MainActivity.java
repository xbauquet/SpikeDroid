package com.xavier.spikedroid;

import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xavier.spikedroid.main.Spike;
import com.xavier.spikedroid.main.SpikeListAdaptor;
import com.xavier.spikedroid.util.PermissionsProvider;

import java.util.List;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 21/06/2016.
 */
public class MainActivity extends AppCompatActivity {

    private SpikeListAdaptor spikeListAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Ask for permissions needed to use the map:
        // ACCESS_FINE_LOCATION
        // WRITE_EXTERNAL_STORAGE
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionsProvider permissionsProvider = new PermissionsProvider(this);
            permissionsProvider.checkPermissions();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spikeListAdaptor = new SpikeListAdaptor(this);
        ListView spikeList = (ListView) findViewById(R.id.spike_list);
        spikeList.setAdapter(spikeListAdaptor);
        spikeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Spike spike = (Spike) spikeListAdaptor.getItem(i);
                startActivity(spike.getIntent());
            }
        });

    }
}
