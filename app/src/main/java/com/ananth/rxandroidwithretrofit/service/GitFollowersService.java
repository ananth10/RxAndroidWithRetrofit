package com.ananth.rxandroidwithretrofit.service;

import com.ananth.rxandroidwithretrofit.model.Follow;
import com.ananth.rxandroidwithretrofit.model.gists.Gists;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Babu on 10/3/2016.
 */
public interface GitFollowersService {
    @GET("users/{username}/followers")
    Observable<List<Follow>> getGitFollowers(@Path("username") String username);
}
