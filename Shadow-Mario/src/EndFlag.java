import bagel.Input;

import java.util.Properties;

public class EndFlag extends Movement {
    /**
     * Construct a new EndFlag object
     * @param x x-coordinate for the end flag
     * @param y y-coordinate for the end flag
     * @param properties read the properties for endFlag
     */
    public EndFlag(int x, int y, Properties properties) {
        super(x, y, properties,"endFlag");
    }
    /**
     * update with player
     * @param input input
     * @param player player
     */
    public void updateWithTarget(Input input,Player player){
        if(player.getHealth()>0){
            move(input);
        }
        if(!isCollided()&&CollisionDetector.isCollided(this,player)){
            setCollided(true);
        }
        draw();
    }
}
