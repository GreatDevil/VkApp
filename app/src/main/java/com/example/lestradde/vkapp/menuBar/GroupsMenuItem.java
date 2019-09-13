package com.example.lestradde.vkapp.menuBar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

//import com.example.lestradde.vkapp.MessageRead;
import com.example.lestradde.vkapp.NewsAdapter;
import com.example.lestradde.vkapp.R;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiMessage;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by lestradde on 19.09.18.
 */

public class GroupsMenuItem extends AppCompatActivity {
    private ListView listView;
    private ListView listViewLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_listview);
        final String[] next = new String[1];
        final NewsAdapter[] newsad = new NewsAdapter[1];
        //final ListView list = (ListView) findViewById(R.id.newsListView);
        VKRequest requestNews = VKApi.groups().get(VKParameters.from("extended",1));
        //VKRequest requestNews = new VKRequest("groups.get");
        //System.out.println(new Date().getTime());
        /*requestNews.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(final VKResponse response) {
                super.onComplete(response);
                //final TextView textView = (TextView) findViewById(R.id.textView);
                final VKList vkList = (VKList) response.parsedModel;
                listView = (ListView) findViewById(R.id.newsListView);
                //listViewLayout = (ListView)findViewById(R.id.listViewLayout);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GroupsMenuItem.this, android.R.layout.simple_expandable_list_item_1, vkList);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    String userId;
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            userId = response.json.getJSONObject("response").getJSONArray("items").getJSONObject(position).getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(userId);
                        //VKRequest requestMessage = new VKRequest("groups.getById",VKParameters.from("group_id",userId, VKApiConst.COUNT,100));
                        VKRequest requestMessage = new VKRequest("wall.get",VKParameters.from("owner_id","-" + userId, VKApiConst.COUNT,100));
                        requestMessage.executeWithListener(new VKRequest.VKRequestListener() {
                            VKApiMessage mes;
                            @Override
                            public void onComplete(VKResponse response) {
                                super.onComplete(response);
                                try {
                                    //JSONArray array = response.json.getJSONObject("response").getJSONArray("items");
                                    listView.setAdapter(new NewsAdapter(GroupsMenuItem.this, response.json.getJSONObject("response"), new JSONArray()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(response.toString());
                            }

                            @Override
                            public void onError(VKError error) {
                                super.onError(error);
                                System.out.println(error+"");
                            }
                        });
                    }
                });
            }
        });*/
        /*requestNews.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                final int count = 1;
                super.onComplete(response);
                try {
                    listView.setAdapter(newsad[0] = new NewsAdapter(GroupsMenuItem.this, response.json.getJSONObject("response")));
                    next[0] = response.json.getJSONObject("response").get("next_from").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
