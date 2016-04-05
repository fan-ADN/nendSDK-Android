package net.nend.sample;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeListListener;
import net.nend.android.NendAdNativeViewBinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class NativeCarouselAdActivity extends AppCompatActivity implements NativePagerFragment.OnAdListener {

    private final int FEED = 0;
    private final int AD = 1;

    private ArrayList<NendAdNative> mLoadedAd = new ArrayList<>();
//    private NativeRecyclerAdapter adAdapter;
    private int mWidth;

    private RecyclerView mParentRecyclerView;
    private int mThreePieceWidth;
    LinearLayoutManager linearLayoutManager;

    private ViewPager mViewPager;
    private NendAdNativeViewBinder mBinder;
    private NendAdNativeClient mClient;

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
        mParentRecyclerView = (RecyclerView) findViewById(R.id.carousel_recycler_parent);
        linearLayoutManager = new LinearLayoutManager(this);
        mParentRecyclerView.setLayoutManager(linearLayoutManager);
        mParentRecyclerView.setAdapter(new CarouselAdapter(this, list));

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

    private void initAdRecyclerView() {
        RecyclerView adRecyclerView = (RecyclerView) findViewById(R.id.carousel_recycler);
        NativeCarouselLayoutManager mLayoutManager = (NativeCarouselLayoutManager) adRecyclerView.getLayoutManager();
        int quadrantWidth = mWidth / 4;
        mThreePieceWidth = quadrantWidth * 3;
        mLayoutManager.setStartCoordinate((mWidth - mThreePieceWidth) / 2);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mWidth = mParentRecyclerView.getWidth();
    }

    @Override
    public void onAdRequest(View view, int position) {
        mClient.loadAd(view, mBinder, position);
    }

    // ViewPager用Adapter
    public class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            NativePagerFragment fragment = new NativePagerFragment();
            Bundle extras = new Bundle();
            extras.putInt("position", position);
            Log.d("CUSTOM", ""+position);
            fragment.setArguments(extras);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }


    // 広告用アダプター
//    class NativeRecyclerAdapter extends RecyclerView.Adapter {
//
//        private LayoutInflater mLayoutInflater;
//        private NendAdNativeClient mClient;
//        private NendAdNativeViewBinder mBinder;
//
//        public NativeRecyclerAdapter(Context context) {
//            super();
//
//            mLayoutInflater = LayoutInflater.from(context);
//            mBinder = new NendAdNativeViewBinder.Builder()
//                    .adImageId(R.id.ad_image)
//                    .titleId(R.id.ad_title)
//                    .contentId(R.id.ad_content)
//                    .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
//                    .actionId(R.id.ad_action)
//                    .build();
//            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                mClient = new NendAdNativeClient(context, 485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
//            } else {
//                mClient = new NendAdNativeClient(context, 485516, "16cb170982088d81712e63087061378c71e8aa5c");
//            }
//            mClient.setListener(new NendAdNativeListListener() {
//                @Override
//                public void onReceiveAd(NendAdNative nendAdNative, int i, final View view, NendAdNativeClient.NendError nendError) {
//                    if (nendError == null) {
//                        Log.d("NativeRecyclerAdapter", "広告取得成功");
//                        mLoadedAd.add(nendAdNative);
//                    } else {
//                        Log.d("NativeRecyclerAdapter", "広告取得失敗 " + nendError.getMessage());
//
//                        // 広告リクエスト制限を越えた場合
//                        if (nendError == NendAdNativeClient.NendError.EXCESSIVE_AD_CALLS) {
//                            // すでに取得済みの広告をランダムで表示
//                            Handler handler = new Handler();
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    NendAdNative ad = mLoadedAd.get(new Random().nextInt(mLoadedAd.size()));
//                                    ad.intoView(view, mBinder);
//                                }
//                            });
//                        }
//                    }
//                }
//
//                @Override
//                public void onClick(NendAdNative nendAdNative) {
//                    Log.i("NativeRecyclerAdapter", "クリック");
//                }
//
//                @Override
//                public void onDisplayAd(Boolean result, final View view) {
//                    Log.i("NativeRecyclerAdapter", "広告表示 = " + result);
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return 10;
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            View view = mLayoutInflater.inflate(R.layout.native_carousel_card, viewGroup, false);
//            view.setLayoutParams(new LinearLayout.LayoutParams(mThreePieceWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
//            return mBinder.createRecyclerViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
//            mClient.loadAd(viewHolder, position);
//        }
//    }

    // リスト全体のアダプター
    class CarouselAdapter extends RecyclerView.Adapter {
        private List<String> mList;
        private LayoutInflater mLayoutInflater;
        private Context mContext;

        public CarouselAdapter(Context context, List<String> list) {
            super();
            mList = list;
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            RecyclerView.ViewHolder viewHolder = null;
            switch (viewType) {
                case FEED:
                    view = mLayoutInflater.inflate(R.layout.native_carousel_card_feed, parent, false);
                    viewHolder = new FeedHolder(view);
                    break;
                case AD:
                    view = mLayoutInflater.inflate(R.layout.native_viewpager, parent, false);
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
                    ((FeedHolder) viewHolder).textName.setTextColor(Color.DKGRAY);
                    ((FeedHolder) viewHolder).textName.setTypeface(Typeface.DEFAULT_BOLD);
                    ((FeedHolder) viewHolder).textDate.setText(currentDate.format(date));
                    ((FeedHolder) viewHolder).textDate.setTextColor(Color.LTGRAY);
                    ((FeedHolder) viewHolder).textDate.setTextSize(11);
                    ((FeedHolder) viewHolder).textComment.setText(longText);
                    ((FeedHolder) viewHolder).textComment.setTextColor(Color.DKGRAY);
                    ((FeedHolder) viewHolder).imageIcon.setBackgroundColor(Color.LTGRAY);
                    ((FeedHolder) viewHolder).imageImage.setBackgroundColor(Color.LTGRAY);
                    break;
                case AD:
                    ((AdHolder) viewHolder).viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager()));
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == 2 ? AD : FEED;
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

        class AdHolder extends RecyclerView.ViewHolder {
            ViewPager viewPager;
            public AdHolder(View itemView) {
                super(itemView);
                viewPager = (ViewPager) itemView.findViewById(R.id.pager);
            }
        }
    }
}