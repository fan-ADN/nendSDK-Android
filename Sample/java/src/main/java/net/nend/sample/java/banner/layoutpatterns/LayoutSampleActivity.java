package net.nend.sample.java.banner.layoutpatterns;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class LayoutSampleActivity extends SimpleListActivity {

    private static final List<Class<?>> SAMPLE_ACTIVITIES = new ArrayList<Class<?>>() {
        {
            add(TopActivity.class);
            add(BottomActivity.class);
            add(AttachAndDetachActivity.class);
            add(ListViewActivity.class);
            add(ViewPagerActivity.class);
            add(PagerAndListActivity.class);
            add(FragmentReplaceActivity.class);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.layout_sample, SAMPLE_ACTIVITIES);
    }
}