package com.example.githubusers.users.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.githubusers.App;
import com.example.githubusers.R;
import java.util.List;

public class UserListFragment extends Fragment implements UserListPresenter.View {

  @BindView(R.id.recycler)
  RecyclerView recycler;

  @BindView(R.id.progress_loading)
  ProgressBar loadingProgress;

  @BindView(R.id.button_retry)
  Button retryButton;

  private Unbinder unbinder;
  private UserRecyclerAdapter adapter;
  private UserListPresenter presenter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_list, container, false);
    unbinder = ButterKnife.bind(this, view);

    presenter = new UserListPresenter(((App) getActivity().getApplication()).getGitHubService());
    recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    recycler.setHasFixedSize(true);
    adapter = new UserRecyclerAdapter();
    recycler.setAdapter(adapter);
    retryButton.setOnClickListener(ign -> {
      adapter.clearUsers();
      retryButton.setVisibility(View.GONE);
      loadingProgress.setVisibility(View.VISIBLE);
      presenter.loadInitialUsers();
    });

    presenter.attachView(this);

    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.detachView();
    presenter.stopLoading();
    unbinder.unbind();
  }

  @Override
  public void onUserLoaded(List<User> userList) {
    recycler.setVisibility(View.VISIBLE);
    loadingProgress.setVisibility(View.GONE);
    adapter.addUsers(userList);
  }

  @Override
  public void onError(Throwable error) {
    recycler.setVisibility(View.GONE);
    loadingProgress.setVisibility(View.GONE);
    retryButton.setVisibility(View.VISIBLE);
  }
}
