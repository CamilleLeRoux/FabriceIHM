package fr.unice.polytech.dipn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetui.SearchTimeline;

public class TwitterList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_list);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tweetList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        final TwitterSession session = new TwitterSession(new TwitterAuthToken(getString(R.string.com_twitter_sdk_android_ACCESS_KEY), getString(R.string.com_twitter_sdk_android_ACCESS_SECRET)), 985877416857034752L, "pbunice");
        TwitterCore.getInstance().getSessionManager().setActiveSession(session);

        final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query("#Polytech")
                .maxItemsPerRequest(50)
                .build();

        final CustomTweetTimeLineAdapter adapter = new CustomTweetTimeLineAdapter(this,
                searchTimeline,
                R.style.tw__TweetLightWithActionsStyle,
                null);

        recyclerView.setAdapter(adapter);
    }
}
