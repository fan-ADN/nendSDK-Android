package net.nend.sample;

import net.nend.android.NendAdView;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class AdjustSizeJavaCallActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javacall_relative);
        
        ViewGroup root = (ViewGroup) findViewById(R.id.root);
        NendAdView adView = new NendAdView(this, 3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f", true);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        root.addView(adView, lp);
        adView.loadAd();
    }
}
