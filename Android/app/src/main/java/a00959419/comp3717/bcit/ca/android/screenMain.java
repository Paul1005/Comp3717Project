package a00959419.comp3717.bcit.ca.android;

import android.media.MediaPlayer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import java.io.Serializable;

import static a00959419.comp3717.bcit.ca.android.ScreenSettings.mute;

public class ScreenMain extends AppCompatActivity {

    public static MediaPlayer soundFX;
    public static MediaPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundFX = MediaPlayer.create(ScreenMain.this, R.raw.button_press);
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
        musicPlayer = MediaPlayer.create(ScreenMain.this, R.raw.menu);
        musicPlayer.setLooping(true);
        if (!mute) {
            musicPlayer.start();
        }
    }

    public void buttonPlayClick(View view) {
        soundFX.start();
        Intent play = new Intent(ScreenMain.this, ScreenPlay.class);
        startActivity(play);
    }

    public void buttonTutorialClick(View view) {
        soundFX.start();
        Intent tutorial = new Intent(ScreenMain.this, ScreenTutorial.class);
        startActivity(tutorial);
    }

    public void buttonDiscoveriesClick(View view) {
        soundFX.start();
        Intent discoveries = new Intent(ScreenMain.this, ScreenDiscoveries.class);
        startActivity(discoveries);
    }

    public void buttonSettingsClick(View view) {
        soundFX.start();
        Intent settings = new Intent(ScreenMain.this, ScreenSettings.class);
        startActivity(settings);
    }
}
