package com.example.githubusers.users.details;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.githubusers.App;
import android.app.Application;


public class UserDetailsPresenterHolder extends AndroidViewModel {

  private UserDetailsPresenter presenter;

  public UserDetailsPresenterHolder(@NonNull Application application) {
    super(application);
    presenter = new UserDetailsPresenter(((App) application).getGitHubService());
  }

  public UserDetailsPresenter getPresenter() {
    return presenter;
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    presenter.stopLoading();
  }
}
