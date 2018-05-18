package net.nend.sample.java.nativead;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeViewBinder;
import net.nend.sample.java.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NativeCarouselAdActivity extends AppCompatActivity implements NativeCarouselPagerFragment.OnAdListener {

    private final String TAG = getClass().getSimpleName();
    private NendAdNativeViewBinder mBinder;
    private NendAdNativeClient mClient;
    private HashMap<Integer, NendAdNative> mLoadedAd = new HashMap<>();

    private int mMenuPosition;
    private int INTERVAL = 3000;
    private Runnable mAutoCarouselRunnable;
    private Handler mHandler;
    private static final int AUTO_CAROUSEL_MENU_POSITION = 9;  // native ad sample menu position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_carousel);

        // フィード用リスト
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            list.add("sample name" + i);
        }

        // 親のRecyclerView
        RecyclerView parentRecyclerView = (RecyclerView) findViewById(R.id.carousel_recycler_parent);
        assert parentRecyclerView != null;
        parentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        parentRecyclerView.setAdapter(new CarouselAdapter(this, list));

        // セルのスクロールイン・スクロールアウトでオートカルーセルを操作
        parentRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if ((view.getTag() != null && view.getTag().equals("CAROUSEL")) && mMenuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    mHandler.postDelayed(mAutoCarouselRunnable, INTERVAL);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if ((view.getTag() != null && view.getTag().equals("CAROUSEL")) && mMenuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    mHandler.removeCallbacks(mAutoCarouselRunnable);
                }
            }
        });

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

        // オートカルーセル用
        mMenuPosition = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (null != mHandler && mMenuPosition == AUTO_CAROUSEL_MENU_POSITION) {
            if (!hasFocus) {
                mHandler.removeCallbacks(mAutoCarouselRunnable);
            } else {
                mHandler.postDelayed(mAutoCarouselRunnable, INTERVAL);
            }
        }
    }

    @Override
    public void onAdRequest(final View view, final int position) {
        if (mLoadedAd.containsKey(position)) {
            mLoadedAd.get(position).intoView(view, mBinder);
        } else {

            mClient.loadAd(new NendAdNativeClient.Callback() {
                @Override
                public void onSuccess(NendAdNative nendAdNative) {
                    Log.i(TAG, "広告取得成功");
                    mLoadedAd.put(position, nendAdNative);
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
                    Log.i(TAG, "広告取得失敗" + nendError.getMessage());
                }
            });
        }
    }

    // リスト全体のアダプター
    class CarouselAdapter extends RecyclerView.Adapter {
        private final int FEED = 0;
        private final int AD = 1;
        private List<String> mList;
        private LayoutInflater mLayoutInflater;

        public CarouselAdapter(Context context, List<String> list) {
            super();
            mList = list;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View view;
            RecyclerView.ViewHolder viewHolder = null;
            switch (viewType) {
                case FEED:
                    view = mLayoutInflater.inflate(R.layout.native_carousel_card_feed, parent, false);
                    viewHolder = new FeedHolder(view);
                    break;
                case AD:
                    view = mLayoutInflater.inflate(R.layout.native_carousel_viewpager, parent, false);
                    view.setTag("CAROUSEL");
                    viewHolder = new AdHolder(view);
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            switch (getItemViewType(position)) {
                case FEED:
                    Date date = new Date();
                    // 表示形式を設定
                    SimpleDateFormat currentDate = new SimpleDateFormat("yyyy'年'MM'月'dd'日'　kk'時'mm'分'ss'秒'", Locale.JAPAN);
                    String longText = getResources().getString(R.string.carousel_longtext);
                    ((FeedHolder) viewHolder).textName.setText(mList.get(position));
                    ((FeedHolder) viewHolder).textDate.setText(currentDate.format(date));
                    ((FeedHolder) viewHolder).textComment.setText(longText);
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == 3 ? AD : FEED;
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class FeedHolder extends RecyclerView.ViewHolder {

            TextView textName;
            TextView textDate;
            TextView textComment;
            ImageView imageIcon;
            ImageView imageImage;

            public FeedHolder(View itemView) {
                super(itemView);
                textName = (TextView) itemView.findViewById(R.id.carousel_feed_name);
                textDate = (TextView) itemView.findViewById(R.id.carousel_feed_date);
                textComment = (TextView) itemView.findViewById(R.id.carousel_feed_comment);
                imageIcon = (ImageView) itemView.findViewById(R.id.carousel_feed_icon);
                imageImage = (ImageView) itemView.findViewById(R.id.carousel_feed_image);
            }
        }

        // 広告用ホルダー
        class AdHolder extends RecyclerView.ViewHolder {
            NativeCarouselViewPager viewPager;

            public AdHolder(View itemView) {
                super(itemView);
                viewPager = (NativeCarouselViewPager) itemView.findViewById(R.id.carousel_pager);
                viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
                viewPager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -10);

                // オートカルーセル
                if (mMenuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    mHandler = new Handler();
                    mAutoCarouselRunnable = new Runnable() {
                        @Override
                        public void run() {
                            int nextItem = viewPager.getCurrentItem() + 1;
                            if (nextItem == 5) {
                                nextItem = 0;
                            }
                            viewPager.setCurrentItem(nextItem, true);
                            mHandler.postDelayed(this, INTERVAL);
                        }
                    };
                }
            }
        }
    }

    // カルーセル部分ViewPager用Adapter
    public class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return NativeCarouselPagerFragment.newInstance(position, R.layout.native_carousel_fragment);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }


}