package net.nend.sample.java.nativead;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.List;

public class NativeSampleActivity extends ListActivity {

    private static final List<Class> SAMPLE_ACTIVITIES = new ArrayList<Class>() {
        {
            add(NativeLayoutActivity.class);
            add(NativeLayoutActivity.class);
            add(NativeLayoutActivity.class);
            add(NativeListActivity.class);
            add(NativeMultipleLayoutListActivity.class);
            add(NativeGridActivity.class);
            add(NativeRecyclerActivity.class);
            add(NativeViewPagerActivity.class);
            add(NativeCarouselAdActivity.class);
            add(NativeCarouselAdActivity.class);
            add(NativeMarqueeActivity.class);
            add(NativeGetAdDataActivity.class);
            add(NativeAutoReloadActivity.class);
            add(NativeV2OnListActivity.class);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_sample);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        startActivity(new Intent(this, SAMPLE_ACTIVITIES.get(position)).putExtra("type", position));
    }
}