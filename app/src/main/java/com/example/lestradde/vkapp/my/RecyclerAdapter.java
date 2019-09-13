package com.example.lestradde.vkapp.my;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lestradde.vkapp.MainActivity;
import com.example.lestradde.vkapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lestradde on 07.10.18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private String[] mDataset;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recyclerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hor,parent,false);
        ViewHolder rec = new ViewHolder(recyclerView);
        return rec;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            holder.recyclerView.setLayoutManager(horizontalLayoutManagaer);
            holder.recyclerView.setAdapter(new HorizontalAdapter(new String[]{"fffffff","gggggggg"}));

        /*else {
            holder.mTextView.setText(mDataset[position]);
            System.out.println(mDataset[position] + "    pos");
        }*/
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.my);
            recyclerView = itemView.findViewById(R.id.hor);
        }
    }
    public RecyclerAdapter(String[] dataset, Context context) {
        mDataset = dataset;
        this.context = context;
    }


    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
        private String[] mDataset;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_layout_my, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mTextView.setTextColor(Color.BLACK);
            holder.mTextView.setText(mDataset[position]);
            System.out.println(mDataset[position] + "    pos");
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView mTextView;
            public MyViewHolder(View itemView) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.my);
            }
        }
        public HorizontalAdapter(String[] dataset) {
            mDataset = dataset;
        }

    }
}
