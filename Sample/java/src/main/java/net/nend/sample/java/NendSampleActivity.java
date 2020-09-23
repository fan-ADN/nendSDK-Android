package net.nend.sample.java;

import android.os.Bundle;

import net.nend.sample.java.banner.BannerSampleActivity;
import net.nend.sample.java.fullboard.FullBoardMenuActivity;
import net.nend.sample.java.icon.IconSampleActivity;
import net.nend.sample.java.interstitial.InterstitialActivity;
import net.nend.sample.java.nativead.NativeSampleActivity;
import net.nend.sample.java.nativeadvideo.ExamplesActivity;
import net.nend.sample.java.video.VideoActivity;

import java.util.ArrayList;
import java.util.List;

public class NendSampleActivity extends SimpleListActivity {

    private static final List<Class<?>> SAMPLE_ACTIVITIES = new ArrayList<Class<?>>() {
        {
            add(BannerSampleActivity.class);
            add(IconSampleActivity.class);
            add(InterstitialActivity.class);
            add(NativeSampleActivity.class);
            add(FullBoardMenuActivity.class);
            add(VideoActivity.class);
            add(ExamplesActivity.class);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.top_example_menus, SAMPLE_ACTIVITIES);
    }

}