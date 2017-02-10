package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Kunlaya on 2017-01-25.
 */

public class ScreenDiscovery extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
    }
    public void buttonBackClick(View view) {
        finish();
    }
}
