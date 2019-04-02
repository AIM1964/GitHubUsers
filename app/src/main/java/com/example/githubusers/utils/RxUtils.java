package com.example.githubusers.utils;

import io.reactivex.disposables.Disposable;

public final class RxUtils {

  private RxUtils() {
  }

  public static void dispose(Disposable... disposables) {
    for (Disposable disposable : disposables) {
      if (disposable != null) {
        disposable.dispose();
      }
    }
  }
}
