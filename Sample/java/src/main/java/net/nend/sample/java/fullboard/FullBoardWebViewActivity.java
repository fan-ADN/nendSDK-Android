package net.nend.sample.java.fullboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import net.nend.android.NendAdFullBoard;
import net.nend.android.NendAdFullBoardLoader;
import net.nend.sample.java.R;

public class FullBoardWebViewActivity extends AppCompatActivity implements NendAdFullBoard.FullBoardAdListener {

    private NendAdFullBoardLoader mLoader;
    private NendAdFullBoard mAd;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_board_web);

        final ObservableWebView webView = findViewById(R.id.webview);
        webView.loadUrl("https://board.nend.net");
        webView.setCallback(new ObservableWebView.Callback() {
            @Override
            public void onScrollEnd() {
                if (mAd != null) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAd.show(FullBoardWebViewActivity.this);
                        }
                    }, 500L);
                }
            }
        });

        mLoader = new NendAdFullBoardLoader(getApplicationContext(), 485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
        loadAd();
    }

    private void loadAd() {
        mLoader.loadAd(new NendAdFullBoardLoader.Callback() {
            @Override
            public void onSuccess(NendAdFullBoard nendAdFullBoard) {
                mAd = nendAdFullBoard;
                mAd.setAdListener(FullBoardWebViewActivity.this);
            }

            @Override
            public void onFailure(NendAdFullBoardLoader.FullBoardAdError fullBoardAdError) {
                Log.w("FullBoard", fullBoardAdError.name());
            }
        });
    }

    @Override
    public void onDismissAd(NendAdFullBoard nendAdFullBoard) {
        loadAd();
    }

    @Override
    public void onShowAd(NendAdFullBoard nendAdFullBoard) {

    }

    @Override
    public void onClickAd(NendAdFullBoard nendAdFullBoard) {

    }
}
