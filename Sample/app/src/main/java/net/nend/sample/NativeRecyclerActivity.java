package net.nend.sample;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeListListener;
import net.nend.android.NendAdNativeViewBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NativeRecyclerActivity extends AppCompatActivity {

    private final int NORMAL = 0;
    private final int AD = 1;

    private Handler mHandler = new Handler();
    private ArrayList<NendAdNative> mLoadedAd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_recycler);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            list.add("item"+i);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NativeRecyclerAdapter(this, list));
    }

    class NativeRecyclerAdapter extends RecyclerView.Adapter {

        private LayoutInflater mLayoutInflater;
        private List<String> mList;
        private NendAdNativeClient mClient;
        private NendAdNativeViewBinder mBinder;

        public NativeRecyclerAdapter(Context context, List<String> list) {
            super();

            mLayoutInflater = LayoutInflater.from(context);
            mList = list;

            mBinder = new NendAdNativeViewBinder.Builder()
                    .adImageId(R.id.ad_image)
                    .titleId(R.id.ad_title)
                    .promotionNameId(R.id.ad_promotion_name)
                    .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.PR)
                    .build();
            mClient = new NendAdNativeClient(context, 24443, "4e418660631509c2466b9b719a267e376b5ddb66");
            mClient.setListener(new NendAdNativeListListener() {
                @Override
                public void onReceiveAd(NendAdNative nendAdNative, int i, final View view, NendAdNativeClient.NendError nendError) {
                    if (nendError == null) {
                        Log.i(getClass().getSimpleName(), "広告取得成功");
                        mLoadedAd.add(nendAdNative);
                    } else {
                        Log.i(getClass().getSimpleName(), "広告取得失敗 " + nendError.getMessage());

                        // 広告リクエスト制限を越えた場合
                        if(nendError == NendAdNativeClient.NendError.EXCESSIVE_AD_CALLS){
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
            return mList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position != 0 && position % 5 == 0)? AD : NORMAL;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view;
            RecyclerView.ViewHolder viewHolder = null;
            switch (viewType) {
                case NORMAL:
                    view = mLayoutInflater.inflate(R.layout.native_list_row, viewGroup, false);
                    viewHolder = new ViewHolder(view);
                    break;
                case AD:
                    view = mLayoutInflater.inflate(R.layout.native_ad_left_row, viewGroup, false);
                    viewHolder = mBinder.createRecyclerViewHolder(view);
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
            switch (getItemViewType(position)) {
                case NORMAL:
                    ((ViewHolder) viewHolder).textView.setText(mList.get(position));
                    ((ViewHolder) viewHolder).imageView.setBackgroundColor(Color.LTGRAY);
                    break;
                case AD:
                    mClient.loadAd(viewHolder, position);
                    break;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView;
            ImageView imageView;

            public ViewHolder(View v) {
                super(v);
                textView = (TextView) v.findViewById(R.id.title);
                imageView = (ImageView) v.findViewById(R.id.thumbnail);
            }
        }
    }
}