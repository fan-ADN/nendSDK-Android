package net.nend.sample;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeListListener;
import net.nend.android.NendAdNativeViewBinder;
import net.nend.android.NendAdNativeViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NativeMultipleLayoutListActivity extends ListActivity {

    private final int NORMAL = 0;
    private final int AD1 = 1;
    private final int AD2 = 2;

    private Handler mHandler = new Handler();
    private ArrayList<NendAdNative> mLoadedAd = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            list.add("item"+i);
        }

        NativeListAdapter adapter = new NativeListAdapter(this, 0, list);
        setListAdapter(adapter);
    }

    class NativeListAdapter extends ArrayAdapter<String> {

        private NendAdNativeClient mClient;
        private NendAdNativeViewBinder mBinder;
        private List<String> mList;

        public NativeListAdapter(Context context, int resource, List<String> list) {
            super(context, resource, list);
            mList = list;

            mBinder = new NendAdNativeViewBinder.Builder()
                    .adImageId(R.id.ad_image)
                    .titleId(R.id.ad_title)
                    .promotionNameId(R.id.ad_promotion_name)
                    .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                    .build();

            mClient = new NendAdNativeClient(context, 485516, "16cb170982088d81712e63087061378c71e8aa5c");
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
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            if(position != 0 && position % 5 == 0){
                return (position % 10 == 0)? AD2 : AD1;
            }
            return NORMAL;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final NendAdNativeViewHolder adHolder;
            switch (getItemViewType(position)){
                case NORMAL:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.native_list_row, parent, false);
                        holder = new ViewHolder();
                        holder.textView = (TextView) convertView.findViewById(R.id.title);
                        holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
                        convertView.setTag(holder);
                    }else{
                        holder = (ViewHolder) convertView.getTag();
                    }
                    holder.textView.setText(getItem(position));
                    holder.imageView.setBackgroundColor(Color.LTGRAY);
                    break;
                case AD1:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.native_ad_left_row, parent, false);
                        adHolder = mBinder.createViewHolder(convertView);
                        convertView.setTag(adHolder);
                    }else{
                        adHolder = (NendAdNativeViewHolder) convertView.getTag();
                    }
                    mClient.loadAd(adHolder, position);
                    break;
                case AD2:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.native_ad_right_row, parent, false);
                        adHolder = mBinder.createViewHolder(convertView);
                        convertView.setTag(adHolder);
                    }else{
                        adHolder = (NendAdNativeViewHolder) convertView.getTag();
                    }
                    mClient.loadAd(adHolder, position);
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
