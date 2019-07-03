package net.nend.sample.java.banner;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import net.nend.android.NendAdView;
import net.nend.sample.java.R;

public class JavaCallRelativeActivity extends AppCompatActivity {

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
                new NendAdView(this, 3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f");
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
