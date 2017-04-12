package a00959419.comp3717.bcit.ca.android;

import android.content.Intent;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Andrew on 3/22/2017.
 */

class Player extends Dino{


    private static final int LEFT_THRESHOLD = SCREEN_WIDTH / 5;
    private static final int RIGHT_THRESHOLD = SCREEN_WIDTH - LEFT_THRESHOLD;
    private static final int TOP_THRESHOLD = SCREEN_HEIGHT / 4;
    private static final int BOT_THRESHOLD = SCREEN_HEIGHT - TOP_THRESHOLD;

    private int score = 0;

    Player(ScreenPlay.GameView gameView, Map map, ScreenPlay screen, float spawnXPos,
                  float spawnYPos) {
        super(gameView, map, R.drawable.dinosaur, screen, spawnXPos, spawnYPos);
    }

    void changeMove(MotionEvent motionEvent) {
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

    @Override
    public void updatePos(long fps) {
        // If bob is moving (the player is touching the screen)
        // then move him to the right based on his target speed and the current fps.
        super.updatePos(fps);
        eat();

        if (trees.size() == 0) {
            screen.finish();
            Intent intent = new Intent(screen.getApplicationContext(), EndGameOver.class);
            intent.putExtra("status", "You Won!");
            intent.putExtra("music", R.raw.victory);
            screen.startActivity(intent);
        }
    }

    private void eat() {
        ArrayList<Rect> eaten = collisions(xPosition, yPosition, trees);
        score += eaten.size();
        trees.removeAll(eaten);
    }
}
