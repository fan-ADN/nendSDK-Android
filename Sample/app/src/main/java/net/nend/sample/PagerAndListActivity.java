package net.nend.sample;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * ViewPager内のListViewに表示するサンプル
 */
public class PagerAndListActivity extends FragmentActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.pager);
        setContentView(mViewPager);

        ArrayList<String> pageList = new ArrayList<String>();
        for (int i = 1; i < 11; i++) {
            pageList.add("page"+i);
        }

        ArrayList<String> itemList = new ArrayList<String>();
        for (int i = 1; i < 51; i++) {
            itemList.add("item"+i);
        }

        mViewPager.setAdapter(new CustomPagerAdapter(
                getSupportFragmentManager(), pageList, itemList));
    }

    public class CustomPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<String> mPageList;
        private ArrayList<String> mItemList;
        
        public CustomPagerAdapter(FragmentManager fragmentManager
                , ArrayList<String> pageList, ArrayList<String> itemList) {
            super(fragmentManager);
            mPageList = pageList;
            mItemList = itemList;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putStringArrayList("itemList", mItemList);
            args.putInt("position", position);
            return Fragment.instantiate(getApplicationContext(), PageFragment.class.getName(), args);
        }

        @Override
        public int getCount() {
            return mPageList.size();
        }
    }
}
