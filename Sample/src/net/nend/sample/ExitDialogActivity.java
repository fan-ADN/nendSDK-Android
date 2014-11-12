package net.nend.sample;

import net.nend.android.NendAdView;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ExitDialogActivity extends Activity {

    NendDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top);
        mDialog = new NendDialog(this);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDialog.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    
    class NendDialog extends Dialog implements android.view.View.OnClickListener {

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

            setContentView(R.layout.exit_dialog);
            
            Button finishButton = (Button) findViewById(R.id.finish);
            Button cancelButton = (Button) findViewById(R.id.cancel);
            finishButton.setOnClickListener(this);
            cancelButton.setOnClickListener(this);

            mNendAdView = (NendAdView) findViewById(R.id.nend);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
            case R.id.finish:
                destroy();
                finish();
                break;
            case R.id.cancel:
                destroy();
                break;
            }
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
