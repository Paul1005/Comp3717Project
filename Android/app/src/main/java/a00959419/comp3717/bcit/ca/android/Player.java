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

    private static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    private static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;

    private static final int LEFT_THRESHOLD = SCREEN_WIDTH / 5;
    private static final int RIGHT_THRESHOLD = SCREEN_WIDTH - LEFT_THRESHOLD;
    private static final int TOP_THRESHOLD = SCREEN_HEIGHT / 4;
    private static final int BOT_THRESHOLD = SCREEN_HEIGHT - TOP_THRESHOLD;

    private int score = 0;

    // Declare an object of type Bitmap
    Bitmap bitmapBob;
    private ArrayList<Rect> buildings;
    private ArrayList<Rect> trees;
    // start off not moving.
    MovDirHorizontal movH = MovDirHorizontal.NONE;
    MovDirVertical movV = MovDirVertical.NONE;

    // He can walk at 150 pixels per second
    float walkSpeedPerSecond = 150;

    // He starts 10 pixels from the left
    float bobXPosition = SCREEN_WIDTH - PLAYER_WIDTH;
    float bobYPosition = SCREEN_HEIGHT - PLAYER_HEIGHT;

    public Player(ScreenPlay.GameView gameView, Map map) {
        // Load Bob from his .png file

        bitmapBob = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.dinosaur);

        bitmapBob = Bitmap.createScaledBitmap(bitmapBob, PLAYER_WIDTH, PLAYER_HEIGHT, true);

        setBuildings(map.getBuildings());
        setTrees(map.getTrees());
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
                if (!isColliding(nextXPos, bobYPosition, buildings)) {
                    bobXPosition = nextXPos;
                }
                break;
            case LEFT:
                nextXPos = bobXPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(nextXPos, bobYPosition, buildings)) {
                    bobXPosition = nextXPos;
                }
                break;
            default:
                break;
        }

        switch (movV) {
            case DOWN:
                float nextYPos = bobYPosition + (walkSpeedPerSecond / fps);
                if (!isColliding(bobXPosition, nextYPos, buildings)) {
                    bobYPosition = nextYPos;
                }
                break;
            case UP:
                nextYPos = bobYPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(bobXPosition, nextYPos, buildings)) {
                    bobYPosition = nextYPos;
                }
                break;
            default:
                break;
        }
        eat();
    }

    private void eat(){
        ArrayList<Rect> eaten = collisions(bobXPosition, bobYPosition, trees);
        score += eaten.size();
        trees.removeAll(eaten);
    }

    private boolean isColliding(float xPos, float yPos, ArrayList<Rect> rects) {
        for (Rect rect : rects) {
            if (Rect.intersects(rect,
                    new Rect((int) xPos, (int) yPos, (int) xPos + PLAYER_WIDTH, (int) yPos +
                    PLAYER_HEIGHT))) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Rect> collisions(float xPos, float yPos, ArrayList<Rect> rects) {
        ArrayList<Rect> output = new ArrayList<>();

        for (Rect rect : rects) {
            if (Rect.intersects(rect,
                    new Rect((int) xPos, (int) yPos, (int) xPos + PLAYER_WIDTH, (int) yPos +
                            PLAYER_HEIGHT))) {
                output.add(rect);
            }
        }
        return output;
    }

    public void display(Canvas canvas, Paint paint) {
        // Draw bob at bobXPosition, 200 pixels
        canvas.drawBitmap(bitmapBob, bobXPosition, bobYPosition, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("Score:" + score, 20, 40, paint);
    }

    public void setTrees(ArrayList<Rect> trees) {
        this.trees = trees;
    }

    enum MovDirHorizontal {
        NONE, LEFT, RIGHT
    }

    enum MovDirVertical {
        NONE, UP, DOWN
    }

    public void setBuildings(ArrayList<Rect> buildings) {
        this.buildings = buildings;
    }
}
