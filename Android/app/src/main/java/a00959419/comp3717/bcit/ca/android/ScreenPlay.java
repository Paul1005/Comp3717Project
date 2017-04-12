package a00959419.comp3717.bcit.ca.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static a00959419.comp3717.bcit.ca.android.ScreenMain.mediaPlayer;
import static a00959419.comp3717.bcit.ca.android.ScreenMain.soundFX;
import static a00959419.comp3717.bcit.ca.android.ScreenSettings.mute;

/**
 * Created by Kunlaya on 2017-01-26.
 */

public class ScreenPlay extends Activity {
    GameView gameView;

    Map map;
    Player player;
    ArrayList<Enemy> enemies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new GameView(this);
        setContentView(gameView);

        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(ScreenPlay.this, R.raw.game);
        mediaPlayer.setLooping(true);

        if (!mute) {
            mediaPlayer.start();
        }

        String level = getIntent().getStringExtra("level");
        String trees = getIntent().getStringExtra("trees");
        String playerSpawn = getIntent().getStringExtra("player spawn");
        String enemySpawns = getIntent().getStringExtra("enemy spawns");

        try {
            makeMap(level, trees, playerSpawn, enemySpawns);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        player = new Player(gameView, map, this, map.getPlayerSpawn().getX(),
                map.getPlayerSpawn().getY());
        for (Point spawnPoint : map.getEnemySpawns()) {
            enemies.add(new Enemy(gameView, map, this, player, spawnPoint.getX(), spawnPoint.getY()));
        }
    }

    @Override
    public void onBackPressed() {
        Intent play = new Intent(ScreenPlay.this, ScreenPaused.class);
        startActivity(play);
    }

    private void makeMap(String buildings, String trees, String playerSpawn, String enemySpawns)
            throws IOException, JSONException {
        map = new Map(getJsonFromFile(buildings), getJsonFromFile(trees),
                getJsonFromFile(playerSpawn), getJsonFromFile(enemySpawns));
    }

    private JSONArray getJsonFromFile(String jsonFile) throws IOException, JSONException {
        String json;
        InputStream is = getAssets().open(jsonFile);
        int size = is.available();
        byte[] buffer = new byte[size];

        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        JSONObject jsonObject = new JSONObject(json);

        return jsonObject.getJSONArray("geometries");
    }

    public void buttonPauseClick(View view) {
        soundFX.start();
        Intent paused = new Intent(ScreenPlay.this, ScreenPaused.class);
        startActivity(paused);
    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        gameView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        gameView.pause();
    }

    // More SimpleGameEngine methods will go here

    // Here is our implementation of GameView
    // It is an inner class.
    // Note how the final closing curly brace }
    // is inside SimpleGameEngine

    // Notice we implement runnable so we have
    // A thread and can override the run method.
    class GameView extends SurfaceView implements Runnable {

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;

        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        // When the we initialize (call new()) on gameView
        // This special constructor method runs
        public GameView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();
        }

        @Override
        public void run() {
            while (playing) {
                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Update the frame
                update();

                // Draw the frame
                try {
                    draw();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Calculate the fps this frame
                // We can then use the result to
                // time animations and more.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame > 0) {
                    fps = 1000 / timeThisFrame;
                }
            }
        }


        // Everything that needs to be updated goes in here
        // In later projects we will have dozens (arrays) of objects.
        // We will also do other things like collision detection.
        public void update() {
            for (Enemy enemy : enemies) {
                enemy.updatePos(fps);
            }
            player.updatePos(fps);
        }

        // Draw the newly updated scene
        public void draw() throws IOException, JSONException {

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {

                //JSONObject reader = new JSONObject(in);
                // Lock the canvas ready to draw
                // Make the drawing surface our canvas object
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                //canvas.drawPicture();
                canvas.drawColor(Color.argb(255, 255, 248, 220));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 0, 0, 0));
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(4);
                // Make the text a bit bigger
                paint.setTextSize(45);

                // Display the current fps on the screen
                canvas.drawText("FPS:" + fps, 20, 40, paint);

                //map.display(canvas, paint);
                map.display(canvas, paint);

                player.display(canvas, paint);

                for (Enemy enemy : enemies) {
                    enemy.display(canvas, paint);
                }

                // Draw everything to the screen
                // and unlock the drawing surface
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }

        // If SimpleGameEngine Activity is started the
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.
        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            player.changeMove(motionEvent);
            return true;
        }

    }
}
