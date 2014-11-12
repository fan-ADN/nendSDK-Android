package net.nend.sample;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ViewPager内に表示するサンプル
 */
public class ViewPagerActivity extends Activity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mViewPager = new ViewPager(this);
        setContentView(mViewPager);

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i < 11; i++) {
            list.add("page"+i);
        }

        mViewPager.setAdapter(new CustomPagerAdapter(this, list));
    }

    public class CustomPagerAdapter extends PagerAdapter {

        private ArrayList<String> mList;

        public CustomPagerAdapter(Context context, ArrayList<String> list) {
            mList = list;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            
            String item = mList.get(position);
            RelativeLayout layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.page, null);
            TextView textView = (TextView) layout.findViewById(R.id.page_title);
            textView.setText(item);
            container.addView(layout);
            
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (RelativeLayout) object;
        }
    }
}
