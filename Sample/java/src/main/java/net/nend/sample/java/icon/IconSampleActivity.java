package net.nend.sample.java.icon;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class IconSampleActivity extends SimpleListActivity {

    private static final List<Class<?>> SAMPLE_ACTIVITIES = new ArrayList<Class<?>>() {
        {
            add(IconActivity.class);
            add(IconResourceActivity.class);
            add(IconJavaCallActivity.class);
            add(IconAttachAndDetachActivity.class);
            add(IconSpaceActivity.class);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.icon_sample, SAMPLE_ACTIVITIES);
    }
}