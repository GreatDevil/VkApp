package com.example.lestradde.vkapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vk.sdk.api.model.VKAttachments;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lestradde on 02.09.18.
 */

class RemoveAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
    private ArrayList<String> inOutList;
    private ArrayList<VKAttachments> attachmentses;
    private ArrayList<String> attchStrind =  new ArrayList<String>();
    ListView listView;
    TextView textView;
    View v;


    public RemoveAdapter(Context context, ArrayList<String> list, ArrayList<String> inOutList, ArrayList<String> attchStrind) {
        this.context = context;
        this.list = list;
        this.inOutList = inOutList;
        this.attchStrind = attchStrind;
    }

    @Override
    public int getCount() {
        return list.size();
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
        View view =  layoutInflater.inflate(R.layout.custom_layout,null);
        View viewMy =  layoutInflater.inflate(R.layout.message_layout_my,null);
        View viewImage = layoutInflater.inflate(R.layout.image_layout,null);
            Set set = new Set();
            set.other = view.findViewById(R.id.other);
            set.my = viewMy.findViewById(R.id.my);
            set.imageView = viewImage.findViewById(R.id.imageView2);

        if (inOutList.get(position) == "out") {
            set.other.setText(list.get(position));
            try {
                if (attchStrind.get(position) != ""){
                    new DownloadImageTask(set.imageView).execute(attchStrind.get(position));
                    return viewImage;
                }
            }catch (Exception e){
                System.out.println(e);
            }
            //set.other.setText(attchStrind.get(2).toString());
            return view;
        }else {
            set.my.setText(list.get(position));
            try {
                if (attchStrind.get(position) != ""){
                    set.my.setText(attchStrind.get(position));
                }
            }catch (Exception e){
                System.out.println(e);
            }
            //set.my.setText(attchStrind.get(0)+"");
            return viewMy;
        }
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
