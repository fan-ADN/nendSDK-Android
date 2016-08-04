package net.nend.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NativeRecyclerActivity extends AppCompatActivity {

    private final int NORMAL = 0;
    private final int AD = 1;
    private final String TAG = getClass().getSimpleName();
    // 広告を表示したポジションの一覧
    private List<Integer> mPositionList = new ArrayList<>();
    // 表示したポジションと広告を紐付けて保持
    private HashMap<Integer, NendAdNative> mLoadedAd = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_recycler);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            list.add("item" + i);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        assert recyclerView != null;
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
            mClient = new NendAdNativeClient(context, 485516, "16cb170982088d81712e63087061378c71e8aa5c");
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position != 0 && position % 5 == 0) ? AD : NORMAL;
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
        @SuppressLint("RecyclerView")
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
            switch (getItemViewType(position)) {
                case NORMAL:
                    ((ViewHolder) viewHolder).textView.setText(mList.get(position));
                    ((ViewHolder) viewHolder).imageView.setBackgroundColor(Color.LTGRAY);
                    break;
                case AD:
                    if (mLoadedAd.containsKey(position)) {
                        mLoadedAd.get(position).intoView(viewHolder);
                        break;
                    }

                    mClient.loadAd(new NendAdNativeClient.Callback() {
                        @Override
                        public void onSuccess(final NendAdNative nendAdNative) {
                            Log.i(TAG, "広告取得成功");
                            mLoadedAd.put(position, nendAdNative);
                            mPositionList.add(position);
                            viewHolder.setIsRecyclable(false);
                            mLoadedAd.get(position).intoView(viewHolder);
                            mLoadedAd.get(position).setOnClickListener(new NendAdNative.OnClickListener() {
                                @Override
                                public void onClick(NendAdNative nendAdNative) {
                                    Log.i(TAG, "クリック" + position);
                                }
                            });
                        }

                        @Override
                        public void onFailure(NendAdNativeClient.NendError nendError) {
                            Log.i(TAG, "広告取得失敗 " + nendError.getMessage());
                            // すでに取得済みの広告をランダムで表示
                            if (!mLoadedAd.isEmpty()) {
                                Collections.shuffle(mPositionList);
                                mLoadedAd.get(mPositionList.get(0)).intoView(viewHolder);
                            }
                        }
                    });
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