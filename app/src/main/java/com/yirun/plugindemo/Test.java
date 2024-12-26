package com.yirun.plugindemo;

import android.util.Log;

public class Test {
    public void test(){
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        Log.d("MethodTime", "cost:" + (System.currentTimeMillis() - startTime));

    }
}
