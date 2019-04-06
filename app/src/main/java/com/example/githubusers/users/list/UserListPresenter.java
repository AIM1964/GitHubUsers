package com.example.githubusers.users.list;

import com.example.githubusers.network.GitHubService;
import com.example.githubusers.utils.RxUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

public class UserListPresenter {

  public static final int LOAD_MORE_OFFSET = 10;
  public static final int USER_PAGE_COUNT = 30;

  public interface IView {

    public void onUserLoaded(List<User> userList);

    public void onError(Throwable error);
  }

  private final GitHubService service;
  private IView view;
  private Disposable disposable;
  private ArrayList<User> userList = new ArrayList<>();
  private boolean allUserLoaded;

  public UserListPresenter(GitHubService service) {
    this.service = service;
  }

  public void attachView(IView view) {
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
    if (isLoading()) {
      return;
    }
    loadUsers(0);
  }

  public void setLastItemPosition(int lastItemPosition) {
    if (lastItemPosition >= userList.size() - LOAD_MORE_OFFSET
        && !isLoading()
        && !allUserLoaded) {
      loadUsers(userList.get(userList.size() - 1).getId());
    }
  }

  public void stopLoading() {
    RxUtils.dispose(disposable);
  }

  private void loadUsers(long id) {
    disposable = service.getUsers(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSuccess(users -> userList.addAll(users))
        .doOnSuccess(users -> allUserLoaded = users.size() < USER_PAGE_COUNT)
        .filter(users -> view != null)
        .subscribe(users -> view.onUserLoaded(users), error -> view.onError(error));
  }

  private boolean isLoading() {
    return disposable != null && !disposable.isDisposed();
  }
}
