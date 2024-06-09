import bagel.Input;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Level2 extends Level{
    private List<FlyingPlatform> flyingPlatformList;
    private List<DoubleScorePower> doubleScorePowerList;
    private List<InvinciblePower> invinciblePowerList;

    /**
     * Construct a new Level2 Object
     * Initialize Lists for coin, enemy, doubleScorePower, invinciblePowerList and flyingPlatform
     */
    public Level2(){
        coinList=new ArrayList<>();
        enemyList=new ArrayList<>();
        doubleScorePowerList=new ArrayList<>();
        invinciblePowerList=new ArrayList<>();
        flyingPlatformList=new ArrayList<>();
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
            }else if("FLYING_PLATFORM".equals(s[0])){
                flyingPlatformList.add(new FlyingPlatform(Integer.parseInt(s[1]),Integer.parseInt(s[2]),gameProps));
            }else if("DOUBLE_SCORE".equals(s[0])){
                doubleScorePowerList.add(new DoubleScorePower(Integer.parseInt(s[1]),Integer.parseInt(s[2]),gameProps));
            }else if("INVINCIBLE_POWER".equals(s[0])){
                invinciblePowerList.add(new InvinciblePower(Integer.parseInt(s[1]),Integer.parseInt(s[2]),gameProps));
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
        for(DoubleScorePower doubleScorePower:doubleScorePowerList){
            doubleScorePower.updateWithTarget(input,player);
        }
        for(InvinciblePower invinciblePower:invinciblePowerList){
            invinciblePower.updateWithTarget(input,player);
        }
        for(FlyingPlatform flyingPlatform:flyingPlatformList){
            flyingPlatform.updateWithTarget(input,player);
        }
        player.updateWithTarget(input);
        game.drawScore(player);
        game.drawHealth(player);
        supportingPlayer();
        return "";
    }

    /**
     * the abstract function: supporting player
     */
    @Override
    protected void supportingPlayer() {
        FlyingPlatform flyingPlatform = null;
        int minDist = Integer.MAX_VALUE;
        for (FlyingPlatform platform : flyingPlatformList) {
            int left = platform.getX() - platform.getHalfLength();
            int right = platform.getX() + platform.getHalfLength();
            if (left <= player.getX() && player.getX() <= right && platform.getY() >= player.getY() + platform.getHalfHeight()) {
                int dist =  platform.getY()-player.getY() + platform.getHalfHeight() ;
                if (minDist > dist) {
                    flyingPlatform = platform;
                    minDist = dist;
                }
            }
        }
        if (flyingPlatform != null) {
            player.setDown2Y(flyingPlatform.getY() - flyingPlatform.getHalfHeight());
        } else {
            player.setDown2YDefault();
        }
    }

}
