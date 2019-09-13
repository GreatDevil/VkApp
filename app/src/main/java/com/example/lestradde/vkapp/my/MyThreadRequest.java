package com.example.lestradde.vkapp.my;

import android.support.v7.util.AsyncListUtil;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lestradde.vkapp.messages.MessagesRecyclerAdapter;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lestradde on 19.10.18.
 */
/*
public class MyThreadRequest implements Runnable {
    Thread thread;
    private RequestArray req;
    Resp resp;

    public static class Resp {
        private RequestArray req;
        private ArrayList<VKRequest> vkRequestsThread = new ArrayList<>();
        private ArrayList<VKResponse> vkResponseArrayList = new ArrayList<>();
        VKResponse resp = null;
        boolean valueset = false;
        Resp(RequestArray req){
            this.req = req;
        }

        public synchronized void response() {
            while (!valueset) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Движ");
            while ((vkRequestsThread = req.getArray()) != null) {

                VKBatchRequest vkBatchRequest = new VKBatchRequest(vkRequestsThread.toArray(new VKRequest[vkRequestsThread.size()]));
                vkBatchRequest.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
                    @Override
                    public void onComplete(VKResponse[] responses) {
                        super.onComplete(responses);
                        for (int i = 0; i < responses.length; i++) {
                            vkResponseArrayList.add(responses[i]);
                            System.out.println(responses[i].toString());
                        }

                    }
                });
            }
            valueset = false;
            notify();

        }

        public void wakeUp() {
            valueset = true;
            System.out.println("WAKEUP");
            notify();
        }
    }

    public MyThreadRequest(Resp resp){
        this.resp = resp;
        req = new RequestArray();
        thread = new Thread(this, "requester");
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("ПОТОК");
        resp.response();
    }

    public static class RequestArray{
        private ArrayList<VKRequest> vkRequests = new ArrayList<>();

        public synchronized void addRequest(VKRequest request){
            vkRequests.add(request);
        }

        public synchronized ArrayList<VKRequest> getArray(){
            ArrayList<VKRequest> req = vkRequests;
            vkRequests.clear();
            return req;
        }
    }
    public static class Requester{
        public static void setRequest(VKRequest request, MyThreadRequest myThreadRequest, Resp resp){
            myThreadRequest.req.addRequest(request);
            if (!myThreadRequest.thread.isAlive()){
                resp.wakeUp();
            }
        }
    }

}
*/