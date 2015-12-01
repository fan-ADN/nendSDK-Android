package net.nend.sample;

import net.nend.android.NendAdIconLoader;
import net.nend.android.NendAdIconView;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class IconJavaCallActivity extends Activity implements SampleConstants {
    
    private NendAdIconLoader mIconLoader;
    private NendAdIconView view1;
    private NendAdIconView view2;
    private NendAdIconView view3;
    private NendAdIconView view4;
    
    private int WC = LayoutParams.WRAP_CONTENT;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javacall_relative);
        
        Context context = getApplicationContext();
        
        view1 = new NendAdIconView(context);
        view2 = new NendAdIconView(context);
        view3 = new NendAdIconView(context);
        view4 = new NendAdIconView(context);
        
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.root);
        mIconLoader = new NendAdIconLoader(context, ICON_SPOT_ID, ICON_API_KEY);
        mIconLoader.addIconView(view1);
        mIconLoader.addIconView(view2);
        mIconLoader.addIconView(view3);
        mIconLoader.addIconView(view4);
        
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(WC, WC);
        params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(WC, WC);
        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(WC, WC);
        params3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(WC, WC);
        params4.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        
        rootLayout.addView(view1, params1);
        rootLayout.addView(view2, params2);
        rootLayout.addView(view3, params3);
        rootLayout.addView(view4, params4);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mIconLoader.loadAd();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mIconLoader.pause();
    }
}