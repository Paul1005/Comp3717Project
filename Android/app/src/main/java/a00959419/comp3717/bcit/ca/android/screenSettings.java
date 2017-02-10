package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import static a00959419.comp3717.bcit.ca.android.screenMain.mediaPlayer;

/**
 * Created by Kunlaya on 2017-01-25.
 */

public class screenSettings extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onMute(View view) {
        Switch onOffSwitch = (Switch)view;
        if(onOffSwitch.isChecked()){
            mediaPlayer.pause();
        }else{
            mediaPlayer.start();
        }
    }

    public void buttonBackClick(View view) {
        finish();
    }
}
