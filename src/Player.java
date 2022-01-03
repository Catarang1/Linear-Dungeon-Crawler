public class Player extends Entity {

    public boolean isStealthed;
    public boolean hasRelic;

    public Player (int hp, int erg) {
        super(EntityType.PLAYER, "John", 100, 100, Location.HALLWAY, 10);
        isStealthed = false;
        hasRelic = false;
    }    
}
