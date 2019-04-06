package com.example.githubusers.network;

import com.example.githubusers.users.details.UserDetails;
import com.example.githubusers.users.list.User;
import io.reactivex.Single;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

  @GET("users")
  Single<List<User>> getUsers(@Query("since") long id);

  @GET("users/{user}")
  Single<UserDetails> getUserDetails(@Path("user") String login);
}
