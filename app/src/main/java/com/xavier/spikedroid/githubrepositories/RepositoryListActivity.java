package com.xavier.spikedroid.githubrepositories;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xavier.spikedroid.R;

public class RepositoryListActivity extends AppCompatActivity {

    private boolean bounded = false;
    private RepositoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);

        adapter = new RepositoryListAdapter(this);
        ListView list = (ListView) findViewById(R.id.repository_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Repository repository = (Repository) adapter.getItem(i);
                Intent intent = new Intent(RepositoryListActivity.this, RepositoryDetailActivity.class);
                intent.putExtra("repository", repository);
                startActivity(intent);


            }
        });

    }

    // ========================================
    // Binding to MyService
    // ========================================
    @Override
    protected void onStart() {
        super.onStart();
        if(!bounded){
            // Bind to the service
            Intent intent = new Intent(this, GitHubService.class);
            getApplicationContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
            Log.e("bound", "bound");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (bounded) {
            unbindService(connection);
            bounded = false;
        }
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Log.e("repo", "repo");
            GitHubService.GitHubServiceBinder binder = (GitHubService.GitHubServiceBinder) service;
            binder.getService().getRepositories("xbauquet", adapter);
            //MainActivity.this.unbindService(this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bounded = false;
        }
    };
}

