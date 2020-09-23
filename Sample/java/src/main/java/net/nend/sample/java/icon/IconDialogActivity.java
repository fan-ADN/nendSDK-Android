package net.nend.sample.java.icon;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import net.nend.android.NendAdIconLoader;
import net.nend.android.NendAdIconView;
import net.nend.sample.java.R;
import net.nend.sample.java.SampleConstants;

import java.util.Objects;

public class IconDialogActivity extends AppCompatActivity implements SampleConstants {

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

        NendAdIconLoader mIconLoader;
        Context mContext;

        public NendDialog(Context context) {
            super(context);
            mContext = context;
            Objects.requireNonNull(getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.icon_dialog);

            NendAdIconView view1 = findViewById(R.id.icon1);
            NendAdIconView view2 = findViewById(R.id.icon2);
            NendAdIconView view3 = findViewById(R.id.icon3);
            NendAdIconView view4 = findViewById(R.id.icon4);
            
            mIconLoader = new NendAdIconLoader(getApplicationContext(), ICON_SPOT_ID, ICON_API_KEY);
            mIconLoader.addIconView(view1);
            mIconLoader.addIconView(view2);
            mIconLoader.addIconView(view3);
            mIconLoader.addIconView(view4);
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            mIconLoader.loadAd();
        }

        @Override
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            mIconLoader.pause();
        }

        public void destroy(){
            dismiss();
        }
    }
}
