package net.nend.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
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
        if (bundle != null) {
            type = bundle.getInt("type");
        }

        switch (type) {
            default:
                layout = R.layout.native_small_square;
                spotId = 485516;
                apiKey = "16cb170982088d81712e63087061378c71e8aa5c";
                break;
            case 1:
                layout = R.layout.native_large_wide;
                spotId = 485520;
                apiKey = "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff";
                break;
            case 2:
                layout = R.layout.native_text_only;
                spotId = 485522;
                apiKey = "2b2381a116290c90b936e409482127efb7123dbc";
                break;
        }

        setContentView(layout);

        final String TAG = getClass().getSimpleName();

        final NendAdNativeViewBinder binder = new NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .logoImageId(R.id.logo_image)
                .titleId(R.id.ad_title)
                .contentId(R.id.ad_content)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.PR)
                .promotionUrlId(R.id.ad_promotion_url)
                .promotionNameId(R.id.ad_promotion_name)
                .actionId(R.id.ad_action)
                .build();

        final RelativeLayout adLayout = (RelativeLayout) findViewById(R.id.ad);
        NendAdNativeClient client = new NendAdNativeClient(this, spotId, apiKey);

        client.loadAd(new NendAdNativeClient.Callback() {
            @Override
            public void onSuccess(final NendAdNative nendAdNative) {
                Log.i(TAG, "広告取得成功");
                nendAdNative.intoView(adLayout, binder);
                nendAdNative.setOnClickListener(new NendAdNative.OnClickListener() {
                    @Override
                    public void onClick(NendAdNative nendAdNative) {
                        Log.i(TAG, "クリック");
                    }
                });
            }

            @Override
            public void onFailure(NendAdNativeClient.NendError nendError) {
                Log.i(TAG, "広告取得失敗 " + nendError.getMessage());
            }
        });
    }
}