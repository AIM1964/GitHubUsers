package com.example.githubusers.users.list;

import com.example.githubusers.network.GitHubService;
import com.example.githubusers.utils.RxUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class UserListPresenter {

  public interface View {

    public void onUserLoaded(List<User> userList);

    public void onError(Throwable error);
  }

  private final GitHubService service;
  private View view;
  private Disposable disposable;
  private ArrayList<User> userList = new ArrayList<>();

  public UserListPresenter(GitHubService service) {
    this.service = service;
  }

  public void attachView(View view) {
    this.view = view;
    if (userList.isEmpty()) {
      loadInitialUsers();
    } else {
      view.onUserLoaded(userList);
    }
  }

  public void detachView() {
    view = null;
  }

  public void loadInitialUsers() {
    userList.clear();
    loadUsers(0);
  }

  public void loadMoreUsers() {
    loadUsers(userList.get(userList.size() - 1).getId());
  }

  public void stopLoading() {
    RxUtils.dispose(disposable);
  }

  private void loadUsers(long id) {
    stopLoading();
    disposable = service.getUsers(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSuccess(users -> userList.addAll(users))
        .filter(users -> view != null)
        .subscribe(users -> view.onUserLoaded(users), error -> view.onError(error));
  }
}
