package net.nend.sample;

import net.nend.android.NendAdInterstitial;
import net.nend.android.NendAdInterstitial.NendAdInterstitialClickType;
import net.nend.android.NendAdInterstitial.NendAdInterstitialShowResult;
import net.nend.android.NendAdInterstitial.NendAdInterstitialStatusCode;
import net.nend.android.NendAdInterstitial.OnCompletionListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InterstitialActivity extends Activity 
        implements NendAdInterstitial.OnClickListener, OnCompletionListener{
    
    private String TAG = InterstitialActivity.this.getClass().getSimpleName();
    private Handler mHandler = new Handler();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interstitial_ad);
        
        NendAdInterstitial.loadAd(getApplicationContext(), "8c278673ac6f676dae60a1f56d16dad122e23516", 213206);
        // 必要に応じて広告取得結果通知を受けとる
        NendAdInterstitial.setListener(this);
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                
                // 表示結果が返却される
                NendAdInterstitialShowResult result = NendAdInterstitial.showAd(InterstitialActivity.this);
                
                // 表示結果に応じて処理を行う
                switch (result) {
                case AD_SHOW_SUCCESS:
                    break;
                case AD_SHOW_ALREADY:
                    break;
                case AD_FREQUENCY_NOT_RECHABLE:
                    break;
                case AD_REQUEST_INCOMPLETE:
                    break;
                case AD_LOAD_INCOMPLETE:
                    break;
                case AD_DOWNLOAD_INCOMPLETE:
                    break;
                default:
                    break;
                }
                // 広告表示結果をログに出力
                Log.d(TAG, result.name());
                
                // ５秒後に広告を閉じる
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NendAdInterstitial.dismissAd();
                    }
                }, 5000);
            }
        });
    }
    
    /**
     * インタースティシャル広告クリック通知
     */
    @Override
    public void onClick(NendAdInterstitialClickType clickType) {
        // クリックに応じて処理行う
        switch (clickType) {
        case CLOSE:
            break;
        case DOWNLOAD:
            break;
        default :
            break;
        }
        // 広告クリックをログに出力
        Log.d(TAG, clickType.name());
    }
    
    /**
     * 広告受信通知
     */
    @Override
    public void onCompletion(NendAdInterstitialStatusCode statusCode) {
        // 受信結果に応じて処理を行う
        switch (statusCode) {
        case SUCCESS:
            break;
        case FAILED_AD_DOWNLOAD:
            break;
        case INVALID_RESPONSE_TYPE:
            break;
        case FAILED_AD_INCOMPLETE:
            break;
        case FAILED_AD_REQUEST:
            break;
        default:
            break;
        }
        // 広告受信結果をログに出力
        Log.d(TAG, statusCode.name());
    }
}
