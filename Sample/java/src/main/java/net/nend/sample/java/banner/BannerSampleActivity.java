package net.nend.sample.java.banner;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;
import net.nend.sample.java.banner.adjust.AdjustSizeActivity;
import net.nend.sample.java.banner.layoutpatterns.DialogActivity;
import net.nend.sample.java.banner.layoutpatterns.LayoutSampleActivity;
import net.nend.sample.java.banner.noxml.JavaCallActivity;
import net.nend.sample.java.banner.sizes.SizeSampleActivity;
import net.nend.sample.java.banner.xmllayout.XmlSampleActivity;

import java.util.ArrayList;
import java.util.List;

public class BannerSampleActivity extends SimpleListActivity {

    private static final List<Class<?>> SAMPLE_ACTIVITIES = new ArrayList<Class<?>>() {
        {
            add(XmlSampleActivity.class);
            add(JavaCallActivity.class);
            add(LayoutSampleActivity.class);
            add(DialogActivity.class);
            add(SizeSampleActivity.class);
            add(AdjustSizeActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.activity_banner_sample, SAMPLE_ACTIVITIES);
    }
}
