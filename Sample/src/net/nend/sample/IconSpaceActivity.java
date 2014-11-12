package net.nend.sample;

import net.nend.android.NendAdIconLoader;
import net.nend.android.NendAdIconView;
import android.app.Activity;
import android.os.Bundle;

public class IconSpaceActivity extends Activity {
	
	NendAdIconLoader mIconLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon_space);
        
        mIconLoader = new NendAdIconLoader(getApplicationContext(), 101282, "0c734134519f25412ae9a9bff94783b81048ffbe");
        for ( int i = 1; i <= 4; i++ ) {
        	int id = getResources().getIdentifier("icon" + i, "id", getPackageName());
        	mIconLoader.addIconView((NendAdIconView)findViewById(id));
        }
        mIconLoader.loadAd();
    }

	@Override
	protected void onPause() {
		super.onPause();
		mIconLoader.pause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		mIconLoader.resume();
	}    
}
