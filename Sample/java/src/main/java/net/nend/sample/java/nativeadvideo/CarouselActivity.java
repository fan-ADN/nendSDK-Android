package net.nend.sample.java.nativeadvideo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.nend.android.NendAdNativeVideo;
import net.nend.android.NendAdNativeVideoListener;
import net.nend.android.NendAdNativeVideoLoader;
import net.nend.sample.java.R;
import net.nend.sample.java.nativead.NativeCarouselPagerFragment;
import net.nend.sample.java.nativead.NativeCarouselViewPager;
import net.nend.sample.java.nativeadvideo.utilities.MyNendAdViewBinder;
import net.nend.sample.java.nativeadvideo.utilities.MyNendAdViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CarouselActivity extends AppCompatActivity implements NativeCarouselPagerFragment.OnAdListener {

    private static final int AUTO_CAROUSEL_MENU_POSITION = 9;  // native ad sample menu position
    private final String TAG = getClass().getSimpleName();
    private MyNendAdViewBinder binder;
    private NendAdNativeVideoLoader loader;
    private SparseArray<NendAdNativeVideo> loadedAds = new SparseArray<>();
    private int menuPosition;
    private int INTERVAL = 3000;
    private Runnable autoCarouselRunnable;
    private Handler handler;

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
        RecyclerView parentRecyclerView = findViewById(R.id.carousel_recycler_parent);
        assert parentRecyclerView != null;
        parentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        parentRecyclerView.setAdapter(new CarouselAdapter(this, list));

        // セルのスクロールイン・スクロールアウトでオートカルーセルを操作
        parentRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if ((view.getTag() != null && view.getTag().equals("CAROUSEL")) && menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    handler.postDelayed(autoCarouselRunnable, INTERVAL);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if ((view.getTag() != null && view.getTag().equals("CAROUSEL")) && menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    handler.removeCallbacks(autoCarouselRunnable);
                }
            }
        });

        binder = new MyNendAdViewBinder.Builder()
                .mediaViewId(R.id.ad_mediaview)
                .titleId(R.id.ad_title)
                .contentId(R.id.ad_content)
                .advertiserId(R.id.ad_advertiser)
                .actionId(R.id.ad_action)
                .build();
        loader = new NendAdNativeVideoLoader(this,
                ExamplesActivity.NATIVE_VIDEO_SPOT_ID, ExamplesActivity.NATIVE_VIDEO_API_KEY);

        // オートカルーセル用
        menuPosition = getIntent().getIntExtra("type", 0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (null != handler && menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
            if (!hasFocus) {
                handler.removeCallbacks(autoCarouselRunnable);
            } else {
                handler.postDelayed(autoCarouselRunnable, INTERVAL);
            }
        }
    }

    @Override
    public void onAdRequest(final View view, final int position) {
        NendAdNativeVideo loadedAd = loadedAds.get(position);
        final MyNendAdViewHolder holder = new MyNendAdViewHolder(view, binder);
        if (loadedAd != null) {
            holder.renderView(loadedAd, null);
        } else {
            loader.loadAd(new NendAdNativeVideoLoader.Callback() {
                @Override
                public void onSuccess(NendAdNativeVideo ad) {
                    Log.i(TAG, "広告取得成功");
                    loadedAds.put(position, ad);
                    ad.setListener(new NendAdNativeVideoListener() {
                        @Override
                        public void onImpression(NendAdNativeVideo nendAdNativeVideo) {
                        }

                        @Override
                        public void onClickAd(NendAdNativeVideo nendAdNativeVideo) {
                            Log.i(TAG, "クリック");
                        }

                        @Override
                        public void onClickInformation(NendAdNativeVideo nendAdNativeVideo) {
                        }
                    });
                    holder.renderView(ad, null);
                }

                @Override
                public void onFailure(int errorCode) {
                    Log.i(TAG, "広告取得失敗: " + errorCode);
                }
            });
        }
    }

    class CarouselAdapter extends RecyclerView.Adapter {
        private final int FEED = 0;
        private final int AD = 1;
        private List<String> list;
        private LayoutInflater layoutInflater;

        CarouselAdapter(Context context, List<String> list) {
            super();
            this.list = list;
            layoutInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
            View view;
            RecyclerView.ViewHolder viewHolder;
            switch (viewType) {
                case AD:
                    view = layoutInflater.inflate(R.layout.native_carousel_viewpager, parent, false);
                    view.setTag("CAROUSEL");
                    viewHolder = new AdHolder(view);
                    break;
                case FEED:
                default:
                    view = layoutInflater.inflate(R.layout.native_carousel_card_feed, parent, false);
                    viewHolder = new FeedHolder(view);
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            switch (getItemViewType(position)) {
                case FEED:
                    Date date = new Date();
                    // 表示形式を設定
                    SimpleDateFormat currentDate = new SimpleDateFormat("yyyy'年'MM'月'dd'日'　kk'時'mm'分'ss'秒'", Locale.JAPAN);
                    String longText = getResources().getString(R.string.carousel_longtext);
                    ((FeedHolder) viewHolder).textName.setText(list.get(position));
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
            return list.size();
        }

        class FeedHolder extends RecyclerView.ViewHolder {

            TextView textName;
            TextView textDate;
            TextView textComment;
            ImageView imageIcon;
            ImageView imageImage;

            FeedHolder(View itemView) {
                super(itemView);
                textName = itemView.findViewById(R.id.carousel_feed_name);
                textDate = itemView.findViewById(R.id.carousel_feed_date);
                textComment = itemView.findViewById(R.id.carousel_feed_comment);
                imageIcon = itemView.findViewById(R.id.carousel_feed_icon);
                imageImage = itemView.findViewById(R.id.carousel_feed_image);
            }
        }

        // 広告用ホルダー
        class AdHolder extends RecyclerView.ViewHolder {
            NativeCarouselViewPager viewPager;

            AdHolder(View itemView) {
                super(itemView);
                viewPager = itemView.findViewById(R.id.carousel_pager);
                viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
                viewPager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -10);

                // オートカルーセル
                if (menuPosition == AUTO_CAROUSEL_MENU_POSITION) {
                    handler = new Handler();
                    autoCarouselRunnable = new Runnable() {
                        @Override
                        public void run() {
                            int nextItem = viewPager.getCurrentItem() + 1;
                            if (nextItem == 5) {
                                nextItem = 0;
                            }
                            viewPager.setCurrentItem(nextItem, true);
                            handler.postDelayed(this, INTERVAL);
                        }
                    };
                }
            }
        }
    }

    // カルーセル部分ViewPager用Adapter
    public class CustomPagerAdapter extends FragmentPagerAdapter {

        CustomPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return NativeCarouselPagerFragment.newInstance(position, R.layout.native_video_carousel_fragment);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
