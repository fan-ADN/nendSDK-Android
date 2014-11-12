package net.nend.sample;

import net.nend.android.NendAdIconLoader;
import net.nend.android.NendAdIconLoader.OnClickListner;
import net.nend.android.NendAdIconLoader.OnFailedListner;
import net.nend.android.NendAdIconLoader.OnReceiveListner;
import net.nend.android.NendAdIconView;
import net.nend.android.NendAdView.NendError;
import net.nend.android.NendIconError;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class IconActivity extends Activity implements SampleConstants {

    private NendAdIconLoader mIconLoader;
    private NendAdIconView view1;
    private NendAdIconView view2;
    private NendAdIconView view3;
    private NendAdIconView view4;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon);
        
        view1 = (NendAdIconView) findViewById(R.id.icon1);
        view2 = (NendAdIconView) findViewById(R.id.icon2);
        view3 = (NendAdIconView) findViewById(R.id.icon3);
        view4 = (NendAdIconView) findViewById(R.id.icon4);
        
        mIconLoader = new NendAdIconLoader(getApplicationContext(), ICON_SPOT_ID, ICON_API_KEY);
        mIconLoader.addIconView(view1);
        mIconLoader.addIconView(view2);
        mIconLoader.addIconView(view3);
        mIconLoader.addIconView(view4);
        mIconLoader.loadAd();
        
        mIconLoader.setOnReceiveLisner(new OnReceiveListner() {
            @Override
            public void onReceiveAd(NendAdIconView iconView) {
                switch (iconView.getId()) {
                case R.id.icon1:
                    break;
                case R.id.icon2:
                    break;
                case R.id.icon3:
                    break;
                case R.id.icon4:
                    break;
                }
                Toast.makeText(getApplicationContext(), "Recieved", Toast.LENGTH_SHORT).show();
            }
        });
        mIconLoader.setOnClickListner(new OnClickListner() {
            @Override
            public void onClick(NendAdIconView iconView) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        mIconLoader.setOnFailedListner(new OnFailedListner() {
            @Override
            public void onFailedToReceiveAd(NendIconError iconError) {
                NendError nendError = iconError.getNendError();
                switch (nendError) {
                case INVALID_RESPONSE_TYPE:
                    nendError.getMessage();
                    break;

                default:
                    break;
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
