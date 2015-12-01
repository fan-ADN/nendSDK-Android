package net.nend.sample;

import net.nend.android.NendAdView;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class JavaCallRelativeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javacall_relative);

        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.root);

        /*
         *
         * NendAdView(Context context, int [Spotid], String [Apikey])
         *
         */
        NendAdView nendAdView = 
                new NendAdView(getApplicationContext(), 3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f");
        // 中央下部表示の場合
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        // 上部表示の場合
        //params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        rootLayout.addView(nendAdView, params);

        nendAdView.loadAd();
    }
}
