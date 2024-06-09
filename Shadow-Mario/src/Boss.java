import bagel.Input;

import java.util.Properties;
import java.util.Random;

public class Boss extends Movement{
    private double health;
    private double activationRadius;
    private int attackFrame=100;
    private int fillSpeed=2;
    private int deadTime=70;
    private void die(){
        setY(getY()+fillSpeed);
        deadTime--;
    }

    /**
     * Determine if the boss is dead
     * @return true or false
     */
    public boolean isDead(){
        return health<=0 && deadTime<=0;
    }
    public Boss(int x, int y, Properties readProp) {
        super(x, y, readProp, "enemyBoss");
        health=Double.parseDouble(readProp.getProperty("gameObjects.enemyBoss.health"));
        activationRadius=Double.parseDouble(readProp.getProperty("gameObjects.enemyBoss.activationRadius"));
    }

    /**
     * damage boss' health
     * @param damage damage value
     */
    public void damage(double damage){
        if(health-damage>=0){
            health-=damage;
        }
    }

    /**
     * return the radius to activate boss to fireball
     * @return the activation radius for the boss
     */
    public double getActivationRadius(){
        return activationRadius;
    }
    /**
     * update with player
     * @param input input
     * @param player player
     */
    public FireBall updateWithTarget(Input input, Player player) {
        if(player.getHealth()>0){
            move(input);
        }
        if(CollisionDetector.isActive(player,this,activationRadius)){
            attackFrame--;
            if(attackFrame<=1){
                attackFrame=100;
                if(new Random().nextBoolean()){
                    return new FireBall(getX(), getY(), this, player, getReadProp());
                }else{
                    return null;
                }
            }
        }
        draw();
        return null;
    }

    /**
     * Method that moves the boss based on the player's movement.
     * @param input user input
     */
    public void move(Input input){
        if(health<=0){
            die();
        }
        super.move(input);
    }

    /**
     * return the boss's health
     * @return the boss' health
     */
    public double getHealth() {
        return health;
    }
}
