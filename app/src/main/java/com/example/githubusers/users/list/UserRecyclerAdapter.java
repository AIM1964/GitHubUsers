package com.example.githubusers.users.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.githubusers.R;
import java.util.ArrayList;
import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter {

  ArrayList<User> userList = new ArrayList<>();

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new UserViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false)
    );
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
    UserViewHolder holder = (UserViewHolder) viewHolder;
    User user = userList.get(position);
    holder.nameText.setText(user.getName());
    Glide.with(holder.avatarImage)
        .load(user.getAvatar())
        .apply(RequestOptions.circleCropTransform()
            .error(R.drawable.ic_logo))
        .into(holder.avatarImage);
  }

  @Override
  public int getItemCount() {
    return userList.size();
  }

  public void addUsers(@NonNull List<User> newUsers) {
    userList.addAll(newUsers);
    notifyItemRangeInserted(getItemCount() - newUsers.size(), newUsers.size());
  }

  public void clearUsers() {
    userList.clear();
    notifyDataSetChanged();
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
}
