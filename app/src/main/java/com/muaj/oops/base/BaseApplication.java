package com.muaj.oops.base;

import android.app.Application;

/** BaseApplication
 * @author muaj
 * @date 2018/5/15
 */

public class BaseApplication  extends Application {

    protected static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

   public static BaseApplication getInstance(){
        return  mInstance;
   }
}
