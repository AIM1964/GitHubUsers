package com.example.githubusers.users.details;

import com.example.githubusers.users.list.User;
import com.google.gson.annotations.SerializedName;
import java.util.Objects;

public class UserDetails extends User {

  private String name;
  private String company;
  private String location;
  private String email;
  private String bio;
  private String url;
  @SerializedName("public_repos")
  private int repoCount;
  private int followers;

  public UserDetails(long id, String avatar, String login, String name, String company, String location,
                     String email, String bio, String url, int repoCount, int followers) {
    super(id, avatar, login);
    this.name = name;
    this.company = company;
    this.location = location;
    this.email = email;
    this.bio = bio;
    this.url = url;
    this.repoCount = repoCount;
    this.followers = followers;
  }

  public String getName() {
    return name;
  }

  public String getCompany() {
    return company;
  }

  public String getLocation() {
    return location;
  }

  public String getEmail() {
    return email;
  }

  public String getBio() {
    return bio;
  }

  public String getUrl() {
    return url;
  }

  public int getRepoCount() {
    return repoCount;
  }

  public int getFollowers() {
    return followers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserDetails)) return false;
    if (!super.equals(o)) return false;
    UserDetails that = (UserDetails) o;
    return getRepoCount() == that.getRepoCount() &&
        getFollowers() == that.getFollowers() &&
        Objects.equals(getName(), that.getName()) &&
        Objects.equals(getCompany(), that.getCompany()) &&
        Objects.equals(getLocation(), that.getLocation()) &&
        Objects.equals(getEmail(), that.getEmail()) &&
        Objects.equals(getBio(), that.getBio()) &&
        Objects.equals(getUrl(), that.getUrl());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getName(), getCompany(), getLocation(), getEmail(),
        getBio(), getUrl(), getRepoCount(), getFollowers());
  }

  @Override
  public String toString() {
    return "UserDetails{" +
        ", id=" + id +
        ", avatar='" + avatar + '\'' +
        ", login='" + login + '\'' +
        "name='" + name + '\'' +
        ", company='" + company + '\'' +
        ", location='" + location + '\'' +
        ", email='" + email + '\'' +
        ", bio='" + bio + '\'' +
        ", url='" + url + '\'' +
        ", repoCount=" + repoCount +
        ", followers=" + followers +
        '}';
  }
}
