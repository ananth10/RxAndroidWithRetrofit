package com.ananth.rxandroidwithretrofit.service;

import com.ananth.rxandroidwithretrofit.model.GitRepo;
import com.ananth.rxandroidwithretrofit.model.Github;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Babu on 10/3/2016.
 */
public interface GitReposService {
    @GET("users/{username}/repos")
    Observable<List<GitRepo>> getGitRepos(@Path("username") String username);
}
