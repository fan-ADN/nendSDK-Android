package net.nend.sample.java.banner.noxml;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class JavaCallActivity extends SimpleListActivity {

    private static final List<Class<?>> SAMPLE_ACTIVITIES = new ArrayList<Class<?>>() {
        {
            add(JavaCallLinearActivity.class);
            add(JavaCallRelativeActivity.class);
            add(JavaCallWithListenerActivity.class);
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.javacall, SAMPLE_ACTIVITIES);
    }
}