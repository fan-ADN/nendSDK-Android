package net.nend.sample.java.banner.xmllayout;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class XmlSampleActivity extends SimpleListActivity {

    private static final List<Class<?>> SAMPLE_ACTIVITIES = new ArrayList<Class<?>>() {
        {
            add(XmlLayoutActivity.class);
            add(XmlResourceActivity.class);
            add(XmlWithListenerActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.xml_sample, SAMPLE_ACTIVITIES);
    }
}
