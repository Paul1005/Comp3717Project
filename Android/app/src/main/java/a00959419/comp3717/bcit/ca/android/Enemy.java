package a00959419.comp3717.bcit.ca.android;

import android.content.Intent;
import android.graphics.Rect;

/**
 * Created by Andrew on 4/6/2017.
 */

class Enemy extends Dino {
    private Player player;

    Enemy(ScreenPlay.GameView gameView, Map map, ScreenPlay screen, Player player,
                 float spawnXPos, float spawnYPos) {
        super(gameView, map, R.drawable.t_rex, screen, spawnXPos, spawnYPos);
        movH = MovDirHorizontal.RIGHT;
        movV = MovDirVertical.DOWN;
        this.player = player;
        walkSpeedPerSecond = SCREEN_WIDTH / 12;
    }

    private void eat() {
        if (Rect.intersects(new Rect((int) xPosition, (int) yPosition, (int) xPosition + MY_WIDTH, (int) yPosition + MY_HEIGHT),
                new Rect((int) player.xPosition, (int) player.yPosition, (int) player.xPosition + MY_WIDTH, (int) player.yPosition + MY_HEIGHT))) {
            screen.finish();
            Intent intent = new Intent(screen.getApplicationContext(), ScreenEndGame.class);
            intent.putExtra("status", "You Got Eaten");
            intent.putExtra("music", R.raw.gameover);
            screen.startActivity(intent);
        }
    }

    @Override
    public void updatePos(long fps) {
        // If bob is moving (the player is touching the screen)
        // then move him to the right based on his target speed and the current fps.
        switch (movH) {
            case RIGHT:
                float nextXPos = xPosition + walkSpeedPerSecond / fps;
                if (!isColliding(nextXPos, yPosition, buildings) && !isAtEdge(nextXPos, yPosition)) {
                    xPosition = nextXPos;
                } else {
                    movH = MovDirHorizontal.LEFT;
                }
                break;
            case LEFT:
                nextXPos = xPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(nextXPos, yPosition, buildings) && !isAtEdge(nextXPos, yPosition)) {
                    xPosition = nextXPos;
                } else {
                    movH = MovDirHorizontal.RIGHT;
                }
                break;
            default:
                break;
        }

        switch (movV) {
            case DOWN:
                float nextYPos = yPosition + (walkSpeedPerSecond / fps);
                if (!isColliding(xPosition, nextYPos, buildings) && !isAtEdge(xPosition, nextYPos)) {
                    yPosition = nextYPos;
                } else {
                    movV = MovDirVertical.UP;
                }
                break;
            case UP:
                nextYPos = yPosition - (walkSpeedPerSecond / fps);
                if (!isColliding(xPosition, nextYPos, buildings) && !isAtEdge(xPosition, nextYPos)) {
                    yPosition = nextYPos;
                } else {
                    movV = MovDirVertical.DOWN;
                }
                break;
            default:
                break;
        }
        eat();
    }
}
