package com.example.lestradde.vkapp.my;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lestradde.vkapp.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by lestradde on 10.10.18.
 */

public class RecyclerNew extends RecyclerView.Adapter<RecyclerNew.MyViewHolder> {
    private JSONArray jsonArray;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.little_image_photos, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DownloaderAdapter downloaderAdapter = new DownloaderAdapter(holder.imageView);
        try {
            downloaderAdapter.execute(jsonArray.getJSONObject(position).getString("photo_75"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.littlePhotos);
        }
    }
    public RecyclerNew(JSONArray dataset) {
        jsonArray = dataset;
    }

}
