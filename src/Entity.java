import java.util.Arrays;

public class Entity {

    public EntityType type;
    public String name;
    private int maxHealth;
    private int maxEnergy;
    private int health;
    private int energy;
    private Location location;
    private int strength;
    
    private final Location defaultSpawn = Location.HALLWAY;
    private final int defaultStrength = 10;

    // location and strength not defined >> default
    public Entity(EntityType type, String name, int hp, int erg, Location location, int str) {

        this.type = type;
        this.name = name;
        this.health = hp; this.energy = erg;
        this.maxHealth = hp; this.maxEnergy = erg;
        this.location = location;
        this.strength = str;
        
    }

    public void attack(Entity e) {
        if (this.energy < 10) {return; }
        e.setHealth(e.getHealth() - this.strength);
        this.energy = this.energy - 10;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public boolean isFizzled() {
        return this.energy > 0;
    }
    
    public void setHealth(int newHealth) {
        if (newHealth >= 0) this.health = newHealth;
        else this.health = 0;
    }
    
    public void setEnergy(int newEnergy) {
        if (newEnergy >= 0) {
            this.energy = newEnergy;
        } else {
            this.energy = 0;
        }
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getMaxEnergy() {
        return this.maxEnergy;
    }

    public void regenerate() {
        this.health = this.maxHealth;
        this.energy = this.maxEnergy;
    }

    public Location getLocation() {
        return this.location;
    }

    // This might be implemented better
    private int getLocationIndex() {
        return Arrays.asList(Location.values()).indexOf(getLocation());
    }

    public Location moveForward() {
        Location nextRoom = Location.values()[getLocationIndex() + 1];
        this.location = nextRoom;
        return getLocation();
    }

    public Location moveBackward() {
        Location previousRoom = Location.values()[getLocationIndex() - 1];
        this.location = previousRoom;
        return getLocation();
    }

    public int getStrength() {
        return strength;
    }
}
