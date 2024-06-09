import bagel.Input;

import java.util.Properties;

public class Coin extends Movement{
    private int speedY=-10;
    private int value;

    /**
     * Construct a new Coin object
     * Initialize the value for each coin read from the property file
     * @param x the x-coordinate of the coin
     * @param y the y-coordinate of the coin
     * @param readProp read the properties for coin
     */
    public Coin(int x, int y, Properties readProp){
        super(x, y,readProp, "coin");
        value=Integer.parseInt(readProp.getProperty("gameObjects.coin.value"));
    }

    /**
     * update with player
     * @param input input
     * @param player player
     */
    public void updateWithTarget(Input input,Player player){
        if(isCollided()){
            setY(getY()+speedY);
        }else{
            if(player.getHealth()>0){
                move(input);
            }
        }
        if(!isCollided()&&CollisionDetector.isCollided(this,player)){
            setCollided(true);
            rewardPlayer(player);
        }
        draw();
    }

    /**
     * when collided, reward the player
     * @param player the player
     */
    public void rewardPlayer(Player player){
        player.addScore(value);
    }
}


