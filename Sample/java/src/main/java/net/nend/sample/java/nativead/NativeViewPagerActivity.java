package net.nend.sample.java.nativead;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeViewBinder;
import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NativeViewPagerActivity extends AppCompatActivity implements NativePagerFragment.OnAdListener {

    private final String TAG = getClass().getSimpleName();
    private NendAdNativeViewBinder mBinder;
    private NendAdNativeClient mClient;
    // 広告を表示したポジションの一覧
    private List<Integer> mPositionList = new ArrayList<>();
    // 表示したポジションと広告を紐付けて保持
    private HashMap<Integer, NendAdNative> mLoadedAd = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.native_viewpager);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        assert mViewPager != null;
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
        mClient = new NendAdNativeClient(this, 485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
    }

    @Override
    public void onAdRequest(final View view, final int position) {
        if (mLoadedAd.containsKey(position)) {
            mLoadedAd.get(position).intoView(view, mBinder);
        } else {
            mClient.loadAd(new NendAdNativeClient.Callback() {
                @Override
                public void onSuccess(final NendAdNative nendAdNative) {
                    Log.i(TAG, "広告取得成功");
                    mLoadedAd.put(position, nendAdNative);
                    mPositionList.add(position);
                    mLoadedAd.get(position).intoView(view, mBinder);
                    mLoadedAd.get(position).setOnClickListener(new NendAdNative.OnClickListener() {
                        @Override
                        public void onClick(NendAdNative nendAdNative) {
                            Log.i(TAG, "クリック");
                        }
                    });
                }

                @Override
                public void onFailure(NendAdNativeClient.NendError nendError) {
                    Log.i(TAG, "広告取得失敗 " + nendError.getMessage());
                    // すでに取得済みの広告があればランダムで表示
                    if (!mLoadedAd.isEmpty()) {
                        Collections.shuffle(mPositionList);
                        mLoadedAd.get(mPositionList.get(0)).intoView(view, mBinder);
                    }
                }
            });
        }
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
