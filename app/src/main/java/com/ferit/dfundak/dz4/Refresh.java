package com.ferit.dfundak.dz4;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Dora on 27/04/2017.
 */

public class Refresh extends Service {

    private final RefreshFeed mRefresh = new RefreshFeed();

    public void getData() {
        MainActivity mainActivity = new MainActivity();
        mainActivity.requestData();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mRefresh;
    }

    public class RefreshFeed extends Binder {
        Refresh getService(){
            return Refresh.this;
        }
    }

}
