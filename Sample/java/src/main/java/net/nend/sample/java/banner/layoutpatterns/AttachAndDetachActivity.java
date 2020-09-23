package net.nend.sample.java.banner.layoutpatterns;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import net.nend.android.NendAdView;
import net.nend.sample.java.R;

/**
 * NendAdViewを削除し、再表示するサンプル
 */
public class AttachAndDetachActivity extends AppCompatActivity {
    
    private RelativeLayout mLayout;
    private NendAdView mNendAdView;
    private Handler mHandler;
    private boolean isAttached = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attach_dettach);

        mLayout = findViewById(R.id.root);
        mNendAdView = findViewById(R.id.nend);

        mHandler = new Handler(Looper.getMainLooper());
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