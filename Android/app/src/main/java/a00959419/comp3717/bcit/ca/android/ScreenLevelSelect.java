package a00959419.comp3717.bcit.ca.android;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static a00959419.comp3717.bcit.ca.android.ScreenMain.soundFX;

public class ScreenLevelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_level_select);
    }

    public void levelSelect(View view) {
        soundFX.start();
        Intent play = new Intent(ScreenLevelSelect.this, ScreenPlay.class);
        String levelInfo = (String)view.getTag();
        String[] level = levelInfo.split(" ");
        play.putExtra("level",level[0]);
        play.putExtra("trees", level[1]);
        startActivity(play);
    }
}
