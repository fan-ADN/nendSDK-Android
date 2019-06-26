package net.nend.sample.java.nativeadvideo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeMediaView;
import net.nend.android.NendAdNativeMediaViewListener;
import net.nend.android.NendAdNativeVideo;
import net.nend.android.NendAdNativeVideoLoader;
import net.nend.android.NendAdNativeViewBinder;
import net.nend.sample.java.R;
import net.nend.sample.java.nativeadvideo.utilities.MyNendAdViewBinder;
import net.nend.sample.java.nativeadvideo.utilities.MyNendAdViewHolder;

public class SimpleActivity extends AppCompatActivity {

    private MyNendAdViewBinder videoBinder;
    private NendAdNativeViewBinder normalBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_video_simple);

        videoBinder = new MyNendAdViewBinder.Builder()
                .mediaViewId(R.id.native_video_ad_mediaview)
                .logoImageId(R.id.native_video_ad_logo_image)
                .titleId(R.id.native_video_ad_title)
                .contentId(R.id.native_video_ad_content)
                .advertiserId(R.id.native_video_ad_advertiser)
                .ratingId(R.id.native_video_ad_rating_stars)
                .ratingCountId(R.id.native_video_ad_rating_count)
                .actionId(R.id.native_video_ad_action)
                .build();
        normalBinder = new NendAdNativeViewBinder.Builder()
                .adImageId(R.id.native_video_ad_image)
                .logoImageId(R.id.native_video_ad_logo_image)
                .titleId(R.id.native_video_ad_title)
                .contentId(R.id.native_video_ad_content)
                .prId(R.id.native_video_ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                .promotionNameId(R.id.native_video_ad_advertiser)
                .actionId(R.id.native_video_ad_action)
                .build();
        final View itemView = findViewById(R.id.include_native_inflater_video);

        NendAdNativeVideoLoader videoLoader = new NendAdNativeVideoLoader(this,
                ExamplesActivity.NATIVE_VIDEO_SPOT_ID, ExamplesActivity.NATIVE_VIDEO_API_KEY);
        videoLoader.setFillerImageNativeAd(485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
        videoLoader.setLocationEnabled(false);

        videoLoader.loadAd(new NendAdNativeVideoLoader.Callback() {
            @Override
            public void onSuccess(NendAdNativeVideo ad) {
                if (ad.hasVideo()) {
                    MyNendAdViewHolder holder = new MyNendAdViewHolder(itemView, videoBinder);
                    videoBinder.renderView(holder, ad, new NendAdNativeMediaViewListener() {
                        @Override
                        public void onStartPlay(NendAdNativeMediaView nendAdNativeMediaView) {

                        }

                        @Override
                        public void onStopPlay(NendAdNativeMediaView nendAdNativeMediaView) {

                        }

                        @Override
                        public void onCompletePlay(NendAdNativeMediaView nendAdNativeMediaView) {

                        }

                        @Override
                        public void onOpenFullScreen(NendAdNativeMediaView nendAdNativeMediaView) {

                        }

                        @Override
                        public void onCloseFullScreen(NendAdNativeMediaView nendAdNativeMediaView) {

                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                } else {
                    findViewById(R.id.native_video_ad_mediaview).setVisibility(View.GONE);
                    findViewById(R.id.native_video_ad_rating_stars).setVisibility(View.GONE);
                    findViewById(R.id.native_video_ad_rating_count).setVisibility(View.GONE);
                    findViewById(R.id.native_video_ad_pr).setVisibility(View.VISIBLE);

                    NendAdNative adNormalNative = ad.getFallbackAd();
                    adNormalNative.intoView(itemView, normalBinder);
                }
            }

            @Override
            public void onFailure(int error) {

            }
        });
    }
}
