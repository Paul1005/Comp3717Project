package a00959419.comp3717.bcit.ca.android;

import android.media.MediaPlayer;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class screenMain extends AppCompatActivity {

    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(mediaPlayer != null) {
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(screenMain.this, R.raw.menu);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void buttonPlayClick(View view) {
        Intent play = new Intent(screenMain.this, screenPlay.class);
        startActivity(play);
    }

    public void buttonTutorialClick(View view) {
        Intent tutorial = new Intent(screenMain.this, screenTutorial.class);
        startActivity(tutorial);
    }

    public void buttonDiscoveriesClick(View view) {
        Intent discoveries = new Intent(screenMain.this, screenDiscoveries.class);
        startActivity(discoveries);
    }

    public void buttonSettingsClick(View view) {
        Intent settings = new Intent(screenMain.this, screenSettings.class);
        startActivity(settings);
    }
}
