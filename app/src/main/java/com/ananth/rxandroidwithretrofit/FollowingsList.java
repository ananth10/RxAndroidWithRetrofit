package com.ananth.rxandroidwithretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ananth.rxandroidwithretrofit.Utils.PrefUtils;
import com.ananth.rxandroidwithretrofit.adapters.FollowAdapter;
import com.ananth.rxandroidwithretrofit.model.Follow;
import com.ananth.rxandroidwithretrofit.service.GitFollowingsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FollowingsList extends AppCompatActivity {

    ArrayList<HashMap<String, String>> mAllFollowingsList = new ArrayList<>();
    private RecyclerView mRv;
    private LinearLayout mProgressLay;
    private LinearLayout mNoResultLay;
    private Toolbar toolbar;
    private String mUserName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followings_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mRv = (RecyclerView) findViewById(R.id.following_list);
        mProgressLay = (LinearLayout) findViewById(R.id.progress_lay);
        mNoResultLay = (LinearLayout) findViewById(R.id.no_result_lay);
        if(getIntent()!=null)
        {
            mUserName=getIntent().getStringExtra("username");
        }
        setupRecyclerView();
        getFollowersList();
    }

    private void setupRecyclerView() {
        mRv.setLayoutManager(new LinearLayoutManager(mRv.getContext()));
        mRv.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRv.addItemDecoration(itemDecoration);
        mRv.setItemAnimator(new SlideInUpAnimator());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }

    private void getFollowersList() {
        mProgressLay.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();

        GitFollowingsService gitFollowService = retrofit.create(GitFollowingsService.class);
        Observable<List<Follow>> call = gitFollowService.getGitFollowings(mUserName);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Follow>>() {

            @Override
            public void onNext(List<Follow> gitFollow) {
                for (int i = 0; i < gitFollow.size(); i++) {
                    HashMap<String, String> mMap = new HashMap<String, String>();
                    mMap.put("name", gitFollow.get(i).getLogin());
                    mMap.put("image", gitFollow.get(i).getAvatarUrl());
                    mAllFollowingsList.add(mMap);
                }
            }

            @Override
            public void onCompleted() {
                // Nothing to do here
                mProgressLay.setVisibility(View.GONE);
                if (mAllFollowingsList.size() > 0) {
                    mRv.setVisibility(View.VISIBLE);
                    mRv.setAdapter(new FollowAdapter(FollowingsList.this, mAllFollowingsList));
                } else {
                    mNoResultLay.setVisibility(View.VISIBLE);
                    mRv.setVisibility(View.GONE);

                }
            }

            @Override
            public void onError(Throwable e) {
                // cast to retrofit.HttpException to get the response code
                e.printStackTrace();
                mNoResultLay.setVisibility(View.VISIBLE);
                mProgressLay.setVisibility(View.GONE);
                if (e instanceof HttpException) {
                    HttpException response = null;
                    int code = response.code();
                    System.out.println("code :" + code);
                }
            }


        });
    }
}
