package com.ananth.rxandroidwithretrofit.service;

import com.ananth.rxandroidwithretrofit.model.Github;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Babu on 9/29/2016.
 */
public interface GithubService {
    @GET("users/{username}")
    Observable<Github> getGithubUserInfo(@Path("username") String username);
}
