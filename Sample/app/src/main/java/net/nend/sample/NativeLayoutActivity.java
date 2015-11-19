package net.nend.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeListener;
import net.nend.android.NendAdNativeViewBinder;

public class NativeLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int spotId;
        String apiKey;
        int layout;
        int type = 0;

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            type = bundle.getInt("type");
        }

        switch (type){
            default:
                layout = R.layout.native_small_square;
                spotId = 24443;
                apiKey = "4e418660631509c2466b9b719a267e376b5ddb66";
                break;
            case 1:
                layout = R.layout.native_small_wide;
                spotId = 24515;
                apiKey = "c89d1fd14081f8959e7c5a8db689e10be7bbe128";
                break;
            case 2:
                layout = R.layout.native_large_wide;
                spotId = 24516;
                apiKey = "89c4239f085dc934d6dcda8af1d6098706c06d38";
                break;
            case 3:
                layout = R.layout.native_text_only;
                spotId = 24517;
                apiKey = "8e80307ddb20f70dd78f4e0fd5714b60d3895201";
                break;
        }

        setContentView(layout);

        NendAdNativeViewBinder binder = new NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .logoImageId(R.id.logo_image)
                .titleId(R.id.ad_title)
                .contentId(R.id.ad_content)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.PR)
                .promotionUrlId(R.id.ad_promotion_url)
                .promotionNameId(R.id.ad_promotion_name)
                .actionId(R.id.ad_action)
                .build();

        RelativeLayout adLayout = (RelativeLayout)findViewById(R.id.ad);
        NendAdNativeClient client = new NendAdNativeClient(this, spotId, apiKey);
        client.loadAd(adLayout, binder);
        client.setListener(new NendAdNativeListener() {
            @Override
            public void onReceiveAd(NendAdNative ad, NendAdNativeClient.NendError nendError) {
                if (nendError == null) {
                    Log.i(getClass().getSimpleName(), "広告取得成功");
                } else {
                    Log.i(getClass().getSimpleName(), "広告取得失敗 " + nendError.getMessage());
                }
            }

            @Override
            public void onClick(NendAdNative ad) {
                Log.i(getClass().getSimpleName(), "クリック");
            }

            @Override
            public void onDisplayAd(Boolean result) {
                Log.i(getClass().getSimpleName(), "広告表示 = " + result);
            }
        });
    }
}