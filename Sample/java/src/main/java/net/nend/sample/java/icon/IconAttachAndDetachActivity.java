package net.nend.sample.java.icon;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import net.nend.android.NendAdIconLoader;
import net.nend.android.NendAdIconView;
import net.nend.sample.java.R;
import net.nend.sample.java.SampleConstants;

import java.util.Random;

public class IconAttachAndDetachActivity extends AppCompatActivity implements SampleConstants {

	private NendAdIconLoader mIconLoader;
	private LinearLayout mIconContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.icon_attach_detach);
		
		mIconContainer = (LinearLayout)findViewById(R.id.icon_container);
		
        NendAdIconView view1 = new NendAdIconView(getApplicationContext());
        NendAdIconView view2 = new NendAdIconView(getApplicationContext());
        NendAdIconView view3 = new NendAdIconView(getApplicationContext());
        NendAdIconView view4 = new NendAdIconView(getApplicationContext());
        
        mIconLoader = new NendAdIconLoader(getApplicationContext(), ICON_SPOT_ID, ICON_API_KEY);
        mIconLoader.addIconView(view1);
        mIconLoader.addIconView(view2);
        mIconLoader.addIconView(view3);
        mIconLoader.addIconView(view4);
        
        mIconContainer.addView(view1);
        mIconContainer.addView(view2);
        mIconContainer.addView(view3);
        mIconContainer.addView(view4);
        
        mIconLoader.loadAd();
	}
	
	public void onClickAdd(View v) {
		if ( 4 <= mIconLoader.getIconCount() ) {
			return;
		}
		NendAdIconView view = new NendAdIconView(getApplicationContext());
		mIconLoader.addIconView(view);
		mIconContainer.addView(view);
	}
	
	public void onClickRemove(View v) {
		if ( 0 == mIconLoader.getIconCount() ) {
			return;
		}
		Random random = new Random();
		int index = random.nextInt(mIconLoader.getIconCount());
		NendAdIconView view = (NendAdIconView)mIconContainer.getChildAt(index);
		mIconLoader.removeIconView(view);
		mIconContainer.removeViewAt(index);
	}
}
