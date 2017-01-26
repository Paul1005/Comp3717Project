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

    public void onButtonPlayClick(View  view){
        Intent intent = new Intent(screenMain.this,screenDiscoveries.class);
        startActivity(intent);
    }


}
