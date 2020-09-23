package net.nend.sample.java.banner.noxml;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import net.nend.android.NendAdView;
import net.nend.sample.java.R;

public class JavaCallLinearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.javacall_linear);

        LinearLayout rootLayout = findViewById(R.id.root);

        /*
         *
         * NendAdView(Context context, int [Spotid], String [Apikey])
         *
         */
        NendAdView nendAdView = new NendAdView(this,
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
