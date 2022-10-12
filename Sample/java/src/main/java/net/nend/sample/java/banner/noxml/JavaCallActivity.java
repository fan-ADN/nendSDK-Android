package net.nend.sample.java.banner.noxml;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class JavaCallActivity extends SimpleListActivity {

    private static final List<String> SAMPLE_ACTIVITIES = new ArrayList<String>() {
        {
            add(JavaCallLinearActivity.class.getName());
            add(JavaCallRelativeActivity.class.getName());
            add(JavaCallWithListenerActivity.class.getName());
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.javacall, SAMPLE_ACTIVITIES);
    }
}