package com.example.lestradde.vkapp.vkRequestDifferential;

import android.content.Context;
import android.graphics.Bitmap;
import android.service.wallpaper.WallpaperService;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lestradde.vkapp.MainAdapter;
import com.example.lestradde.vkapp.NewsAdapter;
import com.example.lestradde.vkapp.R;
import com.example.lestradde.vkapp.messages.MessagesRecyclerAdapter;
import com.example.lestradde.vkapp.my.DownloaderAdapter;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import static com.example.lestradde.vkapp.my.PageFragment.adder;
import static com.example.lestradde.vkapp.my.PageFragment.threadRequest;
import static com.vk.sdk.VKUIHelper.getApplicationContext;

/**
 * Created by lestradde on 25.10.18.
 */

public class Rea {
    VKRequest[] vkRequests = new VKRequest[10];
    VKRequest request = null;
    final String[] next = new String[2];

    MessagesRecyclerAdapter meSSS;
    int messageCounter = 0;
    ArrayList<JSONObject> messageObject = new ArrayList<>();

    NewsAdapter newsAdapter;
    int newsCounter = 0;
    ArrayList<JSONObject> newsObject = new ArrayList<>();

    int count = 0;
    long allCount = 0;
    long between;
    long date;
    Server myServer;
    Adder ad;
    ArrayList<Adder.Obj> ar = new ArrayList<>();
    public Rea(Adder ad){
        this.ad = ad;
        Server serv = new Server();
        myServer = serv;
    }
    public synchronized void request(){
        ar = ad.getItem();
        while (ar.size() !=0){
            System.out.println(ar.size());
            if (count == 0) {
                date = new Date().getTime();
                count = 1;
            }
            between = new Date().getTime();
            if(between - date < 350){
                try {
                    System.out.println("time = " + (between - date) + "SLEEP = " + (400 - (between - date)));
                    Thread.sleep(350 -(between - date));
                    date = new Date().getTime();
                    //count = 3;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("SLEEP прерван");
                }
            }

            /*if (count == 0){
                date = new Date().getTime();
                count +=1;
                allCount+=1;
            }
            between = new Date().getTime();
            System.out.println("between = " + between + " | date = " + date + " | diff = " + (between - date) + " | count = " + count );
            if(ar.size()>=20){
                try {
                    System.out.println("REQ>20 SLEEP");
                    Thread.sleep(1100-(between - date));
                    date = new Date().getTime();
                    count = 3;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("SLEEP прерван");
                }
            }
            else if (count == 4 & (between - date) < 1100){
                System.out.println(between - date + "   Pause = " + (1100-(between - date)) +"  " + new Date().getTime() + "count = " + ar.size() );
                //count = 0;
                try {
                    Thread.sleep(1100-(between - date));
                    date = new Date().getTime();
                    count =1;
                    //allCount+=1;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("SLEEP прерван");
                }
            }else if (count == 4){
                count = 1;
                    date = new Date().getTime();
            }*/
            Adder.Obj r [] = ar.toArray(new Adder.Obj[ar.size()]);
            //Object [] r = ar.toArray(new Object[ar.size()]);
            //Integer [] r = ar.toArray(new Integer[ar.size()]);
            if (r.length != 0){
                VKRequest []requests = new VKRequest[r.length];
                final Context[]contexts =new Context[r.length];
                final ListView[]listViews =new ListView[r.length];
                final String [] nameAdapters = new String[r.length];
                for (int i = 0; i < r.length; i++){
                    requests[i] = r[i].request;
                    contexts[i] = r[i].context;
                    listViews[i] = r[i].listView;
                    nameAdapters[i] = r[i].nameAdapter;
                }

                //VKRequest reqWall = new VKRequest("wall.get");
                //VKRequest reqPhotos = new VKRequest("photos.getAll", VKParameters.from("count", 21));
                //VKRequest reqUserInfo = new VKRequest("users.get", VKParameters.from(VKApiConst.FIELDS, "status,counters,city,photo_200_orig"));
                //VKBatchRequest batch = new VKBatchRequest(reqUserInfo,reqPhotos,reqWall);
                final VKBatchRequest batch = new VKBatchRequest(requests);
                System.out.println("REQUEST " + new Date().getTime() + " " + batch.toString());
                batch.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
                    @Override
                    public void onComplete(final VKResponse[] response) {
                        super.onComplete(response);
                        int countMainadapter = 0;
                        VKResponse[] responses = new VKResponse[3];
                        for (int i = 0; i < response.length; i++){
                            if(nameAdapters[i] == "MainAdapter"){
                                responses[countMainadapter] = response[i];
                                countMainadapter+=1;
                            }
                            if (nameAdapters[i] == "MainAdapter" & countMainadapter == 3){
                                countMainadapter = 0;
                                String str = null;
                                try {
                                    //System.out.println("RESP " + response[1].json.toString());
                                    str = "[" + responses[0].json.getJSONArray("response").getJSONObject(0).toString() + "," + responses[1].json.getJSONObject("response").toString();
                                    for (int j = 0; j < responses[2].json.getJSONObject("response").getJSONArray("items").length(); j++) {
                                        str += "," + responses[2].json.getJSONObject("response").getJSONArray("items").getJSONObject(j).toString();
                                    }
                                    str += "]";
                                    //System.out.println(str);
                                    //listView.setAdapter(new MainAdapter(getActivity().getApplicationContext(), new JSONArray[]{response[0].json.getJSONArray("response"),response[1].json.getJSONArray("response"),response[2].json.getJSONObject("response").getJSONArray("items"), new JSONArray(response[3].json.getJSONObject("response"))}));
                                    RecyclerView re = null;
                                    listViews[i].setAdapter(new MainAdapter(contexts[i], str, re));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (nameAdapters[i] == "MessagesRecyclerAdapter"){

                                VKRequest[] vkRequests = new VKRequest[20];
                                VKRequest request;
                                try {
                                    meSSS = new MessagesRecyclerAdapter(contexts[i],response[i].json.getJSONObject("response").getJSONArray("items"));
                                    listViews[i].setAdapter(meSSS);
                                    System.out.println(response[i].json.getJSONObject("response").getJSONArray("items"));
                                    for (int p = 0; p < response[i].json.getJSONObject("response").getJSONArray("items").length(); p++){
                                        request = new VKRequest("users.get", VKParameters.from("user_ids", response[i].json.getJSONObject("response").getJSONArray("items").getJSONObject(p).getJSONObject("conversation").getJSONObject("peer").getString("id"), "fields", "photo_50"));
                                        vkRequests[p] = request;
                                    }
                                    adder.addItem(vkRequests,contexts[i],listViews[i], "MessagesRecyclerAdapterAdvance");
                                    adder.starter(threadRequest);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            if (nameAdapters[i] == "MessagesRecyclerAdapterAdvance"){
                                if (messageCounter == 19){
                                    messageObject.add(response[i].json);
                                    meSSS.addUserObj(messageObject);
                                }else {
                                    messageObject.add(response[i].json);
                                    messageCounter +=1;
                                }
                                //System.out.println("responseSSSSSS");
                            }
                            if(nameAdapters[i] =="newsfeed.get"){

                                try {
                                    next[0] = response[i].json.getJSONObject("response").get("next_from").toString();
                                    try {
                                        listViews[i].getAdapter().isEmpty();
                                        System.out.println("ВЫЗОВ");
                                        for (int z = 0; z < response[i].json.getJSONObject("response").getJSONArray("items").length(); z++) {
                                            JSONObject ob =null;
                                                if(response[i].json.getJSONObject("response").getJSONArray("items").getJSONObject(z).getInt("source_id") > 0){
                                                    for (int g = 0; g < response[i].json.getJSONObject("response").getJSONArray("profiles").length(); g++){
                                                        if (response[i].json.getJSONObject("response").getJSONArray("items").getJSONObject(z).getInt("source_id")
                                                                == response[i].json.getJSONObject("response").getJSONArray("profiles").getJSONObject(g).getInt("id")){
                                                            ob = response[i].json.getJSONObject("response").getJSONArray("profiles").getJSONObject(g);
                                                            break;
                                                        }
                                                        if (g == response[i].json.getJSONObject("response").getJSONArray("profiles").length()-1)
                                                            ob = new JSONObject();
                                                    }
                                                }
                                                else{
                                                    for (int g = 0; g < response[i].json.getJSONObject("response").getJSONArray("groups").length(); g++){
                                                        if (response[i].json.getJSONObject("response").getJSONArray("items").getJSONObject(z).getInt("source_id")
                                                                == Math.abs(response[i].json.getJSONObject("response").getJSONArray("groups").getJSONObject(g).getInt("id"))){
                                                            ob = response[i].json.getJSONObject("response").getJSONArray("groups").getJSONObject(g);
                                                            break;
                                                        }
                                                        if (g == response[i].json.getJSONObject("response").getJSONArray("groups").length()-1)
                                                            ob = new JSONObject();
                                                    }
                                                }
                                            newsAdapter.add(response[i].json.getJSONObject("response").getJSONArray("items").getJSONObject(z), ob);
                                        }
                                    }catch (Exception e){
                                        JSONArray ar = new JSONArray();
                                        for (int v = 0; v < response[i].json.getJSONObject("response").getJSONArray("items").length(); v++){
                                            if(response[i].json.getJSONObject("response").getJSONArray("items").getJSONObject(v).getInt("source_id") > 0){
                                                for (int g = 0; g < response[i].json.getJSONObject("response").getJSONArray("profiles").length(); g++){
                                                    if (response[i].json.getJSONObject("response").getJSONArray("items").getJSONObject(v).getInt("source_id")
                                                            == response[i].json.getJSONObject("response").getJSONArray("profiles").getJSONObject(g).getInt("id")){
                                                        ar.put(response[i].json.getJSONObject("response").getJSONArray("profiles").getJSONObject(g));
                                                        System.out.println("What a fuck  " + response[i].json.getJSONObject("response").getJSONArray("profiles").getJSONObject(g)+" " + response[i].json.getJSONObject("response").getJSONArray("items").length());
                                                        break;
                                                    }
                                                    /*if (g == response[i].json.getJSONObject("response").getJSONArray("profiles").length()-1)
                                                        ar.put(new JSONObject("{\"a\":\"e\"}"));*/
                                                }
                                            }
                                            else{
                                                for (int g = 0; g < response[i].json.getJSONObject("response").getJSONArray("groups").length(); g++){if (Math.abs(response[i].json.getJSONObject("response").getJSONArray("items").getJSONObject(v).getInt("source_id"))
                                                            == response[i].json.getJSONObject("response").getJSONArray("groups").getJSONObject(g).getInt("id")){
                                                        ar.put(response[i].json.getJSONObject("response").getJSONArray("groups").getJSONObject(g));
                                                        break;
                                                    }
                                                    /*if (g == response[i].json.getJSONObject("response").getJSONArray("groups").length()-1)
                                                        ar.put(new JSONObject("{\"a\":\"e\"}"));*/
                                                }
                                            }
                                            System.out.println("после break");
                                        }
                                        next[0] = "q";
                                        System.out.println("ar.length() = " + ar.length());
                                        newsAdapter = new NewsAdapter(contexts[i], response[i].json.getJSONObject("response"), ar);
                                        listViews[i].setAdapter(newsAdapter);
                                        final ListView list = listViews[i];
                                        final Context cont = contexts[i];
                                        listViews[i].setOnScrollListener(new AbsListView.OnScrollListener() {
                                            @Override
                                            public void onScrollStateChanged(AbsListView view, int scrollState) {

                                            }

                                            @Override
                                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                                if (totalItemCount -(firstVisibleItem+visibleItemCount-1) <= visibleItemCount*3 & !next[0].equals(next[1])) {
                                                    System.out.println("start from = " + next[0]);
                                                    VKRequest requestNews = new VKRequest("newsfeed.get", VKParameters.from("start_from", next[0], "count", 20, "fields", VKParameters.from(VKApiConst.FIELDS, "photo_50")));
                                                    next[1] = next[0];
                                                    VKRequest [] del = new VKRequest[1];
                                                    del[0] = requestNews;
                                                    adder.addItem(del,cont,list, "newsfeed.get");
                                                    adder.starter(threadRequest);
                                                    /*requestNews.executeWithListener(new VKRequest.VKRequestListener() {
                                                        @Override
                                                        public void onComplete(VKResponse response) {
                                                            super.onComplete(response);
                                                            try {
                                                                next[0] = response.json.getJSONObject("response").get("next_from").toString();
                                                                for (int i = 0; i < response.json.getJSONObject("response").getJSONArray("items").length(); i++) {
                                                                    newsAdapter.add(response.json.getJSONObject("response").getJSONArray("items").getJSONObject(i));
                                                                    System.out.println(response.json.getJSONObject("response").get("next_from").toString());
                                                                    System.out.println("ad resp = " + response.json.getJSONObject("response").getJSONArray("items").getJSONObject(i));
                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    });*/
                                                }else System.out.println("ERROR SCROLL " + (totalItemCount -(firstVisibleItem+visibleItemCount-1) <= visibleItemCount*3) +" " + !next[0].equals(next[1]));
                                            }
                                        });
                                    }
                                    /*JSONArray items = response[i].json.getJSONObject("response").getJSONArray("items");
                                    for (int p = 0; p < items.length(); p++){
                                        switch (items.getJSONObject(p).getJSONArray("attachments").getJSONObject(0).getString("type")) {
                                            case "photo":
                                                System.out.println("This is object / " + items.getJSONObject(p).toString());
                                                if (items.getJSONObject(p).getInt("source_id") > 0){
                                                    request = VKApi.users().get(VKParameters.from("user_ids", items.getJSONObject(p).getInt("source_id")));
                                                }else {
                                                    request = VKApi.groups().getById(VKParameters.from("group_ids", Math.abs(items.getJSONObject(p).getInt("source_id"))));
                                                }
                                        }
                                        vkRequests[p] = request;
                                    }
                                    adder.addItem(vkRequests,contexts[i],listViews[i], "newsfeed.getAdvance");
                                    adder.starter(threadRequest);*/
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            /*if(nameAdapters[i] =="newsfeed.getAdvance"){
                                if (newsCounter == 19){
                                    newsObject.add(response[i].json);
                                    newsAdapter.addUserObj(messageObject);
                                }else {
                                    newsObject.add(response[i].json);
                                    newsCounter +=1;
                                }
                            }*/
                            }
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                        Toast.makeText(getApplicationContext(), "CODE 6", Toast.LENGTH_SHORT).show();
                        System.out.println(error + " ошибка " + count + " ms " + (between-date) + " total " + allCount+ "  "+batch.toString());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            ar.clear();
            ar = ad.getItem();
            //count+=1;
            allCount+=1;
        }
    }
    public class SetMessage{
        TextView jsonText;
        ImageView imageView;
    }
}
