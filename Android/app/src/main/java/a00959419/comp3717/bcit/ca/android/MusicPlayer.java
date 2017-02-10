package a00959419.comp3717.bcit.ca.android;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Paul on 2017-02-09.
 */

public class MusicPlayer extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MusicPlayer(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
