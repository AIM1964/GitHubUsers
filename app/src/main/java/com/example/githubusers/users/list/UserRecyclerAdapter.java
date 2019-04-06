package com.example.githubusers.users.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.githubusers.R;
import java.util.ArrayList;
import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter {

  private static final int TYPE_ITEM = 1;
  private static final int TYPE_FOOTER = 2;
  private ArrayList<User> userList = new ArrayList<>();
  private ItemPositionScrollListener scrollListener;
  private ItemClickListener itemClickListener;

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (viewType == TYPE_ITEM) {
      return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
    } else if (viewType == TYPE_FOOTER) {
      return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
    }
    throw new RuntimeException(String.format("there is no type that matches the type: %s", viewType));
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
    if (scrollListener != null) {
      scrollListener.onScrollItem(position);
    }

    if (viewHolder instanceof UserViewHolder) {
      UserViewHolder holder = (UserViewHolder) viewHolder;
      User user = userList.get(position);
      holder.nameText.setText(user.getLogin());
      Glide.with(holder.avatarImage)
          .load(user.getAvatar())
          .apply(RequestOptions.circleCropTransform()
              .error(R.drawable.ic_logo))
          .into(holder.avatarImage);
      holder.itemView.setOnClickListener(ign -> {
        if (itemClickListener != null) {
          itemClickListener.onItemClick(user);
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return userList.size() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    return position == userList.size() ? TYPE_FOOTER : TYPE_ITEM;
  }

  public void addUsers(@NonNull List<User> newUsers) {
    userList.addAll(newUsers);
    notifyItemRangeInserted(getItemCount() - newUsers.size(), newUsers.size());
  }

  public void clearUsers() {
    userList.clear();
    notifyDataSetChanged();
  }

  public void setScrollListener(ItemPositionScrollListener scrollListener) {
    this.scrollListener = scrollListener;
  }

  public void setItemClickListener(ItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public static class UserViewHolder extends RecyclerView.ViewHolder {

    ImageView avatarImage;
    TextView nameText;

    public UserViewHolder(@NonNull View itemView) {
      super(itemView);
      avatarImage = itemView.findViewById(R.id.image_avatar);
      nameText = itemView.findViewById(R.id.text_name);
    }
  }

  public class FooterViewHolder extends RecyclerView.ViewHolder {
    ProgressBar footerLoadingBar;

    public FooterViewHolder(@NonNull View itemView) {
      super(itemView);
      footerLoadingBar = itemView.findViewById(R.id.footerLoadingBar);
      footerLoadingBar.setVisibility(View.VISIBLE);
    }
  }

  public interface ItemPositionScrollListener {
    void onScrollItem(int lastItemPosition);
  }

  public interface ItemClickListener {
    void onItemClick(User user);
  }
}
