package a00959419.comp3717.bcit.ca.android;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Andrew on 3/22/2017.
 */

public class Player {
    private static final int PLAYER_HEIGHT = 100;
    private static final int PLAYER_WIDTH = 100;
    private static final int LEFT_THRESHOLD
            = Resources.getSystem().getDisplayMetrics().widthPixels/5;
    private static final int RIGHT_THRESHOLD
            = Resources.getSystem().getDisplayMetrics().widthPixels - LEFT_THRESHOLD;
    private static final int TOP_THRESHOLD
            = Resources.getSystem().getDisplayMetrics().heightPixels/4;
    private static final int BOT_THRESHOLD
            = Resources.getSystem().getDisplayMetrics().heightPixels-TOP_THRESHOLD;
    private int score = 0;
    private static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().heightPixels;
    // Declare an object of type Bitmap
    Bitmap bitmapBob;
    private ArrayList<Rect> rects;
    private ArrayList<Rect> circles;
    // start off not moving.
    MovDirHorizontal movH = MovDirHorizontal.NONE;
    MovDirVertical movV = MovDirVertical.NONE;

    // He can walk at 150 pixels per second
    float walkSpeedPerSecond = 150;

    // He starts 10 pixels from the left
    float bobXPosition = SCREEN_HEIGHT - PLAYER_WIDTH;
    float bobYPosition = SCREEN_WIDTH - PLAYER_HEIGHT;

    public Player(ScreenPlay.GameView gameView) {
        // Load Bob from his .png file

        bitmapBob = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dinosaur);

        bitmapBob = Bitmap.createScaledBitmap(bitmapBob, PLAYER_WIDTH, PLAYER_HEIGHT, true);

    }

    public void changeMove(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:

                // Set isMoving so Bob is moved in the update method
                if (motionEvent.getX() < LEFT_THRESHOLD)
                    movH = MovDirHorizontal.LEFT;
                else if (motionEvent.getX() > RIGHT_THRESHOLD)
                    movH = MovDirHorizontal.RIGHT;


                if (motionEvent.getY() < TOP_THRESHOLD)
                    movV = MovDirVertical.UP;
                else if (motionEvent.getY() > BOT_THRESHOLD)
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
                float nextXPos = bobXPosition + walkSpeedPerSecond / fps;
                if (!isColliding(nextXPos, bobYPosition, rects)) {
                    bobXPosition = nextXPos;
                    if (isColliding(nextXPos, bobYPosition, circles)) {
                        score++;

                    }
                }
                break;
            case LEFT:
                nextXPos = bobXPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(nextXPos, bobYPosition, rects)) {
                    bobXPosition = nextXPos;
                    if (isColliding(nextXPos, bobYPosition, circles)) {
                        score++;
                    }
                }
                break;
            default:
                break;
        }

        switch (movV) {
            case DOWN:
                float nextYPos = bobYPosition + (walkSpeedPerSecond / fps);
                if (!isColliding(bobXPosition, nextYPos, rects)) {
                    bobYPosition = nextYPos;
                    if (isColliding(bobXPosition, nextYPos, circles)) {
                        score++;
                    }
                }
                break;
            case UP:
                nextYPos = bobYPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(bobXPosition, nextYPos, rects)) {
                    bobYPosition = nextYPos;
                    if (isColliding(bobXPosition, nextYPos, circles)) {
                        score++;
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean isColliding(float xPos, float yPos, ArrayList<Rect> rects) {
        for (Rect rect : rects) {
            if (rect.intersect((int) xPos, (int) yPos, (int) xPos + PLAYER_WIDTH, (int) yPos +
                    PLAYER_HEIGHT)) {
                return true;
            }
        }
        return false;
    }

    public void display(Canvas canvas, Paint paint) {

        // Draw bob at bobXPosition, 200 pixels
        canvas.drawBitmap(bitmapBob, bobXPosition, bobYPosition, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Score:" + score, 20, 40, paint);
    }

    public void setCircles(ArrayList<Rect> circles) {
        this.circles = circles;
    }

    enum MovDirHorizontal {
        NONE, LEFT, RIGHT
    }

    enum MovDirVertical {
        NONE, UP, DOWN
    }

    public void setRects(ArrayList<Rect> rects) {
        this.rects = rects;
    }
}
