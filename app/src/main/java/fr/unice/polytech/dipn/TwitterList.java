package fr.unice.polytech.dipn;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TwitterList extends AppCompatActivity {

    boolean isHashTag;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.isHashTag=true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_list);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tweetList);
        this.rv = recyclerView;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        final TwitterSession session = new TwitterSession(new TwitterAuthToken(getString(R.string.com_twitter_sdk_android_ACCESS_KEY), getString(R.string.com_twitter_sdk_android_ACCESS_SECRET)), 985877416857034752L, "pbunice");
        TwitterCore.getInstance().getSessionManager().setActiveSession(session);

        switchTimeLine();
        FloatingActionButton twitSwitch = findViewById(R.id.twitterSwitch);
        twitSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchTimeLine();
            }
        });
    }

    public void switchTimeLine() {
        final CustomTweetTimeLineAdapter adapter;
        if (isHashTag) {
            final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                    .query("#Polytech")
                    .maxItemsPerRequest(50)
                    .build();

            adapter = new CustomTweetTimeLineAdapter(this,
                    searchTimeline,
                    R.style.tw__TweetLightWithActionsStyle,
                    null);
        } else {
            UserTimeline userTimeline = new UserTimeline.Builder().screenName("pbunice").build();

            adapter = new CustomTweetTimeLineAdapter(this,
                    userTimeline,
                    R.style.tw__TweetLightWithActionsStyle,
                    null);
        }
        rv.setAdapter(adapter);
        this.isHashTag=!this.isHashTag;

    }
}
