package net.nend.sample.java.banner.xmllayout;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import net.nend.android.NendAdInformationListener;
import net.nend.android.NendAdView;
import net.nend.sample.java.R;

public class XmlWithListenerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_layout);

        NendAdView nendAdView = findViewById(R.id.nend);
        nendAdView.setListener(new NendAdInformationListener() {

            @Override
            public void onReceiveAd(@NonNull NendAdView adView) {
                Toast.makeText(getApplicationContext(), "広告取得成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailedToReceiveAd(@NonNull NendAdView adView) {
                Toast.makeText(getApplicationContext(), "広告取得失敗!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(@NonNull NendAdView adView) {
                Toast.makeText(getApplicationContext(), "クリック成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDismissScreen(@NonNull NendAdView adView) {
                Toast.makeText(getApplicationContext(), "復帰成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInformationButtonClick(@NonNull NendAdView adView) {
                Toast.makeText(getApplicationContext(), "Informationボタンクリック成功！", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
