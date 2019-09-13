package com.example.lestradde.vkapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

/**
 * Created by lestradde on 01.09.18.
 */

class CustomAdapter extends BaseAdapter {
    private ArrayList<String> messages;
    private ArrayList<Integer> users;
    private Context context;
    private VKList<VKApiDialog> list;

    public CustomAdapter(Context context, ArrayList<Integer> users, ArrayList<String> messages, VKList<VKApiDialog> list) {
        this.users = users;
        this.messages = messages;
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return users.size();
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
        SetData setData = new SetData();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.custom_layout,null);
        setData.userName = (TextView) view.findViewById(R.id.other);
        setData.messages = (TextView) view.findViewById(R.id.my);
        setData.userName.setText(users.get(position));
        setData.messages.setText(messages.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*VKRequest request = new VKRequest("my.send", VKParameters.from(VKApiConst.USER_ID,list.get(position).my.user_id, VKApiConst.MESSAGE, "text msg"));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        System.out.println("my send");
                    }
                });*/
            }
        });
        return view;
    }
    public class SetData{
        TextView userName, messages;
    }
}
