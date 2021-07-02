package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.codepath.apps.restclienttemplate.databinding.ActivityTweetDetailsBinding;
import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class TweetDetailsActivity extends AppCompatActivity {

    //ActivityTweetDetailsBinding binding;
    private final int REQUEST_CODE = 30;
    public static final String EXTRA_MESSAGE = "com.example.Twitter.MESSAGE";

    Tweet tweet;
    ImageView ivProfileImage;
    ImageView ivMedia;
    ImageView ivReplay;
    TextView tvBody;
    TextView tvScreenName;
    TextView tvRelativeTime;
    TextView tvName;
    TextView tvRetweets;
    TextView tvLikes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tweet_details);
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvBody = findViewById(R.id.tvBody);
        tvName = findViewById(R.id.tvName);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvRelativeTime = findViewById(R.id.tvRelativeTime);
        ivMedia = findViewById(R.id.ivMedia);
        tvRetweets = findViewById(R.id.tvRetweets);
        tvLikes = findViewById(R.id.tvLikes);
        ivReplay = findViewById(R.id.ivReplay);

        tvBody.setText(tweet.body);
        tvName.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvRelativeTime.setText(tweet.RelativeTime);
        tvRetweets.setText(Integer.toString(tweet.retweetCount) + " Retweets");
        tvLikes.setText(Integer.toString(tweet.favouriteCount) + " Likes");

        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);
        if (tweet.entity.mediaUrl != null) {
            Glide.with(this).load(tweet.entity.mediaUrl).into(ivMedia);
            ivMedia.setVisibility(View.VISIBLE);
        } else {
            ivMedia.setVisibility(View.GONE);
        }

        ivReplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "@" + tweet.user.screenName);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }
}