package com.example.lestradde.vkapp.vkRequestDifferential;

/**
 * Created by lestradde on 25.10.18.
 */

public class Server {
    public String[] response(Integer items[]) {
        String[] str = new String[items.length];
        for (int i = 0; i < items.length; i++){
            str[i] = items[i] + " RESPONSE";
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str;
    }
}
