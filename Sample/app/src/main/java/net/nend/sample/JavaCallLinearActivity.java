package net.nend.sample;

import net.nend.android.NendAdView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class JavaCallLinearActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javacall_linear);

        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.root);

        /*
         *
         * NendAdView(Context context, int [Spotid], String [Apikey])
         *
         */
        NendAdView nendAdView = new NendAdView(getApplicationContext(), 
                3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f");
        // 中央下部表示の場合
        rootLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        // 上部表示の場合
        //rootLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        rootLayout.addView(nendAdView, 
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));

        nendAdView.loadAd();
    }
}
