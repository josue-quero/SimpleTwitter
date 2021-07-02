package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    public static final String EXTRA_MESSAGE = "com.example.Twitter.MESSAGE";
    private final int REQUEST_CODE = 40;
    Context context;
    List<Tweet> tweets;

    // Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate a layout for tweet
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        //Get data at position
        Tweet tweet = tweets.get(position);
        // Bind the tweet with the viewholder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Define a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivProfileImage;
        ImageView ivMedia;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvRelativeTime;
        TextView tvName;
        ImageButton btnReply;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById(R.id.tvName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvRelativeTime = itemView.findViewById(R.id.tvRelativeTime);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            btnReply = itemView.findViewById(R.id.ivReply2);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Tweet tweet = tweets.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                // show the activity
                context.startActivity(intent);
            }
        }

        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvName.setText(tweet.user.name);
            tvScreenName.setText("@" + tweet.user.screenName);
            tvRelativeTime.setText(tweet.RelativeTime);

            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            if (tweet.entity.mediaUrl != null) {
                Glide.with(context).load(tweet.entity.mediaUrl).into(ivMedia);
                ivMedia.setVisibility(View.VISIBLE);
            } else {
                ivMedia.setVisibility(View.GONE);
            }

            btnReply.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    // Compose icon has been selected
                    //Navigate to compose activity
                    Intent intent = new Intent(context, ComposeActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, "@" + tweet.user.screenName);
                    ((Activity) context).startActivityForResult(intent, TimelineActivity.REQUEST_COMPOSE);
                }
            });
        }
    }
}
