package com.example.lestradde.vkapp.messages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lestradde.vkapp.R;
import com.example.lestradde.vkapp.my.DownloaderAdapter;
import com.example.lestradde.vkapp.my.PageFragment;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.lestradde.vkapp.my.PageFragment.adder;
import static com.example.lestradde.vkapp.my.PageFragment.threadRequest;

/**
 * Created by lestradde on 17.10.18.
 */

public class MessagesRecyclerAdapter extends BaseAdapter {
    JSONArray items;
    Context context;
    ArrayList<JSONObject> userObj = new ArrayList<>();


    public MessagesRecyclerAdapter(Context context, JSONArray array){
        items = array;
        this.context = context;
    }
    public void addUserObj(ArrayList<JSONObject> object){
        userObj = object;
            notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.length();
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
        convertView =layoutInflater.inflate(R.layout.conversation,null);
        final Set set = new Set();
        set.imageView = convertView.findViewById(R.id.userPhotoConversation);
        set.jsonText = convertView.findViewById(R.id.jsonObjectConversation);
        try {
            /*VKRequest request =new VKRequest("users.get", VKParameters.from("user_ids", items.getJSONObject(position).getJSONObject("conversation").getJSONObject("peer").getString("id"), "fields", "photo_50"));
            VKRequest [] arrayRequest = new VKRequest[1];
            arrayRequest[0]=request;*/
            if (userObj.size() != 0) {
                try {
                    System.out.println(userObj.toString());
                    new DownloaderAdapter(set.imageView).execute(userObj.get(position).getJSONArray("response").getJSONObject(0).getString("photo_50"));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error of messagesrecycler");
                }
            }
                set.jsonText.setText(items.getJSONObject(position).getJSONObject("last_message").getString("text"));
            //MyThreadRequest.Requester requester = new MyThreadRequest.Requester();
            //requester.setRequest(request,req, myThreadRequest,set.imageView, set.jsonText);
           /* request.executeWithListener(new VKRequest.VKRequestListener() {

                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    try {
                        new DownloaderAdapter(set.imageView).execute(response.json.getJSONArray("response").getJSONObject(0).getString("photo_50"));
                        set.jsonText.setText(response.json.getJSONArray("response").getJSONObject(0).getString("photo_50"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
    public class Set{
        TextView jsonText;
        ImageView imageView;
    }
}
