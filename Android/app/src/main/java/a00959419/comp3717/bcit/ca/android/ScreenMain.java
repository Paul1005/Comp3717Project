package a00959419.comp3717.bcit.ca.android;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import java.io.IOException;

import static a00959419.comp3717.bcit.ca.android.ScreenSettings.mute;

public class ScreenMain extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;
    public static MediaPlayer soundFX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundFX = MediaPlayer.create(ScreenMain.this, R.raw.button_press);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(ScreenMain.this, R.raw.menu);
        mediaPlayer.setLooping(true);
        if (!mute) {
            mediaPlayer.start();
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

    public void buttonSettingsClick(View view) {
        soundFX.start();
        Intent settings = new Intent(ScreenMain.this, ScreenSettings.class);
        startActivity(settings);
    }
}
