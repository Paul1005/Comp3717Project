package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import static a00959419.comp3717.bcit.ca.android.ScreenMain.musicPlayer;
import static a00959419.comp3717.bcit.ca.android.ScreenMain.soundFX;

/**
 * Created by Kunlaya on 2017-01-25.
 */

public class ScreenSettings extends Activity {
    public static boolean mute = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onMute(View view) {
        soundFX.start();
        Switch onOffSwitch = (Switch) view;
        if (onOffSwitch.isChecked()) {
            musicPlayer.pause();
            mute = true;
        } else {
            musicPlayer.start();
            mute = false;
        }
    }

    public boolean getMuteValue(){
        return mute;
    }

    public void buttonBackClick(View view) {
        soundFX.start();
        finish();
    }
}
