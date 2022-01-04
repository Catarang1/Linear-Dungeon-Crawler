public class Player extends Entity {

    public boolean isStealthed;
    public boolean hasRelic;

    public Player (int hp, int erg) {
        super(EntityType.PLAYER, "John", 100, 100, Location.HALLWAY, 10);
        isStealthed = false;
        hasRelic = false;
    }

    public String toString() {
        String hpBar = "HP: [";
        int visualHitPoints = this.getHealth() / 10;
        int visualHealthBar = this.getMaxHealth() / 10;
        int visualEmptyHitPoints = visualHealthBar - visualHitPoints;

        for (int i = 0; i < visualHitPoints; i++) {hpBar += "|";}
        for (int i = 0; i < visualEmptyHitPoints; i++) {hpBar += ":";}
        hpBar += "]";
        
        String ergBar = "EG: [";
        int visualEnergy = this.getEnergy() / 10;
        int visualEnergyBar = this.getMaxEnergy() / 10;
        int visualEmptyEnergyPoints = visualEnergyBar - visualEnergy;

        for (int i = 0; i < visualEnergy; i++) {ergBar += "|";}
        for (int i = 0; i < visualEmptyEnergyPoints; i++) {ergBar += ":";}
        ergBar += "]";

        return "Player " + this.name + " in " + this.getLocation().name+ "\n" + hpBar + "\n" + ergBar;
    }
}
