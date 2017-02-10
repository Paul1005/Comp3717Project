package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import static a00959419.comp3717.bcit.ca.android.ScreenMain.soundFX;

/**
 * Created by Kunlaya on 2017-01-25.
 */

public class ScreenDiscoveries extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discoveries);
    }

    public void buttonBackClick(View view) {
        soundFX.start();
        finish();
    }

    public void buttonTempClick(View view) {
        soundFX.start();
        Intent discovery = new Intent(ScreenDiscoveries.this, ScreenDiscovery.class);
        startActivity(discovery);
    }
}
