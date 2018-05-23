package fr.unice.polytech.dipn;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

/**
 * Created by François on 22/05/2018.
 */

public class CustomTweetTimeLineAdapter extends TweetTimelineRecyclerViewAdapter {

    private Tweet tweet;

    protected CustomTweetTimeLineAdapter(Context context, Timeline<Tweet> timeline, int styleResId, Callback<Tweet> cb) {
        super(context, timeline, styleResId, cb);
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.tweet = new TweetBuilder().build();
        final CompactTweetView compactTweetView = new CompactTweetView(context, tweet, styleResId);
        compactTweetView.setOnActionCallback(actionCallback);
        compactTweetView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, IncidentForm.class);

                intent.putExtra("fromTweet", true);
                intent.putExtra("textFromTweet", compactTweetView.getTweet().text);
                context.startActivity(intent);
                return false;
            }
        });
        return new TweetViewHolder(compactTweetView);
    }
}
