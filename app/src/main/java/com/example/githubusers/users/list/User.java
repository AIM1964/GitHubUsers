package com.example.githubusers.users.list;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public final class User {

  private long id;
  @SerializedName("avatar_url") private String avatar;
  @SerializedName("login") private String name;

  public User(final long id, final String avatar, final String name) {
    this.id = id;
    this.avatar = avatar;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public String getAvatar() {
    return avatar;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return getId() == user.getId() &&
        Objects.equals(getAvatar(), user.getAvatar()) &&
        Objects.equals(getName(), user.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getAvatar(), getName());
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", avatar='" + avatar + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
