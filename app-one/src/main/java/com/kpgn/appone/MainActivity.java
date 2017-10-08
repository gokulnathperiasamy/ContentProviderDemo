package com.kpgn.appone;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kpgn.common.ApplicationConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_email_id)
    EditText mEmailID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.b_add)
    public void actionAdd(View view) {
        ContentValues values = new ContentValues();
        values.put(ApplicationConstant.COLUMN_EMAIL, mEmailID.getText().toString());
        Uri uri = getContentResolver().insert(CustomContentProvider.CONTENT_URI, values);
        mEmailID.setText("");
        Toast.makeText(getBaseContext(), "Email Added Successfully!", Toast.LENGTH_LONG).show();
    }
}
