package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static a00959419.comp3717.bcit.ca.android.ScreenMain.soundFX;

/**
 * Created by Kunlaya on 2017-01-25.
 */

public class ScreenTutorial extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
    }

    public void buttonPauseClick(View view) {
        soundFX.start();
        Intent paused = new Intent(ScreenTutorial.this, ScreenPaused.class);
        startActivity(paused);
    }
}
