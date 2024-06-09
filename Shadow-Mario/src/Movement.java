import bagel.Image;
import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public abstract class Movement {
    //the x-coordinate
    private int x;
    //the y-coordinate
    private int y;
    //the speed of each entity
    private int speedX;
    private String type;
    private Properties readProp;
    private Image image;
    private double radius;
    private boolean isCollided;
    /**
     * Constructs a new Movement object
     * @param x x coordinate
     * @param y y coordinate
     * @param readProp
     * @param type the type of each entity
     */
    public Movement(int x, int y, Properties readProp, String type){
        this.x = x;
        this.y = y;
        this.readProp = readProp;
        this.type = type;
        this.speedX = Integer.parseInt(readProp.getProperty("gameObjects."+type+".speed","0"));
        if(!"player".equals(type)){
            image=new Image(readProp.getProperty("gameObjects."+type+".image"));
        }else{
            image=new Image(readProp.getProperty("gameObjects.player.imageRight"));
        }
        isCollided=false;
        radius=Double.parseDouble(readProp.getProperty("gameObjects."+type+".radius","0"));
    }

    /**
     * move in board
     * @param input user input
     */
    public void move(Input input){
        if(input.isDown(Keys.LEFT)||input.isDown(Keys.A)){
            x+=speedX;
        } else if (input.isDown(Keys.RIGHT)||input.isDown(Keys.D)){
            x-=speedX;
        }
    }

    /**
     * To determine if the entity is collided
     * @return true if radius is collided, false otherwise
     */
    public boolean isCollided() {
        return isCollided;
    }

    /**
     * Set the collided status for each entity
     * @param collided true if the entity has collided, false otherwise
     */
    public void setCollided(boolean collided) {
        isCollided = collided;
    }

    /**
     * draw in board
     */
    public void draw(){
        image.draw(x,y);
    }

    /**
     * return the x-coordinate of each entity
     * @return the x-coordinate of each entity
     */
    public int getX() {
        return x;
    }

    /**
     * Initialize the x-coordinate of each entity
     * @param x x-coordinate of each entity
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * return the y-coordinate of each entity
     * @return the y-coordinate of each entity
     */
    public int getY() {
        return y;
    }

    /**
     * initialize the y-coordinate of each entity
     * @param y y-coordinate of each entity
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * return the horizontal speed of each entity
     * @return the horizontal speed of each entity
     */
    public int getSpeedX() {
        return speedX;
    }

    /**
     * read the properties for each entity from the property file
     * @return the corresponding properties
     */
    public Properties getReadProp() {
        return readProp;
    }

    /**
     * set the image
     * @param image image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * get the radius
     * @return radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * damage for boss or player
     * @param damage damage value
     */
    public void damage(double damage) {
    }
}
