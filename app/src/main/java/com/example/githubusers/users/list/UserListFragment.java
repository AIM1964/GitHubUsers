package com.example.githubusers.users.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.githubusers.R;
import com.example.githubusers.users.details.UserDetailsFragment;
import java.util.List;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class UserListFragment extends Fragment implements UserListPresenter.IView {

  public static final float SCALE_IN_VALUE = 0.8f;
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
    presenter = ViewModelProviders.of(this).get(UserListPresenterHolder.class).getPresenter();

    recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    recycler.setHasFixedSize(true);
    adapter = new UserRecyclerAdapter();
    adapter.setScrollListener(position -> presenter.setLastItemPosition(position));
    adapter.setItemClickListener(user -> {
      Bundle bundle = new Bundle();
      bundle.putString(UserDetailsFragment.LOGIN_KEY, user.login);
      Navigation.findNavController(getActivity(), R.id.container)
          .navigate(R.id.action_userListFragment_to_userDetailsFragment, bundle);
    });
    recycler.setAdapter(new ScaleInAnimationAdapter(adapter, SCALE_IN_VALUE));
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
    if (adapter.getItemCount() != 0) {
      return;
    }
    recycler.setVisibility(View.GONE);
    loadingProgress.setVisibility(View.GONE);
    retryButton.setVisibility(View.VISIBLE);
  }
}
