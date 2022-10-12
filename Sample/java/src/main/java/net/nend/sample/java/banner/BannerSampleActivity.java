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

    private static final List<String> SAMPLE_ACTIVITIES = new ArrayList<String>() {
        {
            add(XmlSampleActivity.class.getName());
            add(JavaCallActivity.class.getName());
            add(LayoutSampleActivity.class.getName());
            add(DialogActivity.class.getName());
            add(SizeSampleActivity.class.getName());
            add(AdjustSizeActivity.class.getName());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.activity_banner_sample, SAMPLE_ACTIVITIES);
    }
}
