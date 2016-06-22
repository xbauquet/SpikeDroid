package com.xavier.spikedroid.githubrepositories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.xavier.spikedroid.R;

public class RepositoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);

        TextView textView = (TextView) findViewById(R.id.repository_detail_name);

        Bundle extras = getIntent().getExtras();
        Repository repository = (Repository) extras.get("repository");
        textView.setText(repository.getName());
    }
}
