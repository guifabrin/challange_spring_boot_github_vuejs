package com.github.guifabrin.application.api.v1.entities;

import java.util.Date;

public class GithubUser {

    public boolean hireable;
    public boolean site_admin;
    public Date created_at;
    public Date updated_at;
    public GithubOrg[] orgs;
    public GithubRepo[] repos;
    public int followers;
    public int following;
    public int id;
    public int public_gists;
    public int public_repos;
    public String avatar_url;
    public String bio;
    public String blog;
    public String company;
    public String email;
    public String events_url;
    public String followers_url;
    public String following_url;
    public String gists_url;
    public String gravatar_id;
    public String html_url;
    public String location;
    public String login;
    public String name;
    public String node_id;
    public String organizations_url;
    public String received_events_url;
    public String repos_url;
    public String starred_url;
    public String subscriptions_url;
    public String twitter_username;
    public String type;
    public String url;
}
