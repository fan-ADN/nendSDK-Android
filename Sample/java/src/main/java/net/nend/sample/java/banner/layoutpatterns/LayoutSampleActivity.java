package net.nend.sample.java.banner.layoutpatterns;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class LayoutSampleActivity extends SimpleListActivity {

    private static final List<String> SAMPLE_ACTIVITIES = new ArrayList<String>() {
        {
            add(TopActivity.class.getName());
            add(BottomActivity.class.getName());
            add(AttachAndDetachActivity.class.getName());
            add(ListViewActivity.class.getName());
            add(ViewPagerActivity.class.getName());
            add(FragmentReplaceActivity.class.getName());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.layout_sample, SAMPLE_ACTIVITIES);
    }
}