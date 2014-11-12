package net.nend.sample;

import net.nend.android.NendAdView;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

/**
 * NendAdViewを削除し、再表示するサンプル
 */
public class AttachAndDettachActivity extends Activity {
    
    private RelativeLayout mLayout;
    private NendAdView mNendAdView;
    private Handler mHandler;
    private boolean isAttached = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attach_dettach);

        mLayout = (RelativeLayout)findViewById(R.id.root);
        mNendAdView = (NendAdView)findViewById(R.id.nend);

        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 繰り返し処理を開始
        doPost();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 繰り返し処理を停止
        mHandler.removeCallbacks(runnable);
    }

    private void doPost(){
        // 5秒ごとに削除と再表示を繰り返す
        mHandler.postDelayed(runnable, 5000);
    }
    
    /**
     * NendAdViewの削除、再表示処理
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(isAttached){
                mLayout.removeView(mNendAdView);
                isAttached = false;
            }else{
                mLayout.addView(mNendAdView);
                isAttached = true;
            }
            doPost();
        }
    };
}