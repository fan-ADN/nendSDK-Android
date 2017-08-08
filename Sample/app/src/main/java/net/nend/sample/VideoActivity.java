package net.nend.sample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.nend.android.NendAdInterstitialVideo;
import net.nend.android.NendAdRewardItem;
import net.nend.android.NendAdRewardedListener;
import net.nend.android.NendAdRewardedVideo;
import net.nend.android.NendAdVideo;
import net.nend.android.NendAdVideoListener;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = "NEND_VIDEO";
    private static final int YOUR_SPOT_ID_REWARDED = 12345;
    private static final int YOUR_SPOT_ID_INTERSTITIAL = 12345;
    private static final String YOUR_API_KEY_REWARDED = "YOUR_API_KEY_REWARDED";
    private static final String YOUR_API_KEY_INTERSTITIAL= "YOUR_API_KEY_INTERSTITIAL";
    private static final String USER_ID = "DUMMY_USER_ID";
    private ProgressDialog mProgressDialog;
    private NendAdRewardedVideo mNendAdRewardedVideo;
    private NendAdInterstitialVideo mNendAdInterstitialVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
    }

    public void onClickLoadReward(View view) {
        Log.d(TAG, "Click load reward button.");

        if (null == mNendAdRewardedVideo) {
            mNendAdRewardedVideo = new NendAdRewardedVideo(this, YOUR_SPOT_ID_REWARDED, YOUR_API_KEY_REWARDED);
            mNendAdRewardedVideo.setUserId(USER_ID);
            mNendAdRewardedVideo.setAdListener(new NendAdRewardedListener() {
                @Override
                public void onRewarded(NendAdVideo nendAdVideo, NendAdRewardItem nendAdRewardItem) {
                    String name = nendAdRewardItem.getCurrencyName();
                    int amount = nendAdRewardItem.getCurrencyAmount();
                    Log.d(TAG, "onRewarded");
                    Log.d(TAG, "NendAdRewardItem Name : " + name);
                    Log.d(TAG, "NendAdRewardItem Amount : " + amount);
                    Toast.makeText(VideoActivity.this, "onRewarded, Name : " + name + " Amount : " + amount, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onLoaded(NendAdVideo nendAdVideo) {
                    mProgressDialog.dismiss();
                    Log.d(TAG, "onLoaded reward");
                    Toast.makeText(VideoActivity.this, "onLoaded reward", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailedToLoad(NendAdVideo nendAdVideo, int errorCode) {
                    mProgressDialog.dismiss();
                    Log.w(TAG, "onFailedToLoad reward");
                    Log.w(TAG, "VideoAdError code : " + errorCode);
                    Toast.makeText(VideoActivity.this, "onFailedToLoad, code : " + errorCode, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailedToPlay(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onFailedToPlay reward");
                    Toast.makeText(VideoActivity.this, "onFailedToPlay reward", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onShown(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onShown reward");
                    Toast.makeText(VideoActivity.this, "onShown reward", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClosed(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onClosed reward");
                    Toast.makeText(VideoActivity.this, "onClosed reward", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStarted(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onStarted reward");
                    Toast.makeText(VideoActivity.this, "onStarted reward", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStopped(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onStopped reward");
                    Toast.makeText(VideoActivity.this, "onStopped reward", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCompleted(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onCompleted reward");
                    Toast.makeText(VideoActivity.this, "onCompleted reward", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdClicked(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onAdClicked reward");
                    Toast.makeText(VideoActivity.this, "onAdClicked reward", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onInformationClicked(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onInformationClicked reward");
                    Toast.makeText(VideoActivity.this, "onInformationClicked reward", Toast.LENGTH_SHORT).show();
                }
            });
        }

        mNendAdRewardedVideo.loadAd();
        createProgressDialog("Rewarded");
    }

    public void onClickShowReward(View view) {
        Log.d(TAG, "Click show reward button.");

        if (null != mNendAdRewardedVideo && mNendAdRewardedVideo.isLoaded()) {
            mNendAdRewardedVideo.showAd(this);
        } else {
            Log.d(TAG, "Loading is not completed.");
        }

    }

    public void onClickReleaseReward(View view) {
        Log.d(TAG, "Click release reward button.");
        releaseRewardVideoInstance();
    }

    private void releaseRewardVideoInstance() {
        if (null != mNendAdRewardedVideo) {
            mNendAdRewardedVideo.releaseAd();
            mNendAdRewardedVideo = null;
            Log.i(TAG, "Released.");
        }
    }

    public void onClickLoadInterstitial(View view) {
        Log.d(TAG, "Click load interstitial button.");

        if (null == mNendAdInterstitialVideo) {
            mNendAdInterstitialVideo = new NendAdInterstitialVideo(this, YOUR_SPOT_ID_INTERSTITIAL, YOUR_API_KEY_INTERSTITIAL);
            mNendAdInterstitialVideo.setUserId(USER_ID);
            mNendAdInterstitialVideo.addFallbackFullboard(485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
            mNendAdInterstitialVideo.setAdListener(new NendAdVideoListener() {
                @Override
                public void onLoaded(NendAdVideo nendAdVideo) {
                    mProgressDialog.dismiss();
                    Log.d(TAG, "onLoaded interstitial");
                    Toast.makeText(VideoActivity.this, "onLoaded interstitial", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailedToLoad(NendAdVideo nendAdVideo, int errorCode) {
                    mProgressDialog.dismiss();
                    Log.w(TAG, "onFailedToLoad interstitial");
                    Log.w(TAG, "VideoAdError code : " + errorCode);
                    Toast.makeText(VideoActivity.this, "onFailedToLoad interstitial, code : " + errorCode, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailedToPlay(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onFailedToPlay interstitial");
                    Toast.makeText(VideoActivity.this, "onFailedToPlay interstitial", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onShown(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onShown interstitial");
                    Toast.makeText(VideoActivity.this, "onShown interstitial", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClosed(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onClosed interstitial");
                    Toast.makeText(VideoActivity.this, "onClosed interstitial", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStarted(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onStarted interstitial");
                    Toast.makeText(VideoActivity.this, "onStarted interstitial", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStopped(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onStopped interstitial");
                    Toast.makeText(VideoActivity.this, "onStopped interstitial", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCompleted(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onCompleted interstitial");
                    Toast.makeText(VideoActivity.this, "onCompleted interstitial", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdClicked(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onAdClicked interstitial");
                    Toast.makeText(VideoActivity.this, "onAdClicked interstitial", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onInformationClicked(NendAdVideo nendAdVideo) {
                    Log.d(TAG, "onInformationClicked interstitial");
                    Toast.makeText(VideoActivity.this, "onInformationClicked interstitial", Toast.LENGTH_SHORT).show();
                }
            });
        }

        mNendAdInterstitialVideo.loadAd();
        createProgressDialog("Interstitial");
    }

    public void onClickShowInterstitial(View view) {
        Log.d(TAG, "Click show interstitial button.");

        if (null != mNendAdInterstitialVideo && mNendAdInterstitialVideo.isLoaded()) {
            mNendAdInterstitialVideo.showAd(this);
        } else {
            Log.d(TAG, "Loading is not completed.");
        }

    }

    public void onClickReleaseInterstitial(View view) {
        Log.d(TAG, "Click release interstitial button.");
        releaseInterstitialVideoInstance();
    }

    private void releaseInterstitialVideoInstance() {
        if (null != mNendAdInterstitialVideo) {
            mNendAdInterstitialVideo.releaseAd();
            mNendAdInterstitialVideo = null;
            Log.i(TAG, "Released.");
        }
    }

    private void createProgressDialog(String adType) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(adType);
        mProgressDialog.setMessage("Now loading...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseRewardVideoInstance();
        releaseInterstitialVideoInstance();
    }
}
