package net.nend.sample.java.banner.layoutpatterns;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import net.nend.sample.java.R;

import java.util.ArrayList;

/**
 * ViewPager内のListViewに表示するサンプル
 */
public class PagerAndListActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.pager);
        setContentView(mViewPager);

        ArrayList<String> pageList = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            pageList.add("page"+i);
        }

        ArrayList<String> itemList = new ArrayList<>();
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
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
