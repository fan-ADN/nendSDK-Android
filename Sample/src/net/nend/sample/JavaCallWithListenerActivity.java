package net.nend.sample;

import net.nend.android.NendAdListener;
import net.nend.android.NendAdView;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class JavaCallWithListenerActivity extends Activity implements NendAdListener { // 1) implements


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.javacall_relative);
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.root);

        /*
         * NendAdView(Context context, int [Spotid], String [Apikey])
         */
        NendAdView nendAdView = 
                new NendAdView(getApplicationContext(), 3174, "c5cb8bc474345961c6e7a9778c947957ed8e1e4f");
        // 2) リスナーを登録
        nendAdView.setListener(this);
        // 中央上部表示の場合
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        rootLayout.addView(nendAdView, params);

        nendAdView.loadAd();
    }

    // 3)  通知を受けるメソッドを用意

    /** 受信エラー通知 */
    @Override
    public void onFailedToReceiveAd(NendAdView nendAdView) {
        Toast.makeText(getApplicationContext(), "onFailedToReceiveAd", Toast.LENGTH_LONG).show();
    }

    /** 受信成功通知 */
    @Override
    public void onReceiveAd(NendAdView nendAdView) {
        Toast.makeText(getApplicationContext(), "onReceiveAd", Toast.LENGTH_LONG).show();
    }

    /** クリック通知 */
    @Override
    public void onClick(NendAdView nendAdView) {
        Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_LONG).show();
    }

    /** 復帰通知 */
    @Override
    public void onDismissScreen(NendAdView arg0) {
        Toast.makeText(getApplicationContext(), "onDismissScreen", Toast.LENGTH_LONG).show();
    }
}
