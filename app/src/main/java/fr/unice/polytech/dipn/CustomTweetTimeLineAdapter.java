package fr.unice.polytech.dipn;

import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
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

    protected CustomTweetTimeLineAdapter(Context context, Timeline<Tweet> timeline, int styleResId, Callback<Tweet> cb) {
        super(context, timeline, styleResId, cb);
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Tweet tweet = new TweetBuilder().build();
        final CompactTweetView compactTweetView = new CompactTweetView(context, tweet, styleResId);
        compactTweetView.setOnActionCallback(actionCallback);
        compactTweetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(context, IncidentForm.class);
                intent.putExtra("fromTweet",true);
                System.out.println("Clicked on "+tweet.coordinates);
                intent.putExtra("textFromTweet",tweet.text);
                context.startActivity(intent);
                return false;
            }
        });
        return new TweetViewHolder(compactTweetView);
    }
}
