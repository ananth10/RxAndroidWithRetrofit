package com.ananth.rxandroidwithretrofit.service;

import com.ananth.rxandroidwithretrofit.model.Follow;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Babu on 10/3/2016.
 */
public interface GitFollowingsService {
    @GET("users/{username}/following")
    Observable<List<Follow>> getGitFollowings(@Path("username") String username);
}
