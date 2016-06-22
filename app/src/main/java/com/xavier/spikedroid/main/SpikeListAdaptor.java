package com.xavier.spikedroid.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xavier.spikedroid.R;
import com.xavier.spikedroid.githubrepositories.RepositoryListActivity;
import com.xavier.spikedroid.googlemaps.GoogleMapsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 21/06/2016.
 */
public class SpikeListAdaptor extends BaseAdapter {

    private Context context;
    private List<Spike> spikes = new ArrayList<>();

    public SpikeListAdaptor(Context context){
        this.context = context;
        this.spikes.add(new Spike("GitHub Repositories", new Intent(context, RepositoryListActivity.class)));
        this.spikes.add(new Spike("Google maps", new Intent(context, GoogleMapsActivity.class)));
    }

    @Override
    public int getCount() {
        return spikes.size();
    }

    @Override
    public Object getItem(int i) {
        return spikes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.spike_row, parent, false);
        }
        TextView text = (TextView) convertView.findViewById(R.id.spike_name);
        text.setText(spikes.get(position).getName());
        return convertView;
    }
}