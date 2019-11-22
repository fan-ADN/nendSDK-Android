package net.nend.sample.java.nativead;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.android.NendAdNativeViewBinder;
import net.nend.android.NendAdNativeViewHolder;
import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NativeGridActivity extends AppCompatActivity {

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
        setContentView(R.layout.native_grid);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            list.add("item" + i);
        }

        GridView gridView = (GridView) findViewById(R.id.grid);
        NativeGridAdapter adapter = new NativeGridAdapter(this, 0, list);
        assert gridView != null;
        gridView.setAdapter(adapter);
    }

    class NativeGridAdapter extends ArrayAdapter<String> {

        private NendAdNativeClient mClient;
        private NendAdNativeViewBinder mBinder;

        public NativeGridAdapter(Context context, int resource, List<String> list) {
            super(context, resource, list);

            mBinder = new NendAdNativeViewBinder.Builder()
                    .adImageId(R.id.ad_image)
                    .titleId(R.id.ad_title)
                    .prId(R.id.ad_pr, NendAdNative.AdvertisingExplicitly.SPONSORED)
                    .build();

            mClient = new NendAdNativeClient(context, 485516, "16cb170982088d81712e63087061378c71e8aa5c");
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return (position != 0 && position % 5 == 0) ? AD : NORMAL;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final NendAdNativeViewHolder adHolder;
            switch (getItemViewType(position)) {
                case NORMAL:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.native_grid_row, parent, false);
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
                case AD:
                    if (mLoadedAd.containsKey(position)) {
                        adHolder = (NendAdNativeViewHolder) convertView.getTag();
                        mLoadedAd.get(position).intoView(adHolder);
                        break;

                    } else {
                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.native_ad_grid_row, parent, false);
                        adHolder = mBinder.createViewHolder(convertView);
                        convertView.setTag(adHolder);
                    }

                    mClient.loadAd(new NendAdNativeClient.Callback() {
                        @Override
                        public void onSuccess(final NendAdNative nendAdNative) {
                            Log.i(TAG, "広告取得成功");
                            mLoadedAd.put(position, nendAdNative);
                            mPositionList.add(position);
                            mLoadedAd.get(position).intoView(adHolder);
                            mLoadedAd.get(position).setOnClickListener(new NendAdNative.OnClickListener() {
                                @Override
                                public void onClick(NendAdNative nendAdNative) {
                                    Log.i(TAG, "クリック");
                                }
                            });
                        }

                        @Override
                        public void onFailure(NendAdNativeClient.NendError nendError) {
                            Log.i(TAG, "広告取得失敗 " + nendError.getMessage());
                            // すでに取得済みの広告がればランダムで表示
                            if (!mLoadedAd.isEmpty()) {
                                Collections.shuffle(mPositionList);
                                mLoadedAd.get(mPositionList.get(0)).intoView(adHolder);
                            }
                        }
                    });
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
