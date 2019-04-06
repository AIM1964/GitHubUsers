package com.example.githubusers.users.list;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.githubusers.App;

public class UserListPresenterHolder extends AndroidViewModel {

  private UserListPresenter presenter;

  public UserListPresenterHolder(@NonNull Application application) {
    super(application);
    presenter = new UserListPresenter(((App) application).getGitHubService());
  }

  public UserListPresenter getPresenter() {
    return presenter;
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    presenter.stopLoading();
  }
}
