import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public class Player extends Movement{
    private int score;
    private int upSpeed;
    private int fillSpeed=2;
    private int deadTime=70;
    private boolean isDown;
    private boolean isUp;
    private int lowerYCoordinate;
    private int down2Y;
    private int invinciblePowerTime;
    private int doubleScorePowerTime;

    /**
     * show the die of mario
     */
    private void die(){
        setY(getY() + fillSpeed);
        deadTime --;
    }

    /**
     * damage by fireball
     * @param damage damage value
     */
    public void damage (double damage){
        health = Math.max(health - damage, 0);
    }

    /**
     * return the duration of the invincibility power
     * @return the duration for invincible power
     */
    public int getInvinciblePowerTime() {
        return invinciblePowerTime;
    }

    /**
     * Sets the duration of the invincibility power
     * @param invinciblePowerTime the duration
     */
    public void setInvinciblePowerTime(int invinciblePowerTime) {
        this.invinciblePowerTime = invinciblePowerTime;
    }

    /**
     * define the player's health
     */
    private double health;

    /**
     * Constructor a new Player object
     * Initialize the LowerYCoordinate to y
     * Initialize the isUp and isDown to false
     * Initialize the health of player read from the property file
     * @param x the x-coordinate for the player
     * @param y the y-coordinate for the player
     * @param readProp read the properties for the player
     */
    public Player(int x, int y, Properties readProp){
        super(x, y,readProp, "player");
        lowerYCoordinate = y;
        isUp = false;
        isDown = false;
        health = Double.parseDouble(readProp.getProperty("gameObjects.player.health"));
    }

    /**
     * add score with coin
     * @param v value
     */
    public void addScore(int v){
        if(doubleScorePowerTime>0){
            score += 2*v;
        }else{
            score += v;
        }
    }

    /**
     * return the score of the player
     * @return the score of the player
     */
    public int getScore(){
        return score;
    }

    /**
     * move in game board
     * @param input user input
     */
    @Override
    public void move(Input input) {
        if(health <= 0){
            die();
            return;
        }
        // the invinciblePowerTime
        if(invinciblePowerTime > 0){
            invinciblePowerTime --;
        }
        // the doubleScorePowerTime
        if(doubleScorePowerTime > 0){
            doubleScorePowerTime --;
        }
        if(isDead()){
            setY(getY() + fillSpeed);
            return;
        }
        if (input.isDown(Keys.LEFT)) {
            setImage(new Image(this.getReadProp().getProperty("gameObjects.player.imageLeft")));
        } else if (input.isDown(Keys.RIGHT)) {
            setImage(new Image(this.getReadProp().getProperty("gameObjects.player.imageRight")));
        } else if(input.wasPressed(Keys.UP)&&upSpeed==0){
            isUp = true;
            isDown = false;
            upSpeed =- 20;
        }
        if(isUp) {
            setY(getY() + upSpeed);
            upSpeed ++;
            if(upSpeed == 0){
                isDown = true;
                isUp = false;
            }
        }
        if(isDown){
            if(getY() == down2Y){
                isDown = false;
                upSpeed = 0;
                return;
            }
            if(getY() + upSpeed>down2Y){
                setY(down2Y);
                upSpeed=0;
                isDown=false;
            }else{
                setY(getY() + upSpeed);
            }
            upSpeed++;
        }
    }

    /**
     * determine if the player is dead
     * @return true while health and deadTime less than 0, false otherwise
     */
    public boolean isDead(){
        return health <= 0 && deadTime < 0;
    }

    /**
     * return the player's health
     * @return the player's health
     */
    public double getHealth(){
        return health;
    }

    /**
     * Methods that decrease the health of player
     * @param damage the enemy's damage
     */
    public void decreaseHealth(double damage) {
        health -= damage;
    }

    /**
     * make the mario move by the input and draw the mario
     * @param input user input
     */
    public void updateWithTarget(Input input) {
        move(input);
        draw();
    }
    /**
     * Sets the y-coordinate for the player's downward movement.
     * If the player is not moving up, sets the player to be moving down.
     * @param y the player's current y-coordinate
     */
    public void setDown2Y(int y){
        if(!isUp){
            isDown = true;
        }
        down2Y = y;
    }

    /**
     * Sets the y-coordinate for the player's downward movement to the default lower y-coordinate.
     * If the player is not moving up, sets the player to be moving down.
     */
    public void setDown2YDefault(){
        if(!isUp){
            isDown=true;
        }
        down2Y=lowerYCoordinate;
    }

    /**
     * Set the duration for the doubleScorePower for player
     * @param doubleScorePowerTime the duration of the doubleScorePower
     */
    public void setDoubleScorePowerTime(int doubleScorePowerTime) {
        this.doubleScorePowerTime=doubleScorePowerTime;
    }
}

