package net.nend.sample.java.nativeadvideo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.List;

public class ExamplesActivity extends ListActivity {

    static final int NATIVE_VIDEO_SPOT_ID = 887591;
    static final String NATIVE_VIDEO_API_KEY = "a284d892c3617bf5705facd3bfd8e9934a8b2491";

    private static final List<Class> EXAMPLE_ACTIVITIES = new ArrayList<Class>() {
        {
            add(SimpleActivity.class);
            add(InBannerActivity.class);
            add(ListViewInFeedActivity.class);
            add(GridViewInFeedActivity.class);
            add(RecyclerViewInFeedActivity.class);
            add(CarouselActivity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_video_examples);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        startActivity(new Intent(this, EXAMPLE_ACTIVITIES.get(position)).putExtra("type", position));
    }
}
