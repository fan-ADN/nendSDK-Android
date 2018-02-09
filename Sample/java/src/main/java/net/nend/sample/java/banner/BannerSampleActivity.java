package net.nend.sample.java.banner;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.List;

public class BannerSampleActivity extends ListActivity {

    private static final List<Class> SAMPLE_ACTIVITIES = new ArrayList<Class>() {
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
        setContentView(R.layout.activity_banner_sample);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        startActivity(new Intent(this, SAMPLE_ACTIVITIES.get(position)));
    }
}
