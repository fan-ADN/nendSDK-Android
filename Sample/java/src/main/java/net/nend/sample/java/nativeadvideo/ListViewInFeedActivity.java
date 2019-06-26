package net.nend.sample.java.nativeadvideo;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeVideo;
import net.nend.android.NendAdNativeVideoLoader;
import net.nend.android.NendAdNativeViewBinder;
import net.nend.sample.java.R;
import net.nend.sample.java.nativeadvideo.utilities.MyNendAdViewBinder;
import net.nend.sample.java.nativeadvideo.utilities.MyNendAdViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ListViewInFeedActivity extends ListActivity {

    private final int NORMAL = 0;
    private final int AD = 1;
    private SparseArray<NendAdNativeVideo> loadedAds = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            list.add("item" + i);
        }

        NativeListAdapter adapter = new NativeListAdapter(this, 0, list);
        setListAdapter(adapter);
    }

    class NativeListAdapter extends ArrayAdapter<String> {

        private NendAdNativeVideoLoader videoLoader;
        private MyNendAdViewBinder videoBinder;
        private NendAdNativeViewBinder normalBinder;

        NativeListAdapter(Context context, int resource, List<String> list) {
            super(context, resource, list);

            normalBinder = new NendAdNativeViewBinder.Builder()
                    .adImageId(R.id.native_video_ad_imageview)
                    .titleId(R.id.native_video_ad_title)
                    .prId(R.id.native_video_ad_advertiser, NendAdNative.AdvertisingExplicitly.SPONSORED)
                    .build();
            videoBinder = new MyNendAdViewBinder.Builder()
                    .mediaViewId(R.id.native_video_ad_mediaview)
                    .titleId(R.id.native_video_ad_title)
                    .advertiserId(R.id.native_video_ad_advertiser)
                    .build();

            videoLoader = new NendAdNativeVideoLoader(
                    context,
                    ExamplesActivity.NATIVE_VIDEO_SPOT_ID,
                    ExamplesActivity.NATIVE_VIDEO_API_KEY,
                    NendAdNativeVideo.VideoClickOption.LP
            );
            videoLoader.setFillerImageNativeAd(485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return (position != 0 && position % 5 == 0) ? AD : NORMAL;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            switch (getItemViewType(position)) {
                case AD:
                    final MyNendAdViewHolder adHolder;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.native_video_ad_row_for_list, parent, false);
                        adHolder = new MyNendAdViewHolder(convertView, videoBinder);
                        convertView.setTag(adHolder);
                    } else {
                        adHolder = (MyNendAdViewHolder) convertView.getTag();
                    }

                    NendAdNativeVideo loadedAd = loadedAds.get(position);
                    if (loadedAd != null) {
                        adHolder.renderView(loadedAd, null);
                    } else {
                        videoLoader.loadAd(new NendAdNativeVideoLoader.Callback() {
                            @Override
                            public void onSuccess(NendAdNativeVideo ad) {
                                if (ad.hasVideo()) {
                                    adHolder.itemView.findViewById(R.id.native_video_ad_mediaview).setVisibility(View.VISIBLE);
                                    adHolder.itemView.findViewById(R.id.native_video_ad_imageview).setVisibility(View.GONE);
                                    loadedAds.put(position, ad);
                                    adHolder.renderView(ad, null);
                                } else {
                                    adHolder.itemView.findViewById(R.id.native_video_ad_mediaview).setVisibility(View.GONE);
                                    adHolder.itemView.findViewById(R.id.native_video_ad_imageview).setVisibility(View.VISIBLE);
                                    NendAdNative nendAdNative = ad.getFallbackAd();
                                    nendAdNative.intoView(adHolder.itemView, normalBinder);
                                }
                            }

                            @Override
                            public void onFailure(int error) {
                            }
                        });
                    }

                    break;
                case NORMAL:
                default:
                    final ViewHolder holder;
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.native_list_row, parent, false);
                        holder = new ViewHolder();
                        holder.textView = convertView.findViewById(R.id.title);
                        holder.imageView = convertView.findViewById(R.id.thumbnail);
                        convertView.setTag(holder);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    holder.textView.setText(getItem(position));
                    holder.imageView.setBackgroundColor(Color.LTGRAY);
                    break;
            }
            return convertView;
        }

        class ViewHolder {
            TextView textView;
            ImageView imageView;
        }
    }
}
