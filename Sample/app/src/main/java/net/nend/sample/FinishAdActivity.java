package net.nend.sample;

import net.nend.android.NendAdInterstitial;
import net.nend.android.NendAdInterstitial.NendAdInterstitialClickType;
import net.nend.android.NendAdInterstitial.NendAdInterstitialStatusCode;
import net.nend.android.NendAdInterstitial.OnClickListenerSpot;
import net.nend.android.NendAdInterstitial.OnCompletionListenerSpot;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class FinishAdActivity extends Activity implements OnCompletionListenerSpot, OnClickListenerSpot {
    
	private static final String TAG = FinishAdActivity.class.getSimpleName();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit_ad);
        
        NendAdInterstitial.setListener(this);
        NendAdInterstitial.loadAd(getApplicationContext(), "8c278673ac6f676dae60a1f56d16dad122e23516", 213206);
    }
    
    @Override
    public void onBackPressed() {
        // バックキーが押された時に広告を表示
        NendAdInterstitial.showFinishAd(this, this);
    }

	@Override
	public void onClick(NendAdInterstitialClickType clickType) {
		// not called.
	}

	@Override
	public void onCompletion(NendAdInterstitialStatusCode statusCode) {
		// not called.
	}

	@Override
	public void onClick(NendAdInterstitialClickType clickType, int spotId) {
		Log.d(TAG, clickType.name() + ", " + spotId);		
	}

	@Override
	public void onCompletion(NendAdInterstitialStatusCode statusCode, int spotId) {
		Log.d(TAG, statusCode.name() + ", " + spotId);
	}
}
