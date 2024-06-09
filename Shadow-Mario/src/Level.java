import bagel.Input;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Properties;

public abstract class Level {
    //define array to store coin
    protected List<Coin> coinList;
    //define array to store enemy
    protected List<Enemy> enemyList;
    protected Player player;
    protected EndFlag endFlag;
    protected Platform platform;

    /**
     * read a csv file
     * @param csvFile csv file
     * @param gameProps game's property
     * @throws FileNotFoundException if the file does not exist.
     */
    public abstract void init(String csvFile, Properties gameProps) throws FileNotFoundException;

    /**
     * drawObjects
     * @param input user input
     * @param game the game
     * @return a message
     */
    public abstract String drawObjects(Input input,ShadowMario game);

    /**
     * the abstract function: supporting player
     */
    protected abstract void supportingPlayer();
}
