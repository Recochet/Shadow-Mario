import bagel.Input;
import java.util.Properties;

public class InvinciblePower extends Movement{
    private int speedY=-10;
    private int invinciblePowerTime;

    /**
     * Construct a new Invincible Power Object
     * Initialize the run time for Invincible Power
     * @param x x-coordinate for the Invincible Power
     * @param y y-coordinate for the Invincible Power
     * @param readProp read the properties for the Invincible Power from the property file
     */
    public InvinciblePower(int x, int y, Properties readProp) {
        super(x, y, readProp, "invinciblePower");
        invinciblePowerTime=Integer.parseInt(readProp.getProperty("gameObjects.invinciblePower.maxFrames"));
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
            invinciblePower4Player(player);
        }
        draw();
    }

    /**
     * make the player have invincible power with time
     * @param player player
     */
    private void invinciblePower4Player(Player player) {
        player.setInvinciblePowerTime(invinciblePowerTime);
    }
}
