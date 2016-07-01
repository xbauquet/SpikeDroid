package com.xavier.spikedroid.searchlocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.xavier.spikedroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 30/06/2016.
 */
public class AutoCompleteAdapter extends BaseAdapter implements Filterable{

    private Context context;
    private List<Spot> spotList;

    public AutoCompleteAdapter(Context context){
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return spotList.size();
    }

    @Override
    public Object getItem(int i) {
        return spotList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return spotList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.autocomplete_adapter_row, parent, false);
        }

        TextView text = (TextView) convertView.findViewById(R.id.autocomplete_adapter_label);
        text.setText(spotList.get(position).getName());
        return convertView;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0) {
                    results.values = spotList;
                    results.count = spotList.size();
                } else {
                    List<Spot> filterResultsData = new ArrayList<>();

                    for(Spot spot : spotList) {
                        if(spot.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            filterResultsData.add(spot);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                spotList = (List<Spot>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
