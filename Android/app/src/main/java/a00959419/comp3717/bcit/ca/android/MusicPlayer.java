package a00959419.comp3717.bcit.ca.android;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Paul on 2017-02-09.
 */

public class MusicPlayer extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
