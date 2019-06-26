package net.nend.sample.java.video;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.nend.android.NendAdInterstitialVideo;
import net.nend.android.NendAdRewardItem;
import net.nend.android.NendAdRewardedListener;
import net.nend.android.NendAdRewardedVideo;
import net.nend.android.NendAdUserFeature;
import net.nend.android.NendAdVideo;
import net.nend.android.NendAdVideoListener;
import net.nend.sample.java.R;


/*
　このサンプルは広告配信に位置情報をオプションで利用しています。
  This sample uses location data as an option for ad supply.
*/
public class VideoActivity extends AppCompatActivity {

    private static final String TAG = "NEND_VIDEO";
    private static final int REWARDED_VIDEO_SPOT_ID = 802558;
    private static final int INTERSTITIAL_VIDEO_SPOT_ID = 802559;
    private static final String REWARDED_VIDEO_API_KEY = "a6eb8828d64c70630fd6737bd266756c5c7d48aa";
    private static final String INTERSTITIAL_VIDEO_API_KEY = "e9527a2ac8d1f39a667dfe0f7c169513b090ad44";
    private static final String USER_ID = "DUMMY_USER_ID";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private ProgressDialog mProgressDialog;
    private NendAdRewardedVideo mNendAdRewardedVideo;
    private NendAdInterstitialVideo mNendAdInterstitialVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!verifyPermissions()) {
            requestPermissions();
        }
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

    public void onClickLoadReward(View view) {
        Log.d(TAG, "Click load reward button.");

        if (null == mNendAdRewardedVideo) {
            mNendAdRewardedVideo = new NendAdRewardedVideo(this, REWARDED_VIDEO_SPOT_ID, REWARDED_VIDEO_API_KEY);
            mNendAdRewardedVideo.setUserId(USER_ID);
            NendAdUserFeature feature = new NendAdUserFeature.Builder()
                    .setGender(NendAdUserFeature.Gender.FEMALE)
                    .build();
            mNendAdRewardedVideo.setUserFeature(feature);
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
            mNendAdInterstitialVideo = new NendAdInterstitialVideo(this, INTERSTITIAL_VIDEO_SPOT_ID, INTERSTITIAL_VIDEO_API_KEY);
            mNendAdInterstitialVideo.setUserId(USER_ID);
            NendAdUserFeature feature = new NendAdUserFeature.Builder()
                    .setGender(NendAdUserFeature.Gender.MALE)
                    .setBirthday(1985, 1, 1)
                    .setAge(34)
                    .addCustomFeature("stringParameter", "test")
                    .addCustomFeature("booleanParameter", true)
                    .addCustomFeature("integerParameter", 100)
                    .addCustomFeature("doubleParameter", 123.45)
                    .build();
            mNendAdInterstitialVideo.setUserFeature(feature);
            mNendAdInterstitialVideo.setLocationEnabled(false);
            mNendAdInterstitialVideo.addFallbackFullboard(485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
            mNendAdInterstitialVideo.setMuteStartPlaying(false);
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

    private boolean verifyPermissions() {
        int state = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return state == PackageManager.PERMISSION_GRANTED;
    }

    private void showRequestPermissionDialog() {
        ActivityCompat.requestPermissions(VideoActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldRequest) {
            Snackbar.make(findViewById(R.id.base_layout), "Location permission is needed for get the last Location. It's a demo that uses location data.", Snackbar.LENGTH_LONG).setAction(android.R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRequestPermissionDialog();
                }
            }).show();
        } else {
            showRequestPermissionDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Snackbar.make(findViewById(R.id.base_layout), "User interaction was cancelled.", Snackbar.LENGTH_LONG).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(findViewById(R.id.base_layout), "Permission granted.", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(findViewById(R.id.base_layout), "Permission denied.", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
