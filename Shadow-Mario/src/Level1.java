import bagel.Input;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Level1 extends Level{
    /**
     * Construct a new Level1 Object
     * Initialize the coin list and enemy list
     */
    public Level1(){
        coinList=new ArrayList<>();
        enemyList=new ArrayList<>();
    }
    /**
     * read a csv file
     * @param levelFileName csv
     * @param gameProps game's property
     * @throws FileNotFoundException if the file does not exist.
     */
    @Override
    public void init(String levelFileName, Properties gameProps) throws FileNotFoundException {
        String[][] levelContext=IOUtils.readCsv(levelFileName);
        for(String[] s:levelContext){
            if("PLATFORM".equals(s[0])){
                platform=new Platform(Integer.parseInt(s[1]),Integer.parseInt(s[2]),gameProps);
            }else if("PLAYER".equals(s[0])){
                player=new Player(Integer.parseInt(s[1]),Integer.parseInt(s[2]),gameProps);
            }else if("COIN".equals(s[0])){
                coinList.add(new Coin(Integer.parseInt(s[1]),Integer.parseInt(s[2]),gameProps));
            }else if("ENEMY".equals(s[0])){
                enemyList.add(new Enemy(Integer.parseInt(s[1]),Integer.parseInt(s[2]),gameProps));
            }else if("END_FLAG".equals(s[0])){
                endFlag=new EndFlag(Integer.parseInt(s[1]),Integer.parseInt(s[2]),gameProps);
            }
        }
        player.setDown2Y(platform.getY());
    }

    /**
     * drawObjects
     * @param input user input
     * @param game the game
     * @return a message
     */
    @Override
    public String drawObjects(Input input,ShadowMario game) {
        if(player.isDead()){
            return "lost";
        }
        if(endFlag.isCollided()){
            return "win";
        }
        platform.updateWithTarget(input,player);
        endFlag.updateWithTarget(input,player);
        for(Coin coin:coinList){
            coin.updateWithTarget(input,player);
        }
        for(Enemy enemy:enemyList){
            enemy.updateWithTarget(input,player);
        }
        player.updateWithTarget(input);
        game.drawScore(player);
        game.drawHealth(player);
        supportingPlayer();
        return "";
    }

    /**
     * supporting player
     */
    @Override
    protected void supportingPlayer() {
        player.setDown2YDefault();
    }
}