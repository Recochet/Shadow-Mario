import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Colour;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Properties;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 1, 2024
 * @version 2.0
 * Please enter your name below
 * @author YILIN GUAN
 */
public class ShadowMario extends AbstractGame {
    private final Image BACKGROUND_IMAGE;
    private Properties gameProps;
    private Properties messageProps;
    private boolean gameStart;
    private String levelFileName;
    private String endMessage;
    private Level level;

    /**
     * The constructor
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
                Integer.parseInt(game_props.getProperty("windowHeight")),
                message_props.getProperty("title"));
        gameProps=game_props;
        messageProps=message_props;
        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));
        initGame();
    }
    private void initGame(){
        gameStart=false;
        levelFileName="";
        endMessage="";
    }
    /**
     * draw a string
     * @param x coordinate
     * @param y coordinate
     * @param msg the message
     * @param fontSize font's size
     * @param isCentre (x,y) is the centre
     */
    private void drawString(int x,int y,String msg,int fontSize,boolean isCentre){
        Font font = new Font(gameProps.getProperty("font"),fontSize);
        int width= (int) (font.getWidth(msg)/2);
        if(!isCentre){
            width=0;
        }
        font.drawString(msg,x-width,y);
    }
    /**
     * draw a string in color
     * @param x coordinate
     * @param y coordinate
     * @param msg the message
     * @param fontSize font's size
     * @param isCentre (x,y) is the centre
     * @param color the text's color
     */
    private void drawStringInColor(int x, int y, String msg, int fontSize, boolean isCentre, Colour color){
        Font font = new Font(gameProps.getProperty("font"),fontSize);
        int width= (int) (font.getWidth(msg)/2);
        if(!isCentre){
            width=0;
        }
        DrawOptions drawOptions=new DrawOptions();
        drawOptions.setBlendColour(color);
        font.drawString(msg,x-width,y,drawOptions);
    }
    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    /**
     * draw the player score
     * @param player player
     */
    void drawScore(Player player){
        drawString(Integer.parseInt(gameProps.getProperty("score.x")),
                Integer.parseInt(gameProps.getProperty("score.y")),
                messageProps.getProperty("score")+player.getScore(),
                Integer.parseInt(gameProps.getProperty("score.fontSize")),
                false);
    }

    /**
     * draw the player health
     * @param player player
     */
    void drawHealth(Player player){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        drawString(Integer.parseInt(gameProps.getProperty("playerHealth.x")),
                Integer.parseInt(gameProps.getProperty("playerHealth.y")),
                messageProps.getProperty("health")+decimalFormat.format(player.getHealth()*100),
                Integer.parseInt(gameProps.getProperty("playerHealth.fontSize")),
                false);

    }

    /**
     * draw boss's health in red
     * @param boss boss
     */
    protected void drawBossHealth(Boss boss){
        if(boss==null){
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        drawStringInColor(Integer.parseInt(gameProps.getProperty("enemyBossHealth.x")),
                Integer.parseInt(gameProps.getProperty("enemyBossHealth.y")),
                messageProps.getProperty("health")+decimalFormat.format(boss.getHealth()*100),
                Integer.parseInt(gameProps.getProperty("enemyBossHealth.fontSize")),
                false,
                Colour.RED);
    }

    /**
     * draw the start scene
     */
    private void drawStartScene(){
        drawString(Integer.parseInt(gameProps.getProperty("title.x")),
                Integer.parseInt(gameProps.getProperty("title.y")),
                messageProps.getProperty("title"),
                Integer.parseInt(gameProps.getProperty("title.fontSize")),
                false);
        drawString(Integer.parseInt(gameProps.getProperty("windowWidth"))/2,
                Integer.parseInt(gameProps.getProperty("instruction.y")),
                messageProps.getProperty("instruction"),
                Integer.parseInt(gameProps.getProperty("instruction.fontSize")),
                true);
    }

    /**
     * read file to game
     * @param levelFileName level file's name
     */
    private void readFile2Game(String levelFileName){
        try {
            level.init(levelFileName,gameProps);
        } catch (FileNotFoundException e) {
            System.exit(-1);
        }
    }

    /**
     * Performs a state update of the selected level.
     * Allows the game to exit when the escape key is pressed.
     * Handle screen navigation between levels and instruction pages here.
     */
    @Override
    protected void update(Input input) {
        // close window
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        // the start scene
        if(!gameStart) {
            drawStartScene();
            if(input.wasPressed(Keys.NUM_1)){
                levelFileName=gameProps.getProperty("level1File");
                level=new Level1();
            } else if (input.wasPressed(Keys.NUM_2)) {
                levelFileName=gameProps.getProperty("level2File");
                level=new Level2();
            } else if (input.wasPressed(Keys.NUM_3)) {
                levelFileName=gameProps.getProperty("level3File");
                level=new Level3();
            }
            if(!levelFileName.equals("")){
                gameStart=true;
                readFile2Game(levelFileName);
            }
        }else{
            if(!"".equals(endMessage)){
                String propertyKey="gameWon";
                if(!"win".equals(endMessage)){
                    propertyKey="gameOver";
                }
                String msg=messageProps.get(propertyKey).toString();
                drawString(Integer.parseInt(gameProps.getProperty("windowWidth"))/2,
                        Integer.parseInt(gameProps.getProperty("message.y")),
                        msg,
                        Integer.parseInt(gameProps.getProperty("message.fontSize")),
                        true);
                if(input.wasPressed(Keys.SPACE)){
                    initGame();
                }
                return;
            }
            endMessage=level.drawObjects(input,this);
        }
    }

    /**
     * get the property
     * @return game's property
     */
    public Properties getGameProperty() {
        return gameProps;
    }
}
