package com.eldsoke.islam.dmstask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by islam on 1/3/2018.
 */

public class Repositories {

    @SerializedName("name")
    @Expose
    private String repoName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("html_url")
    @Expose
    private String repositoryUrl;

    @SerializedName("fork")
    @Expose
    private String fork;
    @SerializedName("owner")
    @Expose
    private Owner owner;


    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }


    public String getFork() {
        return fork;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
