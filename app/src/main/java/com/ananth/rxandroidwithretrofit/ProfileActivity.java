package com.ananth.rxandroidwithretrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ananth.rxandroidwithretrofit.Utils.PrefUtils;
import com.ananth.rxandroidwithretrofit.model.Github;
import com.ananth.rxandroidwithretrofit.service.GithubService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView mUserImage;
    private TextView mUserName;
    private TextView mCompany;
    private TextView mLocation;
    private TextView mReposCount;
    private TextView mGistsCount;
    private TextView mFollowingsCount;
    private TextView mFollowersCount;
    private String mGitUserName = "";
    private LinearLayout mProgressLay;
    private LinearLayout mNoResultLay;
    private FrameLayout mContentLay;
    private ImageView mBackGroundImage;
    private TextView mNoResult;
    private LinearLayout mReposLay;
    private LinearLayout mGistsLay;
    private LinearLayout mFollowersLay;
    private LinearLayout mFollowingLay;
    private TextView mHappyCoding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        mProgressLay = (LinearLayout) findViewById(R.id.progress_lay);
        mNoResultLay = (LinearLayout) findViewById(R.id.no_result_lay);
        mContentLay = (FrameLayout) findViewById(R.id.content_lay);
        mUserImage = (ImageView) findViewById(R.id.user_img);
        mBackGroundImage = (ImageView) findViewById(R.id.user_bg);
        mUserName = (TextView) findViewById(R.id.user_name);
        mCompany = (TextView) findViewById(R.id.company_name);
        mLocation = (TextView) findViewById(R.id.user_location);
        mReposCount = (TextView) findViewById(R.id.repos_count);
        mGistsCount = (TextView) findViewById(R.id.gist_count);
        mFollowingsCount = (TextView) findViewById(R.id.followings_count);
        mFollowersCount = (TextView) findViewById(R.id.followers_count);
        mHappyCoding = (TextView) findViewById(R.id.happy_coding);
        mReposLay = (LinearLayout) findViewById(R.id.repos);
        mGistsLay = (LinearLayout) findViewById(R.id.gist_lay);
        mFollowersLay = (LinearLayout) findViewById(R.id.followers);
        mFollowingLay = (LinearLayout) findViewById(R.id.followings);
        mNoResult = (TextView) findViewById(R.id.noresult);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        if (getIntent() != null) {
            mGitUserName = getIntent().getStringExtra("username");
        }
        getUserInfo();

        mReposLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, RepositaryList.class));
            }
        });
        mGistsLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, GistsList.class));
            }
        });
        mFollowersLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, FollowersList.class));
            }
        });
        mFollowingLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, FollowingsList.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.logout) {
            SharedPreferences preferences = getSharedPreferences("RxAndroid", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
            return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    private void getUserInfo() {
        mProgressLay.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();

        GithubService githubService = retrofit.create(GithubService.class);
        Observable<Github> call = githubService.getGithubUserInfo(mGitUserName);
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Github>() {

            @Override
            public void onNext(Github github) {
                bindData(github);
            }

            @Override
            public void onCompleted() {
                // Nothing to do here
            }

            @Override
            public void onError(Throwable e) {
                // cast to retrofit.HttpException to get the response code
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

    private void bindData(final Github github) {
        String name = github.getLogin();
        System.out.println("name :" + name);
        if (github.getName() != null) {
            mContentLay.setVisibility(View.VISIBLE);
            mHappyCoding.setVisibility(View.VISIBLE);
            mNoResultLay.setVisibility(View.GONE);
            mProgressLay.setVisibility(View.GONE);
            if(PrefUtils.mSave) {
                PrefUtils.saveData("username", name, ProfileActivity.this);
            }
            mUserName.setText(github.getName());
            if (!TextUtils.isEmpty(github.getCompany())) {
                mCompany.setText(github.getCompany());
            } else {
                mCompany.setVisibility(View.GONE);
            }
            mLocation.setText(github.getLocation());
            mReposCount.setText("" + github.getPublicRepos());
            mGistsCount.setText("" + github.getPublicGists());
            mFollowersCount.setText("" + github.getFollowers());
            mFollowingsCount.setText("" + github.getFollowing());
            Picasso.with(ProfileActivity.this)
                    .load(github.getAvatarUrl()) // thumbnail url goes here
                    .placeholder(R.mipmap.ic_launcher)
                    .transform(new BlurTransformation(ProfileActivity.this, 40))
                    .into(mBackGroundImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            Picasso.with(ProfileActivity.this)
                                    .load(github.getAvatarUrl()) // image url goes here
                                    .placeholder(mUserImage.getDrawable())
                                    .into(mUserImage);
                        }

                        @Override
                        public void onError() {
                        }
                    });
        } else {
            mContentLay.setVisibility(View.GONE);
            mNoResultLay.setVisibility(View.VISIBLE);
            mProgressLay.setVisibility(View.GONE);
            mNoResult.setText("No User Found");
        }
    }
}
