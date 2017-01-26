package a00959419.comp3717.bcit.ca.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class screenMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paused);
    }

    public void onButtonPlayClick(View view) {
        if (view.getId() == R.id.buttonPlay) {
            Intent play = new Intent(screenMain.this, screenPlay.class);
            startActivity(play);
        }
        if (view.getId() == R.id.buttonTutorial) {
            Intent tutorial = new Intent(screenMain.this, screenTutorial.class);
            startActivity(tutorial);
        }
        if (view.getId() == R.id.buttonDiscoveries) {
            Intent discoveries = new Intent(screenMain.this, screenDiscoveries.class);
            startActivity(discoveries);
        }
        if (view.getId() == R.id.buttonSettings) {
            Intent settings = new Intent(screenMain.this, screenSettings.class);
            startActivity(settings);
        }


    }
}
