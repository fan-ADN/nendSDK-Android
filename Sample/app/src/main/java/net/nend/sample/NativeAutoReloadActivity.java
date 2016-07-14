package net.nend.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;

/**
 * 自動リロード実装サンプル
 */
public class NativeAutoReloadActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private final int AUTO_RELOAD_INTERVAL = 30000;

    private NendAdNativeClient mClient;
    private NendAdNative mNendAdNative;
    private View mAdContainer;
    private TextView mPrTextView;
    private TextView mTitleTextView;
    private TextView mContentTextView;
    private ImageView mAdImageView;
    private boolean isEnableAutoReload = false;

    private ProgressBar mProgressBar;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_auto_reload);
        mAdContainer = findViewById(R.id.ad_container);
        mPrTextView = (TextView) findViewById(R.id.pr_text);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mContentTextView = (TextView) findViewById(R.id.content_text);
        mAdImageView = (ImageView) findViewById(R.id.ad_image);

        mClient = new NendAdNativeClient(this, 485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");

        // 広告オブジェクトの取得
        mClient.loadAd(new NendAdNativeClient.Callback() {
            @Override
            public void onSuccess(NendAdNative nendAdNative) {
                Log.i(TAG, "広告取得成功");
                mNendAdNative = nendAdNative;
                setAdTexts();
                getAndSetAdImage();
                activateAd();
                setClickListener();
                setAutoReload();
            }

            @Override
            public void onFailure(NendAdNativeClient.NendError nendError) {
                Log.i(TAG, "広告取得失敗:" + nendError.getMessage());
            }
        });

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    // 広告オブジェクトから文言を取得
    private void setAdTexts() {
        mTitleTextView.setText(mNendAdNative.getTitleText());
        mContentTextView.setText(mNendAdNative.getContentText());
        mPrTextView.setText(NendAdNative.AdvertisingExplicitly.AD.getText());
    }

    // 広告オブジェクトから画像を取得
    private void getAndSetAdImage() {
        mNendAdNative.downloadAdImage(new NendAdNative.Callback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                Log.i(TAG, "画像取得成功");
                mAdImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(TAG, "画像取得失敗:" + e.getMessage());
            }
        });
    }

    // 広告をアクティブにする
    private void activateAd() {
        mNendAdNative.activate(mAdContainer, mPrTextView);
    }

    // クリックリスナーの付与
    private void setClickListener() {
        mNendAdNative.setOnClickListener(new NendAdNative.OnClickListener() {
            @Override
            public void onClick(NendAdNative nendAdNative) {
                Log.i(TAG, "クリック");
            }
        });
    }

    // 自動リロードのサンプル
    private void setAutoReload() {
        isEnableAutoReload = true;
        progressCount();
        mClient.enableAutoReload(AUTO_RELOAD_INTERVAL, new NendAdNativeClient.Callback() {
            @Override
            public void onSuccess(NendAdNative nendAdNative) {
                Log.i(TAG, "広告取得成功（自動リロード）");
                mNendAdNative = nendAdNative;
                setAdTexts();
                getAndSetAdImage();
                activateAd();
                setClickListener();
            }

            @Override
            public void onFailure(NendAdNativeClient.NendError nendError) {
                Log.i(TAG, "広告取得失敗（自動リロード）");
                isEnableAutoReload = false;
                mClient.disableAutoReload();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isEnableAutoReload) {
            Log.i(TAG, "自動リロード停止");
            isEnableAutoReload = false;
            mClient.disableAutoReload();
            mCountDownTimer.cancel();
        }
    }

    private void progressCount() {
        mCountDownTimer = new CountDownTimer(AUTO_RELOAD_INTERVAL, 100) {
            public void onFinish() {
                start();
            }

            public void onTick(long amount) {
                mProgressBar.setProgress((int) (AUTO_RELOAD_INTERVAL - amount) / 100);
            }
        };
        mCountDownTimer.start();
    }
}
