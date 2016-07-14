package net.nend.sample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.nend.android.NendAdNative;
import net.nend.android.NendAdNativeClient;

/**
 * 広告オブジェクトから広告の各データを取得しレイアウトする、最もシンプルな実装方法
 */
public class NativeGetAdDataActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private RelativeLayout mAdContainer;
    private ImageView mAdImage;
    private ImageView mLogoImage;
    private TextView mTitleText;
    private TextView mPrText;
    private TextView mContentText;
    private TextView mActionButtonText;
    private TextView mPromotionName;
    private TextView mPromotionUrl;

    private TextView mAdImageUrl;
    private TextView mLogoImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_get_ad_data);

        mAdContainer = (RelativeLayout) findViewById(R.id.ad_container);
        mAdImage = (ImageView) findViewById(R.id.ad_image);
        mLogoImage = (ImageView) findViewById(R.id.logo_image);
        mTitleText = (TextView) findViewById(R.id.title_text);
        mPrText = (TextView) findViewById(R.id.pr_text);
        mContentText = (TextView) findViewById(R.id.content_text);
        mActionButtonText = (TextView) findViewById(R.id.action_button);
        mPromotionName = (TextView) findViewById(R.id.promotion_name);
        mPromotionUrl = (TextView) findViewById(R.id.promotion_url);
        mAdImageUrl = (TextView) findViewById(R.id.ad_image_url);
        mLogoImageUrl = (TextView) findViewById(R.id.logo_image_url);

        // NendAdNativeClient インスタンス生成
        NendAdNativeClient client = new NendAdNativeClient(this, 485520, "a88c0bcaa2646c4ef8b2b656fd38d6785762f2ff");
        // 広告オブジェクトのロード
        client.loadAd(new NendAdNativeClient.Callback() {
            @Override
            public void onSuccess(NendAdNative nendAdNative) {
                Log.i(TAG, "広告取得成功");

                // 広告オブジェクトからテキストを取得する
                mTitleText.setText(nendAdNative.getTitleText());
                mPrText.setText(NendAdNative.AdvertisingExplicitly.PR.getText());
                mContentText.setText(nendAdNative.getContentText());
                mActionButtonText.setText(nendAdNative.getActionText());
                mPromotionName.setText(nendAdNative.getPromotionName());
                mPromotionUrl.setText(nendAdNative.getPromotionUrl());

                // 広告オブジェクトから広告画像を取得する
                nendAdNative.downloadAdImage(new NendAdNative.Callback() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        Log.i(TAG, "広告画像取得成功");
                        mAdImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.i(TAG, "広告画像取得失敗:" + e.getMessage());
                    }
                });

                // 広告オブジェクトからロゴ画像を取得する
                nendAdNative.downloadLogoImage(new NendAdNative.Callback() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        Log.i(TAG, "ロゴ取得成功");
                        mLogoImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.i(TAG, "ロゴ画像取得失敗:" + e.getMessage());
                    }
                });

                // 広告をアクティブにする
                nendAdNative.activate(mAdContainer, mPrText);

                // 広告オブジェクトから広告画像、ロゴ画像のDL用URLも取得可能
                mAdImageUrl.setText(nendAdNative.getAdImageUrl());
                mLogoImageUrl.setText(nendAdNative.getLogoImageUrl());
            }

            @Override
            public void onFailure(NendAdNativeClient.NendError nendError) {
                Log.i(TAG, "広告取得失敗:" + nendError.getMessage());
            }
        });

    }
}
