package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Kunlaya on 2017-01-25.
 */

public class screenPaused extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);
    }
    public void onButtonPlayClick(View view) {
        if (view.getId() == R.id.buttonBack) {
            Intent back = new Intent(screenPaused.this, screenPlay.class);
            startActivity(back);
        }
        if (view.getId() == R.id.buttonHome) {
            Intent main = new Intent(screenPaused.this, screenMain.class);
            startActivity(main);
        }

        if (view.getId() == R.id.buttonSettings) {
            Intent settings = new Intent(screenPaused.this, screenSettings.class);
            startActivity(settings);
        }


    }
}
