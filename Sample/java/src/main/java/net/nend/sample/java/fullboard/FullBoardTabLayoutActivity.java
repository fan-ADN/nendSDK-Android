package net.nend.sample.java.fullboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import net.nend.android.NendAdFullBoard;
import net.nend.android.NendAdFullBoardLoader;
import net.nend.android.NendAdFullBoardView;
import net.nend.sample.java.R;

public class FullBoardTabLayoutActivity extends AppCompatActivity {

    private NendAdFullBoard mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_board_tab_layout);

        NendAdFullBoardLoader loader = new NendAdFullBoardLoader(getApplicationContext(), 485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
        loader.loadAd(new NendAdFullBoardLoader.Callback() {
            @Override
            public void onSuccess(NendAdFullBoard nendAdFullBoard) {
                populateTabs(nendAdFullBoard);
            }

            @Override
            public void onFailure(NendAdFullBoardLoader.FullBoardAdError fullBoardAdError) {
                finish();
            }
        });
    }

    private void populateTabs(NendAdFullBoard ad) {
        mAd = ad;

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);
    }

    public static class ListFragment extends android.support.v4.app.ListFragment {

        private static String[] sItems = new String[50];

        static {
            for (int i = 0; i < sItems.length; i++) {
                sItems[i] = "Item" + (i + 1);
            }
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, sItems));
        }
    }

    public static class AdFragment extends Fragment {

        private NendAdFullBoard mAd;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if (mAd != null) {
                return NendAdFullBoardView.Builder.with(getActivity(), mAd).build();
            } else {
                return null;
            }
        }

        void setAd(NendAdFullBoard ad) {
            mAd = ad;
        }
    }

    private class Adapter extends FragmentPagerAdapter {

        Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            if (0 == position || 1 == position) {
                return new ListFragment();
            } else {
                AdFragment fragment = new AdFragment();
                fragment.setAd(mAd);
                return fragment;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "First";
                case 1:
                    return "Second";
                case 2:
                    return "PR";
            }
            return super.getPageTitle(position);
        }
    }
}
