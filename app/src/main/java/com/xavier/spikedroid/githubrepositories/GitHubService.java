package com.xavier.spikedroid.githubrepositories;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Xavier Bauquet <xavier.bauquet@gmail.com> on 22/06/2016.
 */
public class GitHubService extends IntentService {

    public static final String API_URL = "https://api.github.com";
    public RetrofitClient retrofitClient;

    public GitHubService() {
        super(GitHubService.class.getSimpleName());
    }

    // --------------------------------------------------------------
    // Binder
    // --------------------------------------------------------------
    // Implementation of the service that return itself
    private final IBinder binder = new GitHubServiceBinder();

    public class GitHubServiceBinder extends Binder {
        GitHubService getService() {
            return GitHubService.this;
        }
    }

    public void getRepositories(String userName, final RepositoryListAdapter adapter) {
        Call<List<Repository>> call = retrofitClient.getRepositories(userName);
        call.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                adapter.updateRepositories(response.body());
                Log.e("listRepo", response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {

            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("onBind", "before");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitClient = retrofit.create(RetrofitClient.class);
        Log.e("onBind", "after");
        return binder;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public interface RetrofitClient {
        @GET("/users/{owner}/repos")
        Call<List<Repository>> getRepositories(
                @Path("owner") String owner
        );
    }
}