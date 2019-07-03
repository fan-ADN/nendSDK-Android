package net.nend.sample.java.banner;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import net.nend.android.NendAdView;
import net.nend.sample.java.R;

public class DialogActivity extends AppCompatActivity {

    NendDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mDialog = new NendDialog(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDialog.destroy();
    }

    public void onClick(View view){
        mDialog.show();
    }
    
    class NendDialog extends Dialog {

        NendAdView mNendAdView;
        Context mContext;

        public NendDialog(Context context) {
            super(context);
            mContext = context;
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog);

            mNendAdView = (NendAdView) findViewById(R.id.nend);
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            mNendAdView.loadAd();
        }

        @Override
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            mNendAdView.pause();
        }

        public void destroy(){
            dismiss();
        }
    }
}
