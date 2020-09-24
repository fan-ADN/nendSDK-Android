package net.nend.sample.java.nativead;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NativeSampleActivity extends AppCompatActivity {

    private static final List<Class<?>> SAMPLE_ACTIVITIES = new ArrayList<Class<?>>() {
        {
            add(NativeLayoutActivity.class);
            add(NativeLayoutActivity.class);
            add(NativeLayoutActivity.class);
            add(NativeMultipleLayoutListActivity.class);
            add(NativeV2OnListActivity.class);
            add(NativeGridActivity.class);
            add(NativeRecyclerActivity.class);
            add(NativeViewPagerActivity.class);
            add(NativeCarouselAdActivity.class);
            add(NativeMarqueeActivity.class);
            add(NativeAdV2Activity.class);
            add(NativeAutoReloadActivity.class);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantiateListFragment();
    }

    private void instantiateListFragment() {
        int id = new Random().nextInt(0xFFFF);
        FrameLayout container = new FrameLayout(this);
        container.setId(id);
        setContentView(container, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        getSupportFragmentManager()
                .beginTransaction()
                .add(id, new SampleListFragment(R.layout.native_sample))
                .commit();
    }

    public static class SampleListFragment extends ListFragment {
        private final int resId;

        public SampleListFragment(int resId) {
            this.resId = resId;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            return inflater.inflate(resId, container, false);
        }

        @Override
        public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
            Intent intent = new Intent(getActivity(), SAMPLE_ACTIVITIES.get(position));
            intent.putExtra("type", position);
            startActivity(intent);
        }
    }
}