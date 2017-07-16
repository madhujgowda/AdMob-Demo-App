package cc.cu.web_zone.www.ads;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private InterstitialAd interstitialAd;
    private RewardedVideoAd rewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        MobileAds.initialize(this,getApplicationContext().getResources().getString(R.string.admob_app_id));

        //banner ad
        AdView mAdView = (AdView) findViewById(R.id.adView);
        /* Testing with real ads (even if you never tap on them) is against AdMob policy and can cause your account to be suspended.
        So add your device ID as test device id. */
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getApplicationContext().getResources().getString(R.string.test_device_id))
                .build();
        mAdView.loadAd(adRequest);

        //Interstitial ad
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getApplicationContext().getResources().getString(R.string.interstitial_ad_unit_id));
        interstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(getApplicationContext().getResources().getString(R.string.test_device_id))
                .build());

        interstitialAd.setAdListener(new AdListener(){

            // this method is a used to load a new interstitial ad just after displaying the previous one
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder()
                        .addTestDevice(getApplicationContext().getResources().getString(R.string.test_device_id))
                        .build());
            }
        });

        //reward video ad
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        rewardedVideoAd.loadAd(getApplicationContext().getResources().getString(R.string.rewarded_video_ad_unit_id), new AdRequest.Builder()
                .addTestDevice(getApplicationContext().getResources().getString(R.string.test_device_id))
                .build());
    }

    public void showAds(View view)
    {
        int id = view.getId();

        switch (id)
        {
            case R.id.button_interstitial_ad:
                if (interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Interstitial Ad not loaded yet",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.button_video_ad:
                if (rewardedVideoAd.isLoaded())
                {
                    rewardedVideoAd.show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Video Ad is not loaded yet",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        rewardedVideoAd.loadAd(getApplicationContext().getResources().getString(R.string.rewarded_video_ad_unit_id), new AdRequest.Builder()
                .addTestDevice(getApplicationContext().getResources().getString(R.string.test_device_id))
                .build());
        Toast.makeText(this,"Thanks for watching the video ad",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        rewardedVideoAd.loadAd(getApplicationContext().getResources().getString(R.string.rewarded_video_ad_unit_id), new AdRequest.Builder()
                .addTestDevice(getApplicationContext().getResources().getString(R.string.test_device_id))
                .build());
    }
}
