package net.nend.sample.java.banner.layoutpatterns;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import net.nend.sample.java.R;

import java.util.ArrayList;

/**
 * ViewPager内に表示するサンプル
 */
public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager mViewPager = new ViewPager(this);
        setContentView(mViewPager);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            list.add("page"+i);
        }

        mViewPager.setAdapter(new CustomPagerAdapter(list));
    }

    public class CustomPagerAdapter extends PagerAdapter {

        private ArrayList<String> mList;

        public CustomPagerAdapter(ArrayList<String> list) {
            mList = list;
        }

        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            
            String item = mList.get(position);
            RelativeLayout layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.page, null);
            TextView textView = layout.findViewById(R.id.page_title);
            textView.setText(item);
            container.addView(layout);
            
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
