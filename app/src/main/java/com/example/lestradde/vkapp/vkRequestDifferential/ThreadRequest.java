package com.example.lestradde.vkapp.vkRequestDifferential;

/**
 * Created by lestradde on 25.10.18.
 */

public class ThreadRequest implements Runnable {
    Thread thread;
    Adder ad;
    Rea rea;
    public ThreadRequest(Adder ad, Rea rea){
        thread = new Thread(this, "requester");
       this.ad = ad;
        this.rea = rea;
        thread.start();
    }
    @Override
    public void run() {
        //System.out.println("thread");
        rea.request();

    }
}
