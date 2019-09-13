package com.example.lestradde.vkapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static android.R.attr.bitmap;

/**
 * Created by lestradde on 07.09.18.
 */

public class NewsAdapter extends BaseAdapter {
    ArrayList<JSONObject> userObj = new ArrayList<>();
    Locale local = new Locale("ru","RU");
    DateFormat df = DateFormat.getDateTimeInstance (DateFormat.DEFAULT,DateFormat.DEFAULT,local);
    private Context context;
    private JSONArray items;
    private JSONArray info = new JSONArray();
    private JSONObject object;
    LruCache<Integer, Bitmap> mMemoryCache;
    public NewsAdapter(Context context, JSONObject object, JSONArray info) {
        this.context = context;
        //this.arrayMessage = arrayMessage;
        this.object = object;
        try {
            this.items = object.getJSONArray("items");
            this.info = info;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<Integer, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(Integer key, Bitmap bitmap) {
                //return super.sizeOf(key, value);
                return bitmap.getByteCount();
            }
        };
    }

    public void addUserObj(ArrayList<JSONObject> object){
        userObj = object;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
            return items.length();

        /*try {
            return object.getJSONArray("items").length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 1;*/
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final Set set;

                convertView = layoutInflater.inflate(R.layout.news_layout,null);
                set = new Set();
                set.imageView = convertView.findViewById(R.id.imageNewsBody);
                set.name = convertView.findViewById((R.id.nameNews));
                set.date = convertView.findViewById((R.id.dateNews));
                set.text = convertView.findViewById((R.id.textNews));
                convertView.setTag(set);
        try {
            System.out.println("items " + items );
                switch (items.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getString("type")) {
                    case "photo":
                        System.out.println("This is object / " + items.getJSONObject(position).toString());
                        if (items.getJSONObject(position).getInt("source_id") > 0){
                                    try {
                                            set.name.setText(info.getJSONObject(position).getString("first_name") + " " + info.getJSONObject(position).getString("last_name"));
                                            //System.out.println(position +"/   " +response.json.getJSONArray("response").getJSONObject(0).getString("first_name")+" "+ response.json.getJSONArray("response").getJSONObject(0).getString("last_name"));
                                        } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                        }else {

                                    try {
                                        System.out.println("info.length()   " + info.length());
                                            set.name.setText(info.getJSONObject(position).getString("name"));
                                           // System.out.println(position + "/   " + userObj.get(position).getJSONArray("response").getJSONObject(0).getString("name"));
                                        } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                        }
                        set.date.setText(df.format(items.getJSONObject(position).getLong("date")*1000));
                        set.text.setText(items.getJSONObject(position).getString("text"));
                        new DownloadImageTask(set.imageView).execute(items.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getString("photo_130"),items.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getString("id"));
                        //System.out.println(position+"/   "+object.getJSONArray("items").getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getString("photo_130") + "  id=  " + object.getJSONArray("items").getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getString("id"));
                        //new DownloadImageTask(set.imageView).execute(arrayMessage.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("photo").getString("photo_130"));
                        return convertView;
                    case "posted_photo":
                        new DownloadImageTask(set.imageView).execute(items.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("posted_photo").getString("photo_130"),items.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("posted_photo").getString("id"));
                        //System.out.println(object.getJSONArray("items").getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("posted_photo").getString("id"));
                        //new DownloadImageTask(set.imageView).execute(arrayMessage.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("posted_photo").getString("photo_130"));
                        return convertView;
                    default:
                        set.name.setText("null");
                        return convertView;
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
    public void add(JSONObject ob, JSONObject inf){
        items.put(ob);
        info.put(inf);
        notifyDataSetChanged();
    }
    public class Set{
        TextView name, date, text;
        ImageView imageView;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }


        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            String id = urls[1];
            Bitmap mIcon11 = getBitmapFromMemCache(Integer.parseInt(id));
            if (mIcon11 == null) {
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                    addBitmapToMemoryCache(Integer.parseInt(id), mIcon11);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }

    public void addBitmapToMemoryCache(Integer key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(Integer key) {
        return mMemoryCache.get(key);
    }

}
