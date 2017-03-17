package a00959419.comp3717.bcit.ca.android;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/*import com.esri.android.map.FeatureLayer;
import com.esri.core.geodatabase.ShapefileFeatureTable;

import java.io.FileNotFoundException;*/

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
    float maxX;
    float maxY;
    float minX;
    float minY;

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

        try {
            minMax();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void minMax() throws IOException, JSONException {
        maxX = maxY = 0;
        minX = minY = Float.MAX_VALUE;
        String json = null;
        InputStream is = getAssets().open("STREETS.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        JSONObject jsonObject = new JSONObject(json);

        JSONArray jsonArray = jsonObject.getJSONArray("geometries");

        for (int i = 0; i < jsonArray.length(); i++) {
            // JSONArray lineString = jsonArray.get(i);
            JSONArray lineString = jsonArray.getJSONObject(i).getJSONArray("coordinates");
            for (int j = 0; j < lineString.length(); j++) {
                JSONArray startCoordinates = (JSONArray) lineString.get(j);
                float xCur = (float) startCoordinates.getDouble(0);
                float yCur = (float) startCoordinates.getDouble(1);

                if (minX > xCur) {
                    minX = xCur;
                }
                if (maxX < xCur) {
                    maxX = xCur;
                }
                if (minY > yCur) {
                    minY = yCur;
                }
                if (maxY < yCur) {
                    maxY = yCur;
                }
            }
        }
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

        // Declare an object of type Bitmap
        Bitmap bitmapBob;

        // start off not moving.
        MovDirHorizontal movH = MovDirHorizontal.NONE;
        MovDirVertical movV = MovDirVertical.NONE;

        // He can walk at 150 pixels per second
        float walkSpeedPerSecond = 150;

        // He starts 10 pixels from the left
        float bobXPosition = 10;
        float bobYPosition = 10;

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

            // Load Bob from his .png file
            bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.button_home);
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

            // If bob is moving (the player is touching the screen)
            // then move him to the right based on his target speed and the current fps.
            switch (movH) {
                case RIGHT:
                    bobXPosition = bobXPosition + (walkSpeedPerSecond / fps);
                    break;
                case LEFT:
                    bobXPosition = bobXPosition - (walkSpeedPerSecond / fps);
                    break;
                default:
                    break;
            }

            switch (movV) {
                case DOWN:
                    bobYPosition = bobYPosition + (walkSpeedPerSecond / fps);
                    break;
                case UP:
                    bobYPosition = bobYPosition - (walkSpeedPerSecond / fps);
                    break;
                default:
                    break;
            }
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
                canvas.drawColor(Color.argb(255, 26, 128, 182));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 0, 0, 0));
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(4);
                // Make the text a bit bigger
                paint.setTextSize(45);

                // Display the current fps on the screen
                //canvas.drawText("FPS:" + fps, 20, 40, paint);

                String json = null;
                InputStream is = getAssets().open("STREETS.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");

                JSONObject jsonObject = new JSONObject(json);

                JSONArray jsonArray = jsonObject.getJSONArray("geometries");

                for (int i = 0; i < jsonArray.length(); i++) {
                    // JSONArray lineString = jsonArray.get(i);
                    JSONArray lineString = jsonArray.getJSONObject(i).getJSONArray("coordinates");
                    float[] points = new float[lineString.length() * 2];
                    for (int j = 0; j < lineString.length(); j++) {
                        points[2 * j] = (float) (((JSONArray) lineString.get(j)).getDouble(0) - minX);
                        points[2 * j + 1] = (float) (((JSONArray) lineString.get(j)).getDouble(1) - minY);
                    }
                    canvas.drawLines(points, paint);
                }
                // Draw bob at bobXPosition, 200 pixels
                canvas.drawBitmap(bitmapBob, bobXPosition, bobYPosition, paint);

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

        // If SimpleGameEngine Activity is started theb
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

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

                // Player has touched the screen
                case MotionEvent.ACTION_DOWN:

                    // Set isMoving so Bob is moved in the update method
                    if (motionEvent.getX() < 200)
                        movH = MovDirHorizontal.LEFT;
                    else if (motionEvent.getX() > 800)
                        movH = MovDirHorizontal.RIGHT;


                    if (motionEvent.getY() < 200)
                        movV = MovDirVertical.UP;
                    else if (motionEvent.getY() > 800)
                        movV = MovDirVertical.DOWN;

                    break;

                // Player has removed finger from screen
                case MotionEvent.ACTION_UP:

                    // Set isMoving so Bob does not move
                    movH = MovDirHorizontal.NONE;
                    movV = MovDirVertical.NONE;

                    break;
            }
            return true;
        }
    }

    enum MovDirHorizontal {
        NONE, LEFT, RIGHT
    }

    enum MovDirVertical {
        NONE, UP, DOWN
    }
}
