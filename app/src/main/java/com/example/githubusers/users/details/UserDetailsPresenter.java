package com.example.githubusers.users.details;

import com.example.githubusers.network.GitHubService;
import com.example.githubusers.utils.RxUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserDetailsPresenter {

  public interface IView {

    public void onUserDetailsLoaded(UserDetails userDetails);

    public void onError(Throwable error);
  }

  private final GitHubService service;
  private Disposable disposable;
  private IView view;
  private String login;
  private UserDetails userDetails;

  public UserDetailsPresenter(GitHubService service) {
    this.service = service;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void attachView(IView view) {
    this.view = view;
    if (userDetails == null && !isLoading()) {
      loadUserDetails();
    } else {
      view.onUserDetailsLoaded(userDetails);
    }
  }

  public void detachView() {
    this.view = null;
  }

  public void loadUserDetails() {
    disposable = service.getUserDetails(login)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSuccess(response -> userDetails = response)
        .filter(response -> view != null)
        .subscribe(response -> view.onUserDetailsLoaded(response), error -> view.onError(error));
  }

  public void stopLoading() {
    RxUtils.dispose(disposable);
  }

  private boolean isLoading() {
    return disposable != null && !disposable.isDisposed();
  }
}
