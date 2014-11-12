package net.nend.sample;

import net.nend.android.NendAdInterstitial;
import android.app.Activity;
import android.os.Bundle;

public class FinishAdActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_ad);
        
        NendAdInterstitial.loadAd(getApplicationContext(), "8c278673ac6f676dae60a1f56d16dad122e23516", 213206);
    }
    
    @Override
    public void onBackPressed() {
        // バックキーが押された時に広告を表示
        NendAdInterstitial.showFinishAd(this);
    }
}
