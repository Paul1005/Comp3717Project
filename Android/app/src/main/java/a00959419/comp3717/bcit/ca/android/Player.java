package a00959419.comp3717.bcit.ca.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Andrew on 3/22/2017.
 */

public class Player {
    // Declare an object of type Bitmap
    Bitmap bitmapBob;
    private ArrayList<Float> rectPoints;

    // start off not moving.
    MovDirHorizontal movH = MovDirHorizontal.NONE;
    MovDirVertical movV = MovDirVertical.NONE;

    // He can walk at 150 pixels per second
    float walkSpeedPerSecond = 150;

    // He starts 10 pixels from the left
    float bobXPosition = 10;
    float bobYPosition = 10;

    public Player(ScreenPlay.GameView gameView) {
        // Load Bob from his .png file

        bitmapBob = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dinosaur);
        bitmapBob = Bitmap.createScaledBitmap(bitmapBob, 100, 100, true);

    }

    public void changeMove(MotionEvent motionEvent) {
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
    }

    public void updatePos(long fps) {
        // If bob is moving (the player is touching the screen)
        // then move him to the right based on his target speed and the current fps.
        switch (movH) {
            case RIGHT:
                if (bobXPosition + walkSpeedPerSecond / fps < rectPoints.get(0)) {
                    bobXPosition = bobXPosition + (walkSpeedPerSecond / fps);
                }
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

    public void display(Canvas canvas, Paint paint) {
        // Draw bob at bobXPosition, 200 pixels
        canvas.drawBitmap(bitmapBob, bobXPosition, bobYPosition, paint);
    }

    enum MovDirHorizontal {
        NONE, LEFT, RIGHT
    }

    enum MovDirVertical {
        NONE, UP, DOWN
    }

    public void setRectPoints(ArrayList<Float> rectPoints) {
        this.rectPoints = rectPoints;
    }
}
