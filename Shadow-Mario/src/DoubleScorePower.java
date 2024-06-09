import bagel.Input;

import java.util.Properties;

public class DoubleScorePower extends Movement{
    private int speedY=-10;
    private int doubleScorePowerTime;

    /**
     * Construct a new DoubleScorePower object
     * Initialize the run time for doubleScorePower read from the property file
     * @param x x-coordinate for DoubleScorePower
     * @param y y-coordinate for DoubleScorePower
     * @param readProp read the properties for DoubleScorePower
     */
    public DoubleScorePower(int x, int y, Properties readProp) {
        super(x, y, readProp, "doubleScore");
        doubleScorePowerTime=Integer.parseInt(readProp.getProperty("gameObjects.doubleScore.maxFrames"));
    }

    /**
     * update with player
     * @param input input
     * @param player player
     */
    public void updateWithTarget(Input input, Player player) {
        if(isCollided()){
            setY(getY()+speedY);
        }else{
            if(player.getHealth()>0){
                move(input);
            }
        }
        if(!isCollided()&&CollisionDetector.isCollided(this,player)){
            setCollided(true);
            doubleScorePower4Player(player);
        }
        draw();
    }

    /**
     * make the player have doubleScore power with time
     * @param player player
     */
    private void doubleScorePower4Player(Player player) {
        player.setDoubleScorePowerTime(doubleScorePowerTime);
    }
}
