package net.nend.sample.java.nativead;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class NativeSampleActivity extends SimpleListActivity {

    private static final List<Class<?>> SAMPLE_ACTIVITIES = new ArrayList<Class<?>>() {
        {
            add(NativeLayoutActivity.class);
            add(NativeLayoutActivity.class);
            add(NativeLayoutActivity.class);
            add(NativeMultipleLayoutListActivity.class);
            add(NativeV2OnListActivity.class);
            add(NativeGridActivity.class);
            add(NativeRecyclerActivity.class);
            add(NativeViewPagerActivity.class);
            add(NativeCarouselAdActivity.class);
            add(NativeMarqueeActivity.class);
            add(NativeAdV2Activity.class);
            add(NativeAutoReloadActivity.class);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.native_sample, SAMPLE_ACTIVITIES);
    }
}