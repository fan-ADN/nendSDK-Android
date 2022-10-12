package net.nend.sample.java.nativeadvideo;

import android.os.Bundle;

import net.nend.sample.java.R;
import net.nend.sample.java.SimpleListActivity;

import java.util.ArrayList;
import java.util.List;

public class ExamplesActivity extends SimpleListActivity {

    static final int NATIVE_VIDEO_SPOT_ID = 887591;
    static final String NATIVE_VIDEO_API_KEY = "a284d892c3617bf5705facd3bfd8e9934a8b2491";

    private static final List<String> EXAMPLE_ACTIVITIES = new ArrayList<String>() {
        {
            add(SimpleActivity.class.getName());
            add(InBannerActivity.class.getName());
            add(ListViewInFeedActivity.class.getName());
            add(GridViewInFeedActivity.class.getName());
            add(RecyclerViewInFeedActivity.class.getName());
            add(CarouselActivity.class.getName());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateMenuListFragment(R.layout.activity_native_video_examples, EXAMPLE_ACTIVITIES);
    }
}
