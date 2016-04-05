package net.nend.sample;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Timer;
import java.util.TimerTask;

public class NativeAutoCarouselActivity extends AppCompatActivity {

    private final int FEED = 0;
    private final int AD = 1;

    private ArrayList<NendAdNative> mLoadedAd = new ArrayList<>();
    private NativeRecyclerAdapter adAdapter;
    private int mWidth;
    private boolean mAdShowFlag = false;

    private RecyclerView mParentRecyclerView;
    private NativeCarouselLayoutManager mLayoutManager;
    private int mThreePieceWidth;

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
        mParentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mParentRecyclerView.setAdapter(new CarouselAdapter(this, list));

        // 広告のRecyclerView表示をリスナで取得
        mParentRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            Timer timer;
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if(view.getId() == R.id.carousel_linear) {
                    initAdRecyclerView();
                    timer = new Timer();
                    CarouselTimerTask carouselTimerTask = new CarouselTimerTask();
                    timer.schedule(carouselTimerTask, 5000, 5000);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if(view.getId() == R.id.carousel_linear) {
                    timer.cancel();
                }
            }
        });

        adAdapter = new NativeRecyclerAdapter(NativeAutoCarouselActivity.this);
    }

    private void initAdRecyclerView() {
//        RecyclerView adRecyclerView = (RecyclerView) findViewById(R.id.carousel_recycler);
//        mLayoutManager = (NativeCarouselLayoutManager) adRecyclerView.getLayoutManager();
        int quadrantWidth = mWidth / 4;
        mThreePieceWidth = quadrantWidth * 3;
        mLayoutManager.setStartCoordinate((mWidth - mThreePieceWidth) / 2);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mWidth = mParentRecyclerView.getWidth();
    }



    // 広告用アダプター
    class NativeRecyclerAdapter extends RecyclerView.Adapter {

        private LayoutInflater mLayoutInflater;
        private NendAdNativeClient mClient;
        private NendAdNativeViewBinder mBinder;

        public NativeRecyclerAdapter(Context context) {
            super();

            mLayoutInflater = LayoutInflater.from(context);
            mBinder = new NendAdNativeViewBinder.Builder()
                    .adImageId(R.id.ad_image)
                    .titleId(R.id.ad_title)
                    .contentId(R.id.ad_content)
                    .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                    .actionId(R.id.ad_action)
                    .build();
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mClient = new NendAdNativeClient(context, 485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
            } else {
                mClient = new NendAdNativeClient(context, 485516, "16cb170982088d81712e63087061378c71e8aa5c");
            }
            mClient.setListener(new NendAdNativeListListener() {
                @Override
                public void onReceiveAd(NendAdNative nendAdNative, int i, final View view, NendAdNativeClient.NendError nendError) {
                    if (nendError == null) {
                        Log.d("NativeRecyclerAdapter", "広告取得成功");
                        mLoadedAd.add(nendAdNative);
                    } else {
                        Log.d("NativeRecyclerAdapter", "広告取得失敗 " + nendError.getMessage());

                        // 広告リクエスト制限を越えた場合
                        if (nendError == NendAdNativeClient.NendError.EXCESSIVE_AD_CALLS) {
                            // すでに取得済みの広告をランダムで表示
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    NendAdNative ad = mLoadedAd.get(new Random().nextInt(mLoadedAd.size()));
                                    ad.intoView(view, mBinder);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onClick(NendAdNative nendAdNative) {
                    Log.i("NativeRecyclerAdapter", "クリック");
                }

                @Override
                public void onDisplayAd(Boolean result, View view) {
                    Log.i("NativeRecyclerAdapter", "広告表示 = " + result);
                    if(!mAdShowFlag) {
                        mAdShowFlag = true;
                        Log.d("広告表示", "フラグが経ちました。");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = mLayoutInflater.inflate(R.layout.native_carousel_card, viewGroup, false);
            view.setLayoutParams(new LinearLayout.LayoutParams(mThreePieceWidth, LinearLayout.LayoutParams.WRAP_CONTENT));
            return mBinder.createRecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
            mClient.loadAd(viewHolder, position);
        }
    }

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
                    view = mLayoutInflater.inflate(R.layout.native_carousel_recycler, parent, false);
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
                    ((AdHolder) viewHolder).recyclerView.setLayoutManager(new NativeCarouselLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                    ((AdHolder) viewHolder).recyclerView.setAdapter(adAdapter);
                    ((AdHolder) viewHolder).recyclerView.addOnScrollListener(new ScrollEventListener());
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

        class AdHolder extends RecyclerView.ViewHolder {
            RecyclerView recyclerView;

            public AdHolder(View itemView) {
                super(itemView);
                recyclerView = (RecyclerView) itemView.findViewById(R.id.carousel_recycler);
            }
        }

    }

    class ScrollEventListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            // スクロール状態が止まった
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                // 現在要素の番号
                int mCurrentViewPosition = mLayoutManager.findFirstVisibleItemPosition();
                // 次要素の番号
                int mNextViewPosition = mLayoutManager.findLastVisibleItemPosition();
                // 次要素取得
                View nextView = mLayoutManager.findViewByPosition(mNextViewPosition);
                // 次要素の左端座標
                int nextViewLeftCoordinate = nextView.getLeft();
                // 画面中央座標取得
                int centerCoordinate = mWidth / 2;
                // 次要素左端座標が中央より左
                if (nextViewLeftCoordinate <= centerCoordinate) {
                    // 次要素をセンタリング
                    mLayoutManager.smoothScrollToPosition(recyclerView, null, mNextViewPosition);

                    // 次要素左が中央より右
                } else if (nextViewLeftCoordinate > centerCoordinate) {

                    // 要素が3つ表示されている場合
                    if (mNextViewPosition - mCurrentViewPosition == 2) {
//TODO ここが動作していない
                        int centralViewPosition = mCurrentViewPosition + 1;


                        mLayoutManager.smoothScrollToPosition(recyclerView, null, centralViewPosition);

                    } else {
                        mLayoutManager.smoothScrollToPosition(recyclerView, null, mCurrentViewPosition);

                    }
                }
            }
        }
    }

    // タイマー
    class CarouselTimerTask extends TimerTask {
        Handler handler = new Handler();
        boolean isLastItem = false;
        @Override
        public void run() {
            handler.post( new Runnable() {
                public void run() {
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.carousel_recycler);
                    if(null != recyclerView) {

                        // 次要素の番号
                        int mNextViewPosition = mLayoutManager.findLastVisibleItemPosition();
                        // 最後の要素の場合は戦闘へ移動
                        if ((mLayoutManager.getItemCount() ) == mNextViewPosition + 1) {
                            if(!isLastItem) {
                                mLayoutManager.smoothScrollToPosition(recyclerView, null, mNextViewPosition);
                                isLastItem = true;
                            } else {
                                mLayoutManager.smoothScrollToPosition(recyclerView, null, 0);
                                isLastItem = false;
                            }
                        } else {
                            mLayoutManager.smoothScrollToPosition(recyclerView, null, mNextViewPosition);
                        }
                    }

                }
            });
        }
    }

}