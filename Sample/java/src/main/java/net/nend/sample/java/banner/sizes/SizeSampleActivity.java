package net.nend.sample.java.banner.sizes;

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

public class SizeSampleActivity extends AppCompatActivity {

    public static final List<Integer> SIZE_SAMPLE_ACTIVITIES = new ArrayList<Integer>() {
        {
            add(R.layout.ad_320x50);
            add(R.layout.ad_320x100);
            add(R.layout.ad_300x100);
            add(R.layout.ad_300x250);
            add(R.layout.ad_728x90);
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
                .add(id, new SizeSampleListFragment(R.layout.size_sample))
                .commit();
    }

    public static class SizeSampleListFragment extends ListFragment {
        private final int resId;

        public SizeSampleListFragment(int resId) {
            this.resId = resId;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            return inflater.inflate(resId, container, false);
        }

        @Override
        public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
            Intent intent = new Intent(getActivity(), SizeActivity.class);
            intent.putExtra("size_type", position);
            startActivity(intent);
        }
    }
}