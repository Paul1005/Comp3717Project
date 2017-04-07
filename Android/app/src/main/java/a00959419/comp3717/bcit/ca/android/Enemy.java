package a00959419.comp3717.bcit.ca.android;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Andrew on 4/6/2017.
 */

public class Enemy extends Dino {
    Player player;

    public Enemy (ScreenPlay.GameView gameView, Map map, ScreenPlay screen, Player player) {
        super(gameView, map, R.drawable.t_rex, screen);
        movH = MovDirHorizontal.RIGHT;
        movV = MovDirVertical.DOWN;
        this.player = player;
        walkSpeedPerSecond = SCREEN_WIDTH/12;
        xPosition = 0;
        yPosition = 0;
    }

    @Override
    public void updatePos(long fps) {
        // If bob is moving (the player is touching the screen)
        // then move him to the right based on his target speed and the current fps.
        super.updatePos(fps);
        eat();
    }

    private void eat() {
        if (Rect.intersects(new Rect((int)xPosition, (int)yPosition, (int)xPosition+MY_WIDTH, (int)yPosition + MY_HEIGHT),
                new Rect((int)player.xPosition, (int)player.yPosition, (int)player.xPosition+MY_WIDTH, (int)player.yPosition + MY_HEIGHT)))
            screen.finish();
    }
}
