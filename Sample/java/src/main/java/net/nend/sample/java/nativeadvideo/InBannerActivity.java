package net.nend.sample.java.nativeadvideo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.nend.android.NendAdNativeMediaView;
import net.nend.android.NendAdNativeVideo;
import net.nend.android.NendAdNativeVideoLoader;
import net.nend.sample.java.R;

public class InBannerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_video_in_banner);

        loadAd();
    }

    private void loadAd() {
        NendAdNativeVideoLoader loader = new NendAdNativeVideoLoader(
                this,
                ExamplesActivity.NATIVE_VIDEO_SPOT_ID,
                ExamplesActivity.NATIVE_VIDEO_API_KEY
        );
        loader.loadAd(new NendAdNativeVideoLoader.Callback() {
            @Override
            public void onSuccess(NendAdNativeVideo ad) {
                FrameLayout item = findViewById(R.id.movie_area);

                NendAdNativeMediaView mediaView = new NendAdNativeMediaView(InBannerActivity.this);
                mediaView.setMedia(ad);
                item.addView(mediaView);

                TextView title = findViewById(R.id.title_native_in_banner);
                title.setText(ad.getTitleText());
                TextView description = findViewById(R.id.description_native_in_banner);
                description.setText(ad.getDescriptionText());
            }

            @Override
            public void onFailure(int errorCode) {

            }
        });
    }
}
