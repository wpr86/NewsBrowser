package com.tj.newsbrowser.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;

import com.tj.newsbrowser.R;
import com.tj.newsbrowser.browser.BrowserActivity;

/**
 * Created by Host-0 on 2017/4/7.
 */

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private Context mContext;
    private final String BASE_URL = "https://m.so.com/s?q={searchTerms}&src=home&srcg=ff_tongjue_query_1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;
        initView();
    }

    private void initView() {
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSubmitButtonEnabled(true);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                searchView.onActionViewExpanded();
            }
        }, 500);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    String url = BASE_URL.replace("{searchTerms}", query);
                    Intent intent = new Intent(SearchActivity.this, BrowserActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "搜索");
                    mContext.startActivity(intent);
                    SearchActivity.this.finish();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
