public enum Location {
    HALLWAY     ("Hallway", "Dark Hallway, couple cobwebs hanging from the ceiling", false),
    STORAGE     ("Storage", "Granary is darklit and danger seem to seep from behind every cupboard", false),
    STAIRWAY    ("Stairway", "Torches burn happily, lighting up path to King's Chamber", true),
    KINGSROOM   ("King's Room", "King is peering through dimly lit chamber upon you from high stood throne", false);


    public final String name;
    public final String description;
    private boolean isLocked;
    private static Location[] locations = values();

    private Location(String name, String description, boolean isLocked) {
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
    }

    public Location next() {
        return locations[(this.ordinal()+1) % locations.length];
    }

    public Location previous() {
        return locations[(this.ordinal()-1) % locations.length];
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public void unlock() {
        this.isLocked = false;
    }

}