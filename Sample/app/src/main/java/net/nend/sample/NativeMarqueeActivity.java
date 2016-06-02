package net.nend.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeListener;
import net.nend.android.NendAdNativeTextView;
import net.nend.android.NendAdNativeViewBinder;

/**
 * Sample for like telop
 */
public class NativeMarqueeActivity extends AppCompatActivity {

    private String TAG = "MARQUEE_VIEW";
    private MarqueeView mMarqueeView;
    private NendAdNativeTextView mNendTextView;

    private boolean mThreadStopper = false; // for leaving the loop
    private int mScrollViewWidth;           // MarqueeView width
    private int mWidthWithBlank;            // NendNativeTextView width

    private Thread mThread = null;          // runnable thread
    private int mRepeatCount;               // number of repeat
    private int mCurrentX;                  // current text position
    private boolean mIsEndlessLoop;         // endless flag

    /**
     * Runnable variable for marquee
     */
    private Runnable runnable = new Runnable() {
        public void run() {
            // end point
            int endX = getEndX();

            int repeatLimit = 2;
            int textMoveSpeed = 10;

            while (mRepeatCount < repeatLimit) {
                if (!mThreadStopper && !Thread.interrupted()) {

                    mCurrentX = getMarqueeStartX();

                    long beforeTime = System.currentTimeMillis();
                    long afterTime;
                    int fps = 30;
                    long frameTime = 1000 / fps;

                    while (true) {

                        if (!mThreadStopper && !Thread.interrupted()) {

                            if (mCurrentX >= endX) {
                                mRepeatCount++;
                                if (mIsEndlessLoop) {
                                    mRepeatCount--;
                                }
                                break;
                            }

                            mCurrentX += textMoveSpeed;

                            // UI control must on UI thread
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mMarqueeView.smoothScrollTo(mCurrentX, 0);
                                }
                            });

                            // speed control
                            afterTime = System.currentTimeMillis();
                            long pastTime = afterTime - beforeTime;
                            long sleepTime = frameTime - pastTime;
                            if (sleepTime > 0) {
                                try {
                                    Thread.sleep(sleepTime);
                                } catch (InterruptedException e) {
                                    Log.d(TAG, "thread interruption");
                                }
                            }
                            beforeTime = System.currentTimeMillis();
                        } else if (mThreadStopper) {
                            break;
                        }
                    }
                } else if (mThreadStopper) {
                    break;
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_marquee);

        mMarqueeView = (MarqueeView) findViewById(R.id.horizontalScrollView);
        mNendTextView = (NendAdNativeTextView) findViewById(R.id.ad_content);

        final int SPOT_ID = 485516;
        final String API_KEY = "16cb170982088d81712e63087061378c71e8aa5c";

        NendAdNativeViewBinder mBinder = new NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .titleId(R.id.ad_title)
                .contentId(R.id.ad_content)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.AD)
                .build();

        // small square size
        NendAdNativeClient mClient = new NendAdNativeClient(getApplicationContext(), SPOT_ID, API_KEY);
        mClient.setListener(new NendAdNativeListener() {
            @Override
            public void onReceiveAd(NendAdNative ad, NendAdNativeClient.NendError nendError) {
                if (nendError == null) {
                    Log.i(TAG, "広告取得成功");
                    setPortraitPR();
                    setBlank();
                } else {
                    Log.i(TAG, "広告取得失敗 " + nendError.getMessage());
                }
            }

            @Override
            public void onClick(NendAdNative ad) {
                Log.i(TAG, "クリック");
            }

            @Override
            public void onDisplayAd(Boolean result) {
                Log.i(TAG, "広告表示 = " + result);
                startMarquee();
            }
        });
        mClient.loadAd(findViewById(R.id.ad), mBinder);
        setEndless(true);
    }

    /**
     * Get start coordinate
     */
    private int getMarqueeStartX() {
        return 0;
    }

    /**
     * Get end coordinate
     */
    private int getEndX() {
        return mWidthWithBlank;
    }

    /**
     * Set blank to title text
     */
    private void setBlank() {

        // get future title text size
        int nendTextWidth = (int) Layout.getDesiredWidth(mNendTextView.getText(), mNendTextView.getPaint());
        mWidthWithBlank = nendTextWidth + (mScrollViewWidth * 2);
        // first, set text view size
        mNendTextView.setWidth(mWidthWithBlank);

        // second, set padding
        mNendTextView.setPadding(mScrollViewWidth, 0, mScrollViewWidth, 0);
    }

    /**
     * Set portrait to PR text
     */
    private void setPortraitPR() {
        NendAdNativeTextView pr = (NendAdNativeTextView) findViewById(R.id.ad_pr);
        assert pr != null;
        float scale = getResources().getDisplayMetrics().density;
        int paddingDp = (int) (10 * scale + 0.5f);
        // set future pr size plus 10dp
        int prWidth = (int) Layout.getDesiredWidth(pr.getText(), pr.getPaint()) + paddingDp;
        pr.setWidth(prWidth / 2);
    }

    private void setEndless(boolean isEndless) {
        mIsEndlessLoop = isEndless;
    }

    /**
     * clear marquee
     */
    public void clearMarquee() {
        mCurrentX = getMarqueeStartX();
        mRepeatCount = 0;
        mThread = null;
    }

    /**
     * start marquee
     */
    public void startMarquee() {
        clearMarquee();
        mThread = new Thread(runnable);
        mThread.setName("marquee");
        mThread.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        // true to leaving the loop
        mThreadStopper = true;
        if (null != mThread && mThread.getName().equals("marquee") && !mThread.isInterrupted()) {
            mThread.interrupt();
        }
        clearMarquee();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mThreadStopper) {
            mThreadStopper = false;
            startMarquee();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mScrollViewWidth = mMarqueeView.getWidth();
        } else {
            mThreadStopper = true;
            if (null != mThread && !mThread.isInterrupted()) {
                mThread.interrupt();
            }
            clearMarquee();
        }
    }

}
