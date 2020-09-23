package net.nend.sample.java.icon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import net.nend.android.NendAdIconLoader;
import net.nend.android.NendAdIconView;
import net.nend.android.NendAdView.NendError;
import net.nend.android.NendIconError;
import net.nend.sample.java.R;
import net.nend.sample.java.SampleConstants;

public class IconActivity extends AppCompatActivity implements SampleConstants {

    private NendAdIconLoader mIconLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon);

        NendAdIconView view1 = findViewById(R.id.icon1);
        NendAdIconView view2 = findViewById(R.id.icon2);
        NendAdIconView view3 = findViewById(R.id.icon3);
        NendAdIconView view4 = findViewById(R.id.icon4);
        
        mIconLoader = new NendAdIconLoader(getApplicationContext(), ICON_SPOT_ID, ICON_API_KEY);
        mIconLoader.addIconView(view1);
        mIconLoader.addIconView(view2);
        mIconLoader.addIconView(view3);
        mIconLoader.addIconView(view4);
        mIconLoader.loadAd();
                
        mIconLoader.setOnReceiveListener(new NendAdIconLoader.OnReceiveListener() {
            @Override
            public void onReceiveAd(NendAdIconView iconView) {
                int id = 0;
                switch (iconView.getId()) {
                case R.id.icon1:
                    id = 1;
                    break;
                case R.id.icon2:
                    id = 2;
                    break;
                case R.id.icon3:
                    id = 3;
                    break;
                case R.id.icon4:
                    id = 4;
                    break;
                }
                Toast.makeText(getApplicationContext(), "Recieved: " + id, Toast.LENGTH_SHORT).show();
            }
        });
        mIconLoader.setOnClickListener(new NendAdIconLoader.OnClickListener() {
            @Override
            public void onClick(NendAdIconView iconView) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mIconLoader.setOnInformationClickListener(new NendAdIconLoader.OnInformationClickListener() {
            @Override
            public void onClickInformation(NendAdIconView iconView) {
                Toast.makeText(getApplicationContext(), "Clicked Information", Toast.LENGTH_SHORT).show();
            }
        });
        mIconLoader.setOnFailedListener(new NendAdIconLoader.OnFailedListener() {
            @Override
            public void onFailedToReceiveAd(NendIconError iconError) {
                NendError nendError = iconError.getNendError();
                if (nendError == NendError.INVALID_RESPONSE_TYPE) {
                    Log.e("IconActivity", nendError.getMessage());
                }
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mIconLoader.resume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mIconLoader.pause();
    }
    
    public void onClick(View view){
        switch (view.getId()) {
        case R.id.resume:
            mIconLoader.resume();
            break;
        case R.id.pause:
            mIconLoader.pause();
            break;
        }
    }
}
