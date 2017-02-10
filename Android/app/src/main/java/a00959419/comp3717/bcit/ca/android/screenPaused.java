package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Kunlaya on 2017-01-25.
 */

public class ScreenPaused extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);
    }

    public void buttonBackClick(View view) {
        finish();
    }

    public void buttonSettingsClick(View view) {
        Intent settings = new Intent(ScreenPaused.this, ScreenSettings.class);
        startActivity(settings);
    }

    public void buttonHomeClick(View view) {
        Intent home = new Intent(ScreenPaused.this, ScreenMain.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);
    }
}
