package net.nend.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeViewBinder;

public class NativeViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private NendAdNativeViewBinder mBinder;
    private NendAdNativeClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.native_viewpager);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));

        mBinder = new NendAdNativeViewBinder.Builder()
                .adImageId(R.id.ad_image)
                .logoImageId(R.id.logo_image)
                .titleId(R.id.ad_title)
                .contentId(R.id.ad_content)
                .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                .promotionUrlId(R.id.ad_promotion_url)
                .promotionNameId(R.id.ad_promotion_name)
                .actionId(R.id.ad_action)
                .build();
        mClient = new NendAdNativeClient(this, 24516, "89c4239f085dc934d6dcda8af1d6098706c06d38");
    }

    public void onAdRequest(View view, int position){
        mClient.loadAd(view, mBinder, position);
    }

    public class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            NativePagerFragment fragment = new NativePagerFragment();
            Bundle extras = new Bundle();
            extras.putInt("position", position);
            fragment.setArguments(extras);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
