package net.nend.sample.java.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import net.nend.android.NendAdInformationListener;
import net.nend.android.NendAdView;
import net.nend.sample.java.R;

public class XmlWithListenerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_layout);

        NendAdView nendAdView = (NendAdView) findViewById(R.id.nend);
        nendAdView.setListener(new NendAdInformationListener() {

            @Override
            public void onReceiveAd(NendAdView adView) {
                Toast.makeText(getApplicationContext(), "広告取得成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(NendAdView adView) {
                Toast.makeText(getApplicationContext(), "広告取得失敗!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(NendAdView adView) {
                Toast.makeText(getApplicationContext(), "クリック成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDismissScreen(NendAdView adView) {
                Toast.makeText(getApplicationContext(), "復帰成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInformationButtonClick(NendAdView adView) {
                Toast.makeText(getApplicationContext(), "Informationボタンクリック成功！", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
