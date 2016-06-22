package com.xavier.spikedroid.githubrepositories;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xavier.spikedroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 22/06/2016.
 */
public class RepositoryListAdapter extends BaseAdapter {

    private Context context;
    private List<Repository> repositoryList;

    public RepositoryListAdapter(Context context){
        this.context = context;
        Repository repository = new Repository();
        repository.setName("Loading ...");
        repositoryList = new ArrayList<Repository>();
        repositoryList.add(repository);
    }

    public void updateRepositories(List<Repository> repositories){
        if(!repositories.isEmpty()) {
            Log.e("notEmpty", "notEmpty");
            this.repositoryList = repositories;
        }else{
            Log.e("Empty", "Empty");
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return repositoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return repositoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.github_list_row, parent, false);
        }
        TextView text = (TextView) convertView.findViewById(R.id.repository_name);
        text.setText(repositoryList.get(position).getName());
        return convertView;
    }
}