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

    public void buttonBackClick(View view) {
        finish();
    }

    public void buttonSettingsClick(View view) {
        Intent settings = new Intent(screenPaused.this, screenSettings.class);
        startActivity(settings);
    }

    public void buttonHomeClick(View view) {
        Intent home = new Intent(screenPaused.this, screenMain.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);
    }
}
