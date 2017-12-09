package com.handen.hypemeter;

/**
 * Created by Vanya on 07.06.2017.
 */

import android.content.Intent;



import com.vk.sdk.VKSdk;

public class MainApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
