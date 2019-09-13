package com.example.lestradde.vkapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;

import java.util.Date;

import static com.example.lestradde.vkapp.my.PageFragment.adder;
import static com.example.lestradde.vkapp.my.PageFragment.threadRequest;

/**
 * Created by lestradde on 11.09.18.
 */

public class NewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_listview);
        final String[] next = new String[1];
        final NewsAdapter[] newsad = new NewsAdapter[1];
        final ListView list = (ListView) findViewById(R.id.newsListView);
        VKRequest requestNews = new VKRequest("newsfeed.get", VKParameters.from("count",20, "fields", VKParameters.from(VKApiConst.FIELDS, "photo_50")));
        VKRequest [] del = new VKRequest[1];
        del[0] = requestNews;
        adder.addItem(del, NewsActivity.this,list, "newsfeed.get");
        adder.starter(threadRequest);
        //System.out.println(new Date().getTime());
        /*requestNews.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                final int count = 1;
                super.onComplete(response);
                                try {
                                    list.setAdapter(newsad[0] = new NewsAdapter(NewsActivity.this, response.json.getJSONObject("response")));
                                    next[0] = response.json.getJSONObject("response").get("next_from").toString();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                list.setOnScrollListener(new AbsListView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                                    }

                                    @Override
                                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                        if(firstVisibleItem >= totalItemCount/count/4){
                                            VKRequest requestNews = new VKRequest("newsfeed.get", VKParameters.from("start_from", "count", 5 ));
                                            requestNews.executeWithListener(new VKRequest.VKRequestListener() {
                                                @Override
                                                public void onComplete(VKResponse response) {
                                                    super.onComplete(response);
                                                    //response.json.getJSONObject("response").getJSONArray("items").put()
                                                    try {
                                                        next[0] = response.json.getJSONObject("response").get("next_from").toString();
                                                        //System.out.println(response.json.getJSONObject("response").get("next_from").toString());
                                                        for (int i = 0; i< response.json.getJSONObject("response").getJSONArray("items").length();i++) {
                                                            newsad[0].add(response.json.getJSONObject("response").getJSONArray("items").getJSONObject(i));
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
            }
        });*/
    }
}
