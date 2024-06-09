import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Platform extends Movement{
    //define the max x-coordinate that the platform can move
    private final int MAX_COORDINATE = 3000;

    /**
     * Construct a new Platform object
     * @param x x-coordinate of platform
     * @param y y-coordinate of platform
     * @param readProp read the properties for platform
     */
    public Platform(int x, int y, Properties readProp){
        super(x, y,readProp,"platform");
    }

    /**
     * update with player
     * @param input input
     * @param player player
     */
    public void updateWithTarget(Input input,Player player) {
        if(player.getHealth()>0){
            move(input);
        }
        draw();
    }

    /***
     * Method that moves the platform based on the player's movement.
     */
    public void move(Input input){
        if (input.isDown(Keys.RIGHT)){
            if(getX()>getSpeedX()) {
                setX(getX() - getSpeedX());
            }
        } else if (input.isDown(Keys.LEFT)){
            if (getX() < MAX_COORDINATE){
                setX(getX()+getSpeedX());
            }
        }
    }
}



