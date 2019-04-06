package com.example.githubusers.users.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.githubusers.R;

public class UserDetailsFragment extends Fragment implements UserDetailsPresenter.IView {

  public static final String LOGIN_KEY = "LOGIN_KEY";

  //regionViews
  @BindView(R.id.image_avatar)
  ImageView avatarImage;

  @BindView(R.id.text_id)
  TextView idText;

  @BindView(R.id.text_login)
  TextView loginText;

  @BindView(R.id.text_name)
  TextView nameText;

  @BindView(R.id.text_location)
  TextView locationText;

  @BindView(R.id.text_email)
  TextView emailText;

  @BindView(R.id.text_bio)
  TextView bioText;

  @BindView(R.id.text_url)
  TextView urlText;

  @BindView(R.id.text_repo_count)
  TextView repoCountText;

  @BindView(R.id.text_follower_count)
  TextView followerCountText;

  @BindView(R.id.group_content)
  Group contentGroup;

  @BindView(R.id.progress_loading)
  ProgressBar loadingProgress;

  @BindView(R.id.button_retry)
  Button retryButton;
  //endregion

  private Unbinder unbinder;
  private UserDetailsPresenter presenter;
  private String login;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_details, container, false);
    unbinder = ButterKnife.bind(this, view);
    login = getArguments().getString(LOGIN_KEY);
    loginText.setText(login);
    retryButton.setOnClickListener(ign -> {
      retryButton.setVisibility(View.GONE);
      loadingProgress.setVisibility(View.VISIBLE);
      presenter.loadUserDetails();
    });

    presenter = ViewModelProviders.of(this).get(UserDetailsPresenterHolder.class).getPresenter();
    presenter.setLogin(login);
    presenter.attachView(this);

    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.detachView();
    unbinder.unbind();
  }

  @Override
  public void onUserDetailsLoaded(UserDetails userDetails) {
    loadingProgress.setVisibility(View.GONE);
    contentGroup.setVisibility(View.VISIBLE);
    Glide.with(getContext())
        .load(userDetails.getAvatar())
        .apply(RequestOptions.circleCropTransform()
            .error(R.drawable.ic_logo))
        .into(avatarImage);
    idText.setText(getString(R.string.id_mask, userDetails.getId()));
    loginText.setText(getString(R.string.login_mask, userDetails.getLogin()));
    nameText.setText(userDetails.getName());
    locationText.setText(userDetails.getLocation());
    emailText.setText(userDetails.getEmail());
    bioText.setText(userDetails.getBio());
    urlText.setText(userDetails.getUrl());
    repoCountText.setText(getString(R.string.repo_count_mask, userDetails.getRepoCount()));
    followerCountText.setText(getString(R.string.followers_count_mask, userDetails.getFollowers()));
  }

  @Override
  public void onError(Throwable error) {
    loadingProgress.setVisibility(View.GONE);
    contentGroup.setVisibility(View.GONE);
    retryButton.setVisibility(View.VISIBLE);
  }
}
