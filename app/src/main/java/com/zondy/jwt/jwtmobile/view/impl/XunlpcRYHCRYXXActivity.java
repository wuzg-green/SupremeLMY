package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityXunlpcJDCXX;

import butterknife.BindView;

/**
 * Created by sheep on 2017/3/14.
 */

public class XunlpcRYHCRYXXActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_xunlpc_ryhcryxx;
    }
    public static void actionStart(Context context,EntityXunlpcJDCXX entityXunlpcJDCXX) {
        Intent intent = new Intent(context, XunlpcRYHCRYXXActivity.class);
        intent.putExtra("JDCXX",entityXunlpcJDCXX);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams();
        initViews();
    }

    private void initParams() {

    }

    private void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}