package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Created by Kunlaya on 2017-01-26.
 */

public class screenPlay extends Activity{
    private move mov = new move();
    private Thread thread = new Thread(mov);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        findViewById(R.id.buttonRight).setOnTouchListener(new pressListener());
    }

    public void buttonPauseClick(View view) {
        Intent paused = new Intent(screenPlay.this, screenPaused.class);
        startActivity(paused);
    }

    class pressListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    thread.start();
                    return true;
                case MotionEvent.ACTION_UP:
                    thread.interrupt();
                    return true;
            }
            return false;
        }
    }

    class move implements Runnable {
        public void run() {
            while(!Thread.interrupted()) {
                findViewById(R.id.tempHouse).offsetLeftAndRight((int)(Math.random() * 3) - 1);
            }
        }
    }
}
