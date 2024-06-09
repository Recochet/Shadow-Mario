/**
 * Class that handles the collision detection.
 */
public class CollisionDetector {
    /**
     * Method that checks for a collision between the player and the given entity's position.
     * @param movement1 one movement
     * @param movement2 other movement
     */
    public static boolean isCollided(Movement movement1,Movement movement2) {
        return Math.sqrt(Math.pow(movement1.getX() - movement2.getX(), 2) +
                Math.pow(movement1.getY() - movement2.getY(), 2)) <= movement1.getRadius() + movement2.getRadius();
    }

    /**
     * Method that checks for a collision between the player and the given entity's position with activeDistance.
     * @param movement1 one movement
     * @param movement2 other movement
     * @param activeDistance the active distance
     */
    public static boolean isActive(Movement movement1,Movement movement2,double activeDistance) {
        return Math.sqrt(Math.pow(movement1.getX() - movement2.getX(), 2) +
                Math.pow(movement1.getY() - movement2.getY(), 2)) <= movement1.getRadius() + movement2.getRadius()+activeDistance;
    }
}