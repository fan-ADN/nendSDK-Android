package net.nend.sample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeListListener;
import net.nend.android.NendAdNativeViewBinder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class NativeCarouselActivity extends AppCompatActivity {

    private final int NORMAL = 0;
    private final int AD = 1;

    private Handler mHandler = new Handler();
    private ArrayList<NendAdNative> mLoadedAd = new ArrayList<>();
    NativeRecyclerAdapter adAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_carousel);

        // カルーセル広告以外のリスト
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            list.add("sample name" + i);
        }

        // 広告のRecyclerView
        adAdapter = new NativeRecyclerAdapter(this);

        // 親のRecyclerView
        RecyclerView parentRecycler = (RecyclerView) findViewById(R.id.carousel_recycler_parent);
        parentRecycler.setLayoutManager(new LinearLayoutManager(this));
        parentRecycler.setAdapter(new CarouselAdapter(this, list));

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
                    .promotionNameId(R.id.ad_promotion_name)
                    .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                    .actionId(R.id.ad_action)
                    .build();
            mClient = new NendAdNativeClient(context, 485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
            mClient.setListener(new NendAdNativeListListener() {
                @Override
                public void onReceiveAd(NendAdNative nendAdNative, int i, final View view, NendAdNativeClient.NendError nendError) {
                    if (nendError == null) {
                        Log.d(getClass().getSimpleName(), "広告取得成功");
                        mLoadedAd.add(nendAdNative);
                    } else {
                        Log.d(getClass().getSimpleName(), "広告取得失敗 " + nendError.getMessage());

                        // 広告リクエスト制限を越えた場合
                        if (nendError == NendAdNativeClient.NendError.EXCESSIVE_AD_CALLS) {
                            // すでに取得済みの広告をランダムで表示
                            mHandler.post(new Runnable() {
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
                    Log.i(getClass().getSimpleName(), "クリック");
                }

                @Override
                public void onDisplayAd(Boolean result, View view) {
                    Log.i(getClass().getSimpleName(), "広告表示 = " + result);
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
                case NORMAL:
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
                case NORMAL:
                    Date date = new Date();
                    // 表示形式を設定
                    SimpleDateFormat currentDate = new SimpleDateFormat("yyyy'年'MM'月'dd'日'　kk'時'mm'分'ss'秒'");
                    String longText = "LONG TEXT FOR CAROUSEL AD. LONG TEXT FOR CAROUSEL AD.";
                    ((FeedHolder) viewHolder).textName.setText(mList.get(position));
                    ((FeedHolder) viewHolder).textName.setTextColor(Color.DKGRAY);
                    ((FeedHolder) viewHolder).textDate.setText(currentDate.format(date));
                    ((FeedHolder) viewHolder).textDate.setTextColor(Color.DKGRAY);
                    ((FeedHolder) viewHolder).textComment.setText(longText + longText);
                    ((FeedHolder) viewHolder).textComment.setTextColor(Color.DKGRAY);
                    ((FeedHolder) viewHolder).imageIcon.setBackgroundColor(Color.LTGRAY);
                    ((FeedHolder) viewHolder).imageImage.setBackgroundColor(Color.LTGRAY);

                    break;
                case AD:
                    ((AdHolder) viewHolder).recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                    ((AdHolder) viewHolder).recyclerView.setAdapter(adAdapter);
                    ((AdHolder) viewHolder).recyclerView.addOnScrollListener(new ScrollEventListener());
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == 3 ? AD : NORMAL;
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

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int mCurrentViewPosition = manager.findFirstVisibleItemPosition();
                // 次要素の番号
                int mNextViewPosition = manager.findLastVisibleItemPosition();
                // 次要素を取得
                View nextView = manager.findViewByPosition(mNextViewPosition);

                // リサイクラービューのサイズ
                int recyclerViewWidth = recyclerView.getWidth();
                // 次要素のサイズ
                int viewWidth = nextView.getWidth();
                // セットする座標
                final int startCoordinate = (recyclerViewWidth - viewWidth - 20) / 2;

                // 次要素の左端の座標取得
                int nextViewLeftCoordinate = nextView.getLeft();

                // 画面中央座標取得
                int centerCoordinate = recyclerView.getWidth() / 2;


                // 次要素左端座標が中央より右にあれば
                if (nextViewLeftCoordinate <= centerCoordinate) {
//                    manager.scrollToPositionWithOffset(mNextViewPosition, startCoordinate);
                    Smoother ss = new Smoother(getApplicationContext(), startCoordinate);
                    ss.setTargetPosition(mNextViewPosition);
                    manager.startSmoothScroll(ss);
                    manager.smoothScrollToPosition(recyclerView, null, mNextViewPosition);
                } else if (nextViewLeftCoordinate > centerCoordinate) {
                    if (mNextViewPosition - mCurrentViewPosition == 2) {
                        mCurrentViewPosition++;
                    }
                    //    manager.scrollToPositionWithOffset(mCurrentViewPosition, startCoordinate);
                    //    manager.smoothScrollToPosition(recyclerView, null, mCurrentViewPosition);
                    Smoother ss = new Smoother(getApplicationContext(), startCoordinate);
                    ss.setTargetPosition(mCurrentViewPosition);
                    manager.startSmoothScroll(ss);
                    manager.smoothScrollToPosition(recyclerView, null, mCurrentViewPosition);
                }

            }
        }
    }

    class Smoother extends LinearSmoothScroller {

        int startCoordinate;

        public Smoother(Context context) {
            super(context);

        }

        public Smoother(Context context, int dx) {
            super(context);
            startCoordinate = dx;
        }

        @Override
        protected void onStart() {

        }

        @Override
        protected void onStop() {

        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return null;
        }

        @Override
        protected void onSeekTargetStep(int dx, int dy, RecyclerView.State state, Action action) {
            if (dx == startCoordinate) {
                stop();
            }
        }

        @Override
        protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {

        }
    }
}