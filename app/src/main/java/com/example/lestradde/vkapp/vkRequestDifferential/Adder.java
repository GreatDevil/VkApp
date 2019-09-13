package com.example.lestradde.vkapp.vkRequestDifferential;

import android.content.Context;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import com.vk.sdk.api.VKRequest;

import java.util.ArrayList;

/**
 * Created by lestradde on 25.10.18.
 */

public class Adder {
    ArrayList<Obj> ob = new ArrayList<>();

    public synchronized void addItem(VKRequest []req, Context cont, ListView listView, String nameAdapter){
        for (int i =0; i < req.length; i ++) {
            Obj obj = new Obj(req[i], cont, listView, nameAdapter);
            ob.add(obj);
        }
    }
    public synchronized ArrayList<Obj> getItem(){
        ArrayList<Obj> ar1 = new ArrayList<>(ob);
        ob.clear();
        return ar1;
    }
    public synchronized void starter(ThreadRequest threadRequest){
        if (!threadRequest.thread.isAlive()){
            threadRequest.run();
        }
    }
    static class Obj{
        VKRequest request;
        Context context;
        ListView listView;
        String nameAdapter;
        Obj(Integer integer){

        }
        Obj(VKRequest request, Context context,ListView listView, String nameAdapter){
            this.request = request;
            this.context = context;
            this.listView = listView;
            this.nameAdapter = nameAdapter;
        }
    }
}
