package com.example.lestradde.vkapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiMessage;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by lestradde on 06.09.18.
 */

public class MessageRead extends BaseAdapter {
    private Context context;
    private JSONArray arrayMessage;

    public MessageRead(Context context, JSONArray arrayMessage) {
        this.context = context;
        this.arrayMessage = arrayMessage;
    }

    @Override
    public int getCount() {
        return arrayMessage.length();
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
        VKApiMessage mes;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.custom_layout,null);
        View viewMy =  layoutInflater.inflate(R.layout.message_layout_my,null);
        View viewImage = layoutInflater.inflate(R.layout.image_layout,null);
        Set set = new Set();
        set.other = view.findViewById(R.id.other);
        set.my = viewMy.findViewById(R.id.my);
        set.imageView = viewImage.findViewById(R.id.imageView2);
        try {

            if (arrayMessage.getJSONObject(position).getString("out").equals("1")) {
                try {
                    if(arrayMessage.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).get("type").toString().equals("sticker")){
                        System.out.println(arrayMessage.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("sticker").get("photo_128").toString());
                        new DownloadImageTask(set.imageView).execute(arrayMessage.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("sticker").get("photo_128").toString());
                        if (!arrayMessage.getJSONObject(position).getString("body").equals("")){
                            TextView body = new TextView(context);
                            body.setText(arrayMessage.getJSONObject(position).getString("body"));
                            LinearLayout mainLayout = viewImage.findViewById(R.id.linear);
                            mainLayout.addView(body);
                        }
                        return viewImage;
                    }
                } catch (Exception e){
                    System.out.println(e);
                }
                set.other.setText(arrayMessage.getJSONObject(position).getString("body"));
                return view;
            }else {
                try {
                    //attchStrind.add(array.getJSONObject(i).getJSONArray("attachments").toString());;
                    if(arrayMessage.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).get("type").toString().equals("sticker")){
                        System.out.println(arrayMessage.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("sticker").get("photo_128").toString());
                        //ImageView imageView = new ImageView(MainActivity.this);
                        //imageView.setImageURI(Uri.parse(array.getJSONObject(i).getJSONArray("attachments").getJSONObject(0).getJSONObject("sticker").get("photo_128").toString()));
                        new DownloadImageTask(set.imageView).execute(arrayMessage.getJSONObject(position).getJSONArray("attachments").getJSONObject(0).getJSONObject("sticker").get("photo_128").toString());
                        if (!arrayMessage.getJSONObject(position).getString("body").equals("")){
                            TextView body = new TextView(context);
                            body.setText(arrayMessage.getJSONObject(position).getString("body"));
                            LinearLayout mainLayout = viewImage.findViewById(R.id.linear);
                            mainLayout.addView(body);
                        }
                        return viewImage;
                    }
                } catch (Exception e){
                    System.out.println(e);
                }
                set.my.setText(arrayMessage.getJSONObject(position).getString("body"));
                return viewMy;
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return null;
    }
    public class Set{
        TextView other, my;
        ImageView imageView;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

