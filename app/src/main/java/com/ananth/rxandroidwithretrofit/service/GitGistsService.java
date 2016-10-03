package com.ananth.rxandroidwithretrofit.service;


import com.ananth.rxandroidwithretrofit.model.gists.Gists;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Babu on 10/3/2016.
 */
public interface GitGistsService {
    @GET("users/{username}/gists")
    Observable<List<Gists>> getGitGists(@Path("username") String username);
}
