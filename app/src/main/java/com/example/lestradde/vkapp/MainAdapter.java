package com.example.lestradde.vkapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.util.LruCache;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lestradde.vkapp.my.DownloaderAdapter;
import com.example.lestradde.vkapp.my.RecyclerAdapter;
import com.example.lestradde.vkapp.my.RecyclerNew;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.example.lestradde.vkapp.my.PageFragment.mBundleRecyclerViewState;

/**
 * Created by lestradde on 29.09.18.
 */

public class MainAdapter extends BaseAdapter {
    private final String KEY_RECYCLER_STATE = "recycler_state";

    //
    LruCache<Integer, Bitmap> mMemoryCache;
    RecyclerView recyclerView;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory());
    final int cacheSize = maxMemory / 8;
    Locale local = new Locale("ru","RU");
    DateFormat df = DateFormat.getDateTimeInstance (DateFormat.DEFAULT,DateFormat.DEFAULT,local);
    //

    private Context context;
    private JSONArray jsonArray = null;

    public MainAdapter(Context context, String stringArray,RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
       try {
            this.jsonArray = new JSONArray (stringArray);
            //System.out.println(this.jsonArray.toString());
            //System.out.println("LENGTH   "+jsonArray.length());
        } catch (JSONException e) {
           System.out.println("Ошибка создания массива");
            e.printStackTrace();
        }

        mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(Integer key, Bitmap bitmap) {
                //return super.sizeOf(key, value);
                return bitmap.getByteCount();
            }
        };
    }

    @Override
    public int getCount() {
        try {
            return jsonArray.length();
        }catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View horizontalPhoto = layoutInflater.inflate(R.layout.horizontal_layout, null);
        switch (position) {
            case 0:
                View viePhoto = layoutInflater.inflate(R.layout.image_layout, null);
                ImageView mainImage = viePhoto.findViewById(R.id.imageView2);
                DownloaderAdapter downloaderAdapter = new DownloaderAdapter(mainImage);
                try {
                    downloaderAdapter.execute(jsonArray.getJSONObject(0).getString("photo_200_orig"));
                } catch (JSONException e) {
                    System.out.println("ОШИБКА в Mainadapter");
                    e.printStackTrace();
                }
                return viePhoto;
            case 1:
                convertView = layoutInflater.inflate(R.layout.user_info_layout, null);
                TextView statusView = convertView.findViewById(R.id.textViewStatus);
                TextView friendsView = convertView.findViewById(R.id.textViewCountFriends);
                TextView followersView = convertView.findViewById(R.id.textViewCountFollowers);
                TextView cityView = convertView.findViewById(R.id.textViewCity);
                statusView.setTextColor(Color.BLACK);
                friendsView.setTextColor(Color.BLACK);
                followersView.setTextColor(Color.BLACK);
                cityView.setTextColor(Color.BLACK);
                try {
                    statusView.setText("Статус: " + jsonArray.getJSONObject(0).getString("status"));
                    friendsView.setText("Друзья " + jsonArray.getJSONObject(0).getJSONObject("counters").getString("friends"));
                    followersView.setText("Подписчики " + jsonArray.getJSONObject(0).getJSONObject("counters").getString("followers"));
                    cityView.setText("Город " + jsonArray.getJSONObject(0).getString("city"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return convertView;
            case 2:
                convertView = layoutInflater.inflate(R.layout.recycler_hor, null);
                recyclerView = convertView.findViewById(R.id.hor);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManagaer);
                if(mBundleRecyclerViewState != null){
                    Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    recyclerView.getLayoutManager().onRestoreInstanceState(listState);
                }
                try {
                    recyclerView.setAdapter(new RecyclerNew(jsonArray.getJSONObject(1).getJSONArray("items")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(recyclerView.getLayoutManager().toString());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                            Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
                            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
                        }
                    });
                }
                return convertView;
            default:
                layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                final Set set;
                convertView = layoutInflater.inflate(R.layout.news_layout, null);
                set = new Set();
                set.imageView = convertView.findViewById(R.id.imageNewsBody);
                set.name = convertView.findViewById((R.id.nameNews));
                set.date = convertView.findViewById((R.id.dateNews));
                set.text = convertView.findViewById((R.id.textNews));
                convertView.setTag(set);
                try {
                    if (MainActivity.metricsB.widthPixels>jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getInt("width")) {
                        double differentWidthDisp = MainActivity.metricsB.widthPixels - jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getInt("width");
                        int widthImage = MainActivity.metricsB.widthPixels;
                        System.out.println("differentWidthDisp " + differentWidthDisp + " " + MainActivity.metricsB.widthPixels + " " + jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getInt("width"));
                        differentWidthDisp = (100 / (double)MainActivity.metricsB.widthPixels) * differentWidthDisp;
                        System.out.println(MainActivity.metricsB.heightPixels);
                        System.out.println("differentWidthDisp " + differentWidthDisp + " " + MainActivity.metricsB.widthPixels + " " + jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getInt("width"));
                        System.out.println("differentWidthDisp пров" + differentWidthDisp);
                        double heightImage = jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getInt("height");
                        System.out.println(heightImage);
                        heightImage = (heightImage / 100 * differentWidthDisp) + heightImage;
                        System.out.println(heightImage);
                        set.imageView.getLayoutParams().width = widthImage;

                        set.imageView.getLayoutParams().height = (int)heightImage;
                    }else {
                        int differentWidthDisp = jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getInt("width") - MainActivity.metricsB.widthPixels;
                        //int widthImage = MainActivity.metricsB.widthPixels;
                        System.out.println("differentWidthDisp " + differentWidthDisp + " " + MainActivity.metricsB.widthPixels + " " + jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getInt("width"));
                        differentWidthDisp = 100 / jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getInt("width") * differentWidthDisp;
                        int heightImage = jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getInt("height");
                        System.out.println("differentWidthDisp " + differentWidthDisp);
                        heightImage = (heightImage / 100 * differentWidthDisp) - heightImage;
                        //set.imageView.getLayoutParams().width = widthImage;
                        set.imageView.getLayoutParams().height = heightImage;
                    }
                    String link = jsonArray.getJSONObject(position).getJSONArray("copy_history").getJSONObject(0).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getString("photo_75");
                    new DownloaderAdapter(set.imageView).execute(link);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return convertView;

        }
    }
    public class Set{
        TextView name, date, text;
        ImageView imageView;
    }

}