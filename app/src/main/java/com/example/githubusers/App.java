package com.example.githubusers;

import android.app.Application;
import com.example.githubusers.network.GitHubService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

  GitHubService gitHubService;

  @Override
  public void onCreate() {
    super.onCreate();
    createNetworkServices();
  }

  public GitHubService getGitHubService() {
    return gitHubService;
  }

  private void createNetworkServices() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(logging)
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    gitHubService = retrofit.create(GitHubService.class);
  }
}
