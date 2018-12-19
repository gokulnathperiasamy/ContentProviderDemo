package com.kpgn.appthree;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.kpgn.common.ApplicationConstant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.swipe_refresh_container)
    SwipeRefreshLayout mSwipeRefreshContainer;

    @BindView(R.id.tv_email_list)
    TextView mEmailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupSwipeRefreshAction();
    }

    private void setupSwipeRefreshAction() {
        mSwipeRefreshContainer.setColorScheme(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        mSwipeRefreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        getLoaderManager().initLoader(1, null, this);
        mSwipeRefreshContainer.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshData();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Uri.parse(ApplicationConstant.PROVIDER_URL), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            cursor.moveToFirst();
            StringBuilder res = new StringBuilder();
            while (!cursor.isAfterLast()) {
                res.append(cursor.getString(cursor.getColumnIndex(ApplicationConstant.COLUMN_EMAIL)));
                res.append("\n");
                cursor.moveToNext();
            }
            mEmailList.setText(res);
        } else {
            Toast.makeText(getBaseContext(), "Data Not Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
