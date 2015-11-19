package net.nend.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Fragment切り替えサンプル
 */
public class FragmentReplaceActivity extends FragmentActivity implements OnClickListener{
    
    int count = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.layout, new FirstFragment());
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        
        Fragment fragment = null;
        switch (count%2) {
        case 0:
            fragment = new SecondFragment();
            break;
        case 1:
            fragment = new FirstFragment();
            break;
        }
        count++;
        
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}
