package com.zondy.jwt.jwtmobile.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tuesda.walker.circlerefresh.CircleRefreshLayout;
import com.zondy.jwt.jwtmobile.R;
import com.zondy.jwt.jwtmobile.base.BaseActivity;
import com.zondy.jwt.jwtmobile.entity.EntityLocation;
import com.zondy.jwt.jwtmobile.global.Constant;
import com.zondy.jwt.jwtmobile.util.MapManager;
import com.zondy.jwt.jwtmobile.util.SharedTool;
import com.zondy.jwt.jwtmobile.util.ToastTool;
import com.zondy.mapgis.android.annotation.Annotation;
import com.zondy.mapgis.android.annotation.AnnotationView;
import com.zondy.mapgis.android.mapview.MapView;
import com.zondy.mapgis.core.geometry.Dot;

import butterknife.BindView;

import static android.R.id.message;
import static com.zondy.jwt.jwtmobile.R.id.mapView;

/**
 * Created by sheep on 2017/3/30.
 */

public class ShujcjFWXXCXJGActivity extends BaseActivity implements View.OnClickListener {
//    @BindView(R.id.list)
//    ListView list;
//    @BindView(R.id.refresh_layout)
//    CircleRefreshLayout refreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mapview)
    MapView mapView;
    MapManager mapManager;

    private LinearLayout llBottomCollapse;
    private ImageView iv;
    private ScrollView scExpanded;
    @Override
    public int setCustomContentViewResourceId() {
        return R.layout.activity_shujcj_fwxxcxjg;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ShujcjFWXXCXJGActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams();
        initBottomSheet();
        initViews();
//        loadWeb();

    }

    @Override
    protected void onResume() {
        super.onResume();
        iv.layout(0,100,1080,700);
        iv.invalidate();
        iv.requestLayout();
    }

    private void initBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        llBottomCollapse= (LinearLayout) findViewById(R.id.ll_bottom_collapse);
        iv= (ImageView) findViewById(R.id.iv_fwxxcxjg);
        scExpanded= (ScrollView) findViewById(R.id.sc_expand);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                String state = "null";
                switch (newState) {
                    case 1:
                        state = "STATE_DRAGGING";
//                        ToastTool.getInstance().shortLength(ShujcjFWXXCXJGActivity.this, "拖动状态jwt", true);
                        break;
                    case 2:
                        state = "STATE_SETTLING";
//                        ToastTool.getInstance().shortLength(ShujcjFWXXCXJGActivity.this, "到达终点状态前的位置jwt", true);
                        break;
                    case 3:
                        state = "STATE_EXPANDED";
//                        llBottomCollapse.setBackground(getDrawable(R.drawable.panda));
//                        iv.setVisibility(View.VISIBLE);
//                        ToastTool.getInstance().shortLength(ShujcjFWXXCXJGActivity.this, "展开模式jwt", true);
                        break;
                    case 4:
                        state = "STATE_COLLAPSED";
                        llBottomCollapse.setVisibility(View.VISIBLE);
                        scExpanded.setVisibility(View.GONE);
//                        ToastTool.getInstance().shortLength(ShujcjFWXXCXJGActivity.this, "折叠模式jwt", true);
//                        iv.setVisibility(View.GONE);
                        break;
                    case 5:
                        state = "STATE_HIDDEN";
//                        ToastTool.getInstance().shortLength(ShujcjFWXXCXJGActivity.this, state, true);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("sheep", "onSlide: "+slideOffset+"-------"+iv.getMeasuredHeight()+"--------"+iv.getHeight()+"------"+iv.getMeasuredWidth()
                +"--------"+iv.getWidth());

                scExpanded.setVisibility(View.VISIBLE);
                llBottomCollapse.setVisibility(View.GONE);
                iv.layout(0,(int)(-(1-slideOffset)*510),1080,(int)(slideOffset*510));
//                int a= (int) ((slideOffset-0.5)*iv.getMeasuredHeight()*2);
//                iv.layout(0,(a-540),iv.getWidth(),a);
//                if(slideOffset!=0){
//                    iv.setVisibility(View.VISIBLE);
//                }else {
//                    iv.setVisibility(View.GONE);
//                }
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }
//
//    private void loadWeb() {
//        String url = "http://54.222.206.74:8081/chat/msg.jsp";
//
//        wvWebview.setWebViewClient(new WebViewClient(
//        ));
//        WebSettings webSettings = wvWebview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        wvWebview.addJavascriptInterface(new message(), "webview");
//        wvWebview.loadUrl(url);
//    }

//    class message {
//
//        @JavascriptInterface
//        public void show(String msg) {
//            ToastTool.getInstance().shortLength(ShujcjFWXXCXJGActivity.this, msg, true);
//        }
//    }


    private void initParams() {
        mapManager = new MapManager(mapView, context);
        mapManager.initMap(Constant.mapPath, new MapManager.MapLoadListner() {
            @Override
            public void onMapLoadSuccess() {
                EntityLocation entityLocation = SharedTool.getInstance().getLocationInfo(context);
                double[] xy = mapManager.lonLat2Mercator(entityLocation.getLongitude(), entityLocation.getLatitude());
                mapView.zoomToCenter(new Dot(xy[0], xy[1]), mapManager.goodResolution, false);
                mapView.refresh();
            }

            @Override
            public void onMapLoadFail() {

            }
        }, new MapManager.MapAnnotationListener() {
            @Override
            public AnnotationView createAnnotationView(Annotation annotation) {
                return null;
            }
        });
//        //测试酷炫水滴动画下拉
//        String[] strs = {
//                "The",
//                "Canvas",
//                "class",
//                "holds",
//                "the",
//                "draw",
//                "calls",
//                ".",
//                "To",
//                "draw",
//                "something,",
//                "you",
//                "need",
//                "4 basic",
//                "components",
//                "Bitmap",
//        };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
//        list.setAdapter(adapter);
//
//
//        refreshLayout.setOnRefreshListener(
//                new CircleRefreshLayout.OnCircleRefreshListener() {
//                    @Override
//                    public void refreshing() {
//
//
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                refreshLayout.finishRefreshing();
//
//                            }
//                        }, 1000);
//                    }
//
//                    @Override
//                    public void completeRefresh() {
//                        // do something when refresh complete
//                        ToastTool.getInstance().shortLength(ShujcjFWXXCXJGActivity.this, "haha", true);
//                    }
//                });
    }

    private void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_ceshi:
//                String s = "test";
//                wvWebview.loadUrl("javascript:sendMsg('test')");
//                break;

        }
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
}
