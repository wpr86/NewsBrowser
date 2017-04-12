package com.tj.newsbrowser;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.qihoo360.newssdk.exportui.NewsEmbedListView;
import com.tj.newsbrowser.browser.BrowserActivity;
import com.tj.newsbrowser.search.SearchActivity;
import com.tj.newsbrowser.utils.DensityUtil;
import com.tj.newsbrowser.view.NewsScrollView;

public class MainActivity extends AppCompatActivity implements NewsScrollView.ScrollPositionCallback, View.OnClickListener {
    private FloatingActionButton mFAB;
    private NewsEmbedListView mContentView;
    private NewsScrollView mNewsScroll;
    private RelativeLayout searchRl;

    private ImageView toobarSearch;

    private RelativeLayout navigationRl;
    private RelativeLayout videoRl;
    private RelativeLayout readRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initView();
    }

    private void initView() {
        searchRl = (RelativeLayout) findViewById(R.id.search_rl);
        searchRl.setOnClickListener(this);

        navigationRl = (RelativeLayout) findViewById(R.id.navigation_rl);
        navigationRl.setOnClickListener(this);
        videoRl = (RelativeLayout) findViewById(R.id.video_rl);
        videoRl.setOnClickListener(this);
        readRl = (RelativeLayout) findViewById(R.id.read_rl);
        readRl.setOnClickListener(this);

        mContentView = (NewsEmbedListView) findViewById(R.id.embed_list_view);
        mContentView.callOnFocus(true);
        mContentView.callOnCreate();

        mNewsScroll = (NewsScrollView) findViewById(R.id.portal_root_scroll);
        mNewsScroll.setScrollPositionCallback(this);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        layoutParams.height = DensityUtil.getScreenHeight(this) - DensityUtil.dip2px(this, 56) - DensityUtil.getStatusBarHeight();
        layoutParams.width = DensityUtil.getScreenWidth(this);
        mContentView.setLayoutParams(layoutParams);

        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        mFAB.setOnClickListener(this);
        mFAB.hide();

        toobarSearch = (ImageView) findViewById(R.id.title_search_image);
        toobarSearch.setClickable(true);
        toobarSearch.setOnClickListener(this);
    }

    @Override
    public void onScrollPosition(int position) {
        if (position > 0) {
            mFAB.show();
            toobarSearch.setVisibility(View.VISIBLE);
        } else {
            mFAB.hide();
            toobarSearch.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                mNewsScroll.fullScroll(ScrollView.FOCUS_UP);
                break;
            case R.id.search_rl:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.navigation_rl:
                toNavigation();
                break;
            case R.id.video_rl:
                toVideo();
                break;
            case R.id.read_rl:
                toRead();
                break;
            case R.id.title_search_image:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            default:
                break;
        }
    }

    private void toRead() {
        Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
        intent.putExtra("url", "http://api.reader.m.so.com/mbook/index.php?m=MBook&c=Index&a=recommend&src=sohome");
        intent.putExtra("title", "小说");
        startActivity(intent);
    }

    private void toVideo() {
        Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
        intent.putExtra("url", "http://m.video.so.com/?src=m_home");
        intent.putExtra("title", "视频");
        startActivity(intent);
    }

    private void toNavigation() {
        Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
        intent.putExtra("url", "http://h5.mse.360.cn/");
        intent.putExtra("title", "导航");
        startActivity(intent);
    }
}
