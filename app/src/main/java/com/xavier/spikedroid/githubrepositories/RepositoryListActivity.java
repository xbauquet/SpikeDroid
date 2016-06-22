package com.xavier.spikedroid.githubrepositories;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xavier.spikedroid.R;

public class RepositoryListActivity extends AppCompatActivity {

    private boolean bounded = false;
    private RepositoryListAdapter adapter;
    private String userName = "xbauquet";
    private GitHubService.GitHubServiceBinder binder;
    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);


        userNameTextView = (TextView) findViewById(R.id.github_user_name);
        userNameTextView.setText(userName + " repositories :");

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

        final EditText searchRepositories = (EditText) findViewById(R.id.search_repositories);
        assert searchRepositories != null;
        searchRepositories.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    binder.getService().getRepositories(searchRepositories.getText().toString(), adapter);
                    userNameTextView.setText(searchRepositories.getText().toString() + " repositories :");
                    Toast.makeText(RepositoryListActivity.this, "Loading ...", Toast.LENGTH_SHORT).show();
                }
                return false;
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
            binder = (GitHubService.GitHubServiceBinder) service;
            binder.getService().getRepositories(userName, adapter);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bounded = false;
        }
    };
}

