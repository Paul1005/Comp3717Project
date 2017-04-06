package a00959419.comp3717.bcit.ca.android;

/**
 * Created by Andrew on 4/6/2017.
 */

public class Enemy extends Dino {
    public Enemy (ScreenPlay.GameView gameView, Map map) {
        super(gameView, map, R.drawable.t_rex);
        movH = MovDirHorizontal.LEFT;
        movV = MovDirVertical.UP;
        walkSpeedPerSecond = SCREEN_WIDTH/12;
    }
}
