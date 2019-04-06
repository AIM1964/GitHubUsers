package com.example.githubusers.users.list;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class User {

  protected long id;
  @SerializedName("avatar_url")
  protected String avatar;
  protected String login;

  public User(final long id, final String avatar, final String login) {
    this.id = id;
    this.avatar = avatar;
    this.login = login;
  }

  public long getId() {
    return id;
  }

  public String getAvatar() {
    return avatar;
  }

  public String getLogin() {
    return login;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return getId() == user.getId() &&
        Objects.equals(getAvatar(), user.getAvatar()) &&
        Objects.equals(getLogin(), user.getLogin());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getAvatar(), getLogin());
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", avatar='" + avatar + '\'' +
        ", login='" + login + '\'' +
        '}';
  }
}
