import bagel.Input;

import java.util.Properties;
import java.util.Random;

public class FlyingPlatform extends Movement{
    private int maxRandomDisplacementX;
    private int randomSpeed;
    private int direct;
    private int distance;
    private int halfLength;
    private int halfHeight;

    /**
     * Construct a new FlyingPlatform object
     * Initialize the max random movement distance for flyingPlatform
     * Initialize the random speed of the movement
     * Initialize the length of flyingPlatform
     * Initialize the height of flyinPlatform
     * Initialize the direct for flyingPlatform true for 1 and false for -1
     * Initialize the start position of flyingPlatform
     * @param x the x-coordinate of the flyingPlatform
     * @param y the y-coordinate of the flyingPlatform
     * @param readProp read the properties for the flyingPlatform
     */
    public FlyingPlatform(int x, int y, Properties readProp) {
        super(x, y, readProp,"flyingPlatform");
        maxRandomDisplacementX=Integer.parseInt(readProp.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX"));
        randomSpeed=Integer.parseInt(readProp.getProperty("gameObjects.flyingPlatform.randomSpeed"));
        halfLength=Integer.parseInt(readProp.getProperty("gameObjects.flyingPlatform.halfLength"));
        halfHeight=Integer.parseInt(readProp.getProperty("gameObjects.flyingPlatform.halfHeight"));
        direct=new Random().nextBoolean()?1:-1;
        distance=0;
    }

    /**
     * return the HalfLength of the flying platform
     * @return the HalfLength of the flying platform
     */
    public int getHalfLength() {
        return halfLength;
    }

    /**
     * return the HalfHeight of the flying platform
     * @return the HalfHeight of the flying platform
     */
    public int getHalfHeight() {
        return halfHeight;
    }

    /**
     * update with player
     * @param input input
     * @param player player
     */
    public void updateWithTarget(Input input, Player player) {
        move(input,player);
        draw();
    }

    /**
     * Method that moves the flying platform based on the player's movement.
     * @param input user input
     * @param player the player
     */
    private void move(Input input,Player player){
        if(player.getHealth()>0){
            super.move(input);
        }
        setX(getX()+randomSpeed*direct);
        distance+=randomSpeed;
        if(distance>=maxRandomDisplacementX){
            distance=0;
            direct=-direct;
        }
    }
}
