package net.nend.sample.java.nativeadvideo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeVideo;
import net.nend.android.NendAdNativeVideoLoader;
import net.nend.sample.java.R;
import net.nend.sample.java.nativeadvideo.utilities.MyNendAdViewBinder;
import net.nend.sample.java.nativeadvideo.utilities.MyNendAdViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewInFeedActivity extends AppCompatActivity {

    private final int NORMAL = 0;
    private final int AD = 1;

    private NativeRecyclerAdapter adapter;
    private SparseArray<NendAdNativeVideo> loadedAds = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_recycler);

        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager manager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                    if (manager == null) {
                        return;
                    }
                    int firstPosition = manager.findFirstVisibleItemPosition();
                    int lastPosition = manager.findLastVisibleItemPosition();

                    for (int index = firstPosition; index < lastPosition; index++) {
                        RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(index);
                        if (holder instanceof MyNendAdViewHolder) {
                            adapter.onBindViewHolder(holder, index);
                        }
                    }
                }
            }
        });

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            list.add("item" + i);
        }
        adapter = new NativeRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);

    }

    class NativeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private LayoutInflater layoutInflater;
        private List<String> objects;
        private NendAdNativeVideoLoader loader;
        private MyNendAdViewBinder videoBinder;
        private Context context;

        NativeRecyclerAdapter(Context context, List<String> list) {
            super();

            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            objects = list;

            videoBinder = new MyNendAdViewBinder.Builder()
                    .mediaViewId(R.id.native_video_ad_for_feed_video)
                    .titleId(R.id.native_video_ad_for_feed_title)
                    .advertiserId(R.id.native_video_ad_for_feed_advertiser_name)
                    .build();
            loader = new NendAdNativeVideoLoader(context,
                    ExamplesActivity.NATIVE_VIDEO_SPOT_ID, ExamplesActivity.NATIVE_VIDEO_API_KEY);
        }

        @Override
        public int getItemCount() {
            return objects.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position != 0 && position % 5 == 0) ? AD : NORMAL;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view;
            RecyclerView.ViewHolder viewHolder;
            if (viewType == AD) {
                view = layoutInflater.inflate(R.layout.native_video_ad_row_for_feed, viewGroup, false);
                viewHolder = new MyNendAdViewHolder(view, videoBinder);
            } else {
                view = layoutInflater.inflate(R.layout.native_list_row, viewGroup, false);
                viewHolder = new ViewHolder(view);
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {
            switch (getItemViewType(position)) {
                case NORMAL:
                    ((ViewHolder) viewHolder).textView.setText(objects.get(position));
                    ((ViewHolder) viewHolder).imageView.setBackgroundColor(Color.LTGRAY);
                    break;
                case AD:
                    NendAdNativeVideo loadedAd = loadedAds.get(position);
                    if (loadedAd != null) {
                        setNativeVideoAdAtViewHolder(loadedAd, (MyNendAdViewHolder) viewHolder);
                    } else {
                        final int lastPosition = position;
                        loader.loadAd(new NendAdNativeVideoLoader.Callback() {
                            @Override
                            public void onSuccess(final NendAdNativeVideo ad) {
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadedAds.put(lastPosition, ad);
                                        setNativeVideoAdAtViewHolder(ad, (MyNendAdViewHolder) viewHolder);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(int errorCode) {

                            }
                        });
                    }
                    break;
            }
        }

        private void setNativeVideoAdAtViewHolder(NendAdNativeVideo nativeVideo, MyNendAdViewHolder holder) {
            if (holder.logoImageView != null) {
                final ImageView imageView = holder.logoImageView;
                imageView.setImageBitmap(nativeVideo.getLogoImageBitmap());
            }
            if (holder.titleTextView != null)
                holder.titleTextView.setText(nativeVideo.getTitleText());
            if (holder.contentTextView != null)
                holder.contentTextView.setText(nativeVideo.getDescriptionText());
            if (holder.advertiserNameTextView != null)
                holder.advertiserNameTextView.setText(nativeVideo.getAdvertiserName());
            if (holder.prTextView != null) {
                holder.prTextView.setText(NendAdNative.AdvertisingExplicitly.SPONSORED.getText());
            }
            if (holder.actionTextView != null) {
                holder.actionTextView.setText(nativeVideo.getCallToActionText());
            }

            nativeVideo.setMutePlayingFullscreen(true);

            ViewParent parent = holder.itemView.getParent();
            if (parent instanceof RecyclerView) {
                videoBinder.renderView(holder, nativeVideo, null);
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView;
            ImageView imageView;

            ViewHolder(View v) {
                super(v);
                textView = v.findViewById(R.id.title);
                imageView = v.findViewById(R.id.thumbnail);
            }
        }
    }
}
