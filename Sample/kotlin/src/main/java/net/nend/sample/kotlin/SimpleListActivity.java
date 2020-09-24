package net.nend.sample.kotlin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;

import java.util.List;
import java.util.Random;

public class SimpleListActivity extends AppCompatActivity {
    private ListFragment listFragment;

    protected void instantiateMenuListFragment(int resId, List<Class<?>> menus) {
        int id = new Random().nextInt(0xFFFF);
        FrameLayout container = new FrameLayout(this);
        container.setId(id);
        setContentView(container, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        getSupportFragmentManager()
                .beginTransaction()
                .add(id, new SimpleMenuListFragment(resId, menus))
                .commit();
    }

    protected void instantiateListAdapter(ArrayAdapter adapter) {
        listFragment = new ListFragment();
        listFragment.setListAdapter(adapter);

        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, listFragment, "list_fragment")
                .commit();
    }

    @Nullable
    protected ListView getListView() {
        return (listFragment != null ? listFragment.getListView() : null);
    }

    public static class SimpleMenuListFragment extends ListFragment {
        private final List<Class<?>> menus;
        private final int resId;

        public SimpleMenuListFragment(int resId, List<Class<?>> menus) {
            this.resId = resId;
            this.menus = menus;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            return inflater.inflate(resId, container, false);
        }

        @Override
        public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
            Intent intent = new Intent(getActivity(), menus.get(position));
            startActivity(intent);
        }
    }
}