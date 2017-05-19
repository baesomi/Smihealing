package com.swdm.mp.smile;

import android.os.Handler;

/**
 * Created by ChaeRin on 2017-02-15.
 */
public class ServiceThread extends Thread {
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {
        while (isRun) {
            handler.sendEmptyMessage(0); //thread에 있는 handler에게 메시지 보냄
            try {
                Thread.sleep(86400000); //24x60x60 = one day
            } catch (Exception e) {
            }
        }
    }
}
