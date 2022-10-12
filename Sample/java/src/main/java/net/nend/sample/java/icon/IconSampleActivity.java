package net.nend.sample.java.icon;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class IconSampleActivity extends SimpleListActivity {

    private static final List<String> SAMPLE_ACTIVITIES = new ArrayList<String>() {
        {
            add(IconActivity.class.getName());
            add(IconResourceActivity.class.getName());
            add(IconJavaCallActivity.class.getName());
            add(IconAttachAndDetachActivity.class.getName());
            add(IconSpaceActivity.class.getName());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.icon_sample, SAMPLE_ACTIVITIES);
    }
}