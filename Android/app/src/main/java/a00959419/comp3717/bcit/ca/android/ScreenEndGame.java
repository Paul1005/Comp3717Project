package a00959419.comp3717.bcit.ca.android;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static a00959419.comp3717.bcit.ca.android.ScreenMain.mediaPlayer;
import static a00959419.comp3717.bcit.ca.android.ScreenMain.soundFX;
import static a00959419.comp3717.bcit.ca.android.ScreenSettings.mute;

public class ScreenEndGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_screen_game_over);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(ScreenEndGame.this, getIntent().getIntExtra("music", -1));
        mediaPlayer.setLooping(false);

        if (!mute) {
            mediaPlayer.start();
        }

        TextView textView = (TextView) findViewById(R.id.textView4);

        textView.setText(getIntent().getStringExtra("status"));
    }

    public void buttonHomeClick(View view) {
        soundFX.start();
        Intent home = new Intent(ScreenEndGame.this, ScreenMain.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);
    }
}
