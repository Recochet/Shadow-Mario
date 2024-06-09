import bagel.Input;

import java.util.Properties;

public class FireBall extends Movement{
    private Movement source;
    private Movement target;
    //define the fireball damage size
    private double damage;
    private int direct;

    /**
     * Construct a new FireBall Object
     * Initialize the direction of fireball
     * Initialize the value damage that caused by the fireball
     * @param x x-coordinate for the fireball
     * @param y y-coordinate for the fireball
     * @param source where the fireball from
     * @param target when collided the target will get damage
     * @param readProp read the properties for fireball
     */
    public FireBall(int x, int y,Movement source,Movement target, Properties readProp) {
        super(x, y, readProp, "fireball");
        if(source.getX()>target.getX()){
            direct =- 1;
        }else{
            direct = 1;
        }
        damage=Double.parseDouble(readProp.getProperty("gameObjects.fireball.damageSize"));
        this.source=source;
        this.target=target;
    }

    /**
     * update with target
     */
    public void updateWithTarget(){
        if(isCollided()){
            return;
        }
        move(null);
        if(CollisionDetector.isCollided(this,target)){
            setCollided(true);
            target.damage(damage);
        }
        draw();
    }

    /**
     * Method that moves the fireball based on the game.
     * @param input user input
     */
    @Override
    public void move(Input input) {
        setX(getX()+direct*getSpeedX());
    }
}
