import bagel.Input;

import java.util.Properties;
import java.util.Random;

public class Enemy extends Movement{
    private double damage;
    private int maxRandomDisplacementX;
    private int randomSpeed;
    private int direct;
    private int distance;

    /**
     * Construct a new Enemy object
     * Initialize the value for damage when player collided with enemy
     * Initialize the random movement and speed for enemy
     * Initialize the direct for enemy true for 1 and false for -1
     * Initialize the start position for enemy
     * @param x
     * @param y
     * @param readProp
     */
    public Enemy(int x, int y, Properties readProp){
        super(x, y,readProp,"enemy");
        damage=Double.parseDouble(readProp.getProperty("gameObjects.enemy.damageSize"));
        maxRandomDisplacementX=Integer.parseInt(readProp.getProperty("gameObjects.enemy.maxRandomDisplacementX"));
        randomSpeed=Integer.parseInt(readProp.getProperty("gameObjects.enemy.randomSpeed"));
        direct=new Random().nextBoolean()?1:-1;
        distance=0;
    }

    /**
     * update with player
     * @param input input
     * @param player player
     */
    public void updateWithTarget(Input input, Player player){
        move(input,player);
        if(!isCollided()&&CollisionDetector.isCollided(this,player)&&player.getInvinciblePowerTime()==0){
            setCollided(true);
            player.decreaseHealth(damage);
        }
        draw();
    }

    /**
     * Method that moves the enemy based on the player's movement.
     * @param input user input
     * @param player current player
     */
    public void move(Input input,Player player){
        if(player.getHealth()>0){
            super.move(input);
        }
        setX(getX()+randomSpeed*direct);
        distance += randomSpeed;
        if(distance>=maxRandomDisplacementX){
            distance=0;
            direct=-direct;
        }
    }
}
