package a00959419.comp3717.bcit.ca.android;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static a00959419.comp3717.bcit.ca.android.ScreenMain.mediaPlayer;
import static a00959419.comp3717.bcit.ca.android.ScreenMain.soundFX;
import static a00959419.comp3717.bcit.ca.android.ScreenSettings.mute;

public class ScreenGameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_game_over);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(ScreenGameOver.this, R.raw.gameover);
        mediaPlayer.setLooping(false);
        if (!mute) {
            mediaPlayer.start();
        }
    }

    public void buttonHomeClick(View view) {
        soundFX.start();
        Intent home = new Intent(ScreenGameOver.this, ScreenMain.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);
    }
}
