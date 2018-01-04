package com.eldsoke.islam.dmstask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by islam on 1/4/2018.
 */

public class Owner {
    @SerializedName("login")
    @Expose
    String login;
    @SerializedName("html_url")
    @Expose
    private String repositoryUrl;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }
}
