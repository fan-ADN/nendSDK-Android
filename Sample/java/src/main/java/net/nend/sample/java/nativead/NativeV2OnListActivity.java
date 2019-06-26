package net.nend.sample.java.nativead;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;
import net.nend.sample.java.R;

import java.util.ArrayList;
import java.util.List;

public class NativeV2OnListActivity extends ListActivity {

    private final int NORMAL = 0;
    private final int AD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NendAdNativeClient mClient = new NendAdNativeClient(this, 485516, "16cb170982088d81712e63087061378c71e8aa5c");
        ArrayList<Object> list = new ArrayList<>();

        for (int i = 1; i < 50; i++) {
            final NativeFeed feed = new NativeFeed();

            if (i == 10 || i == 20 || i == 30) {
                feed.setType(AD);
                mClient.loadAd(new NendAdNativeClient.Callback() {
                    @Override
                    public void onSuccess(NendAdNative nendAdNative) {
                        feed.setNendAdNative(nendAdNative);
                    }

                    @Override
                    public void onFailure(NendAdNativeClient.NendError nendError) {
                    }
                });
            } else {
                feed.setType(NORMAL);
                feed.setContent("【" + i + "行目】インフィード広告記事部分長いテキスト　インフィード広告記事部分長いテキスト　インフィード広告記事部分長いテキスト");
                feed.setMediaName("メディア名" + i);
                feed.setDate("1970/01/01");
            }
            list.add(feed);
        }

        NativeListAdapter adapter = new NativeListAdapter(getApplicationContext(), 0, list);
        setListAdapter(adapter);

    }

    private class NativeListAdapter extends ArrayAdapter<Object> {
        private LayoutInflater mInflater;
        private List<Object> mObjects;

        NativeListAdapter(Context context, int resource, List<Object> objects) {
            super(context, resource, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mObjects = objects;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return ((NativeFeed) mObjects.get(position)).getType();
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.native_v2_list_row, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.contentText = (TextView) convertView.findViewById(R.id.content);
                holder.smallText = (TextView) convertView.findViewById(R.id.small_text);
                holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            switch (getItemViewType(position)) {

                case NORMAL:
                    NativeFeed feed = (NativeFeed) getItem(position);
                    assert feed != null;
                    holder.contentText.setText(feed.getContent());
                    holder.smallText.setText(feed.getDate());
                    holder.title.setText(feed.getMediaName());
                    break;

                case AD:
                    final View adRoot = convertView;
                    final NativeFeed adFeed = (NativeFeed) getItem(position);

                    if (adFeed != null && adFeed.getNendAdNative() != null && adFeed.getAdimage() == null) {
                        adFeed.getNendAdNative().downloadAdImage(new NendAdNative.Callback() {
                            @Override
                            public void onSuccess(final Bitmap bitmap) {
                                adFeed.setAdimage(bitmap);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.title.setText(adFeed.getNendAdNative().getTitleText());
                                        holder.contentText.setText(adFeed.getNendAdNative().getContentText());
                                        holder.smallText.setText(NendAdNative.AdvertisingExplicitly.AD.getText());
                                        holder.thumbnail.setImageBitmap(adFeed.getAdimage());

                                        adFeed.getNendAdNative().activate(adRoot, holder.smallText);
                                        adFeed.getNendAdNative().setOnClickListener(new NendAdNative.OnClickListener() {
                                            @Override
                                            public void onClick(NendAdNative ad) {
                                                Log.d("onclick", "" + ad);
                                            }
                                        });
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Exception error) {
                            }
                        });
                    }
                    if (adFeed != null && adFeed.getNendAdNative() != null) {
                        holder.title.setText(adFeed.getNendAdNative().getTitleText());
                        holder.contentText.setText(adFeed.getNendAdNative().getContentText());
                        holder.smallText.setText(NendAdNative.AdvertisingExplicitly.AD.getText());
                        holder.thumbnail.setImageBitmap(adFeed.getAdimage());
                    }
                    break;
            }

            return convertView;
        }

        class ViewHolder {
            TextView title;
            TextView contentText;
            TextView smallText;
            ImageView thumbnail;
        }

    }


    private class NativeFeed {

        private int type;
        private String content;
        private String date;
        private String mediaName;
        private Bitmap adimage;
        private NendAdNative nendAdNative;

        int getType() {
            return type;
        }

        void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        String getDate() {
            return date;
        }

        void setDate(String date) {
            this.date = date;
        }

        String getMediaName() {
            return mediaName;
        }

        void setMediaName(String mediaName) {
            this.mediaName = mediaName;
        }

        Bitmap getAdimage() {
            return adimage;
        }

        void setAdimage(Bitmap adimage) {
            this.adimage = adimage;
        }

        public NendAdNative getNendAdNative() {
            return nendAdNative;
        }

        public void setNendAdNative(NendAdNative nendAdNative) {
            this.nendAdNative = nendAdNative;
        }
    }
}
