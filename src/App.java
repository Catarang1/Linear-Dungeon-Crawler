import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {

        // initialize variables
        List<Entity> entities = new ArrayList<>();
        Map<String, Command> commands = new HashMap<String, Command>();
        Player player = new Player(100, 100);

        commands.put("quit", arg -> {
            player.setHealth(0);
        });

        commands.put("help", arg -> {
            System.out.println("Welcome to my Dungeon Crawler!");
            System.out.println("------------------------------");
            System.out.println("> command 'help' will show you what commands can you use to wander around dungeon");
            System.out.println("> command 'compass' will show you where in dungeon you are");
            System.out.println("> command 'move' will move your character through a dungeon");
            System.out.println(
                    "> command 'unlock' will combined with 'next' or 'back' will try to unlock doors behind or in front of you");
            System.out.println("> command 'quit' will exit the dungeon");
        });

        commands.put("compass", arg -> {
            System.out.println("You are in " + player.getLocation().name + "\n" + player.getLocation().description);
            System.out.println(player);
        });

        commands.put("move", arg -> {
            if (arg.equals("next")) {
                if (!player.getLocation().next().isLocked()) {
                    player.moveForward();
                    System.out.println(player.getLocation().name + "\n" + player.getLocation().description);
                } else {
                    System.out.println("Next location is locked!");
                }
            } else if (arg.equals("back")) {
                if (!player.getLocation().previous().isLocked()) {
                    player.moveBackward();
                    System.out.println(player.getLocation().name + "\n" + player.getLocation().description);
                } else {
                    System.out.println("Next location is locked!");
                }
            } else {
                System.out.println("I don't understand where to move.");
            }
        });

        commands.put("unlock", arg -> {
            if (arg.equals("next")) {
                Location nextRoom = player.getLocation().next();
                if (nextRoom.isLocked()) {
                    nextRoom.unlock();
                    System.out.println("Unlocked " + nextRoom.name + "!");
                } else {
                    System.out.println("Next location is not locked!");
                }
            } else if (arg.equals("back")) {
                Location previousRoom = player.getLocation().previous();
                if (previousRoom.isLocked()) {
                    previousRoom.unlock();
                    System.out.println("Unlocked " + previousRoom.name + "!");
                } else {
                    System.out.println("Previous location is not locked!");
                }
            } else {
                System.out.println("I don't understand what to unlock.");
            }
        });

        commands.put("sethp", arg -> {
            player.setHealth(Integer.parseInt(arg));
        });

        commands.put("regen", arg -> {
            player.regenerate();
            System.out.println("With sigh of relief, you skip a turn and refill your HP and Energy.");
        });

        commands.put("attack", arg -> {
            Entity attackable = null;
            for (Entity entity : entities) {
                if (entity.getLocation() == player.getLocation())
                    attackable = entity;
            }
            if (attackable != null) {
                player.attack(attackable);
                System.out.println("Player attacks " + attackable.name + " for " + player.getStrength());
                if (!attackable.isAlive()) {
                    entities.remove(attackable);
                    System.out.println(attackable.name + " dies.");
                }
            } else {
                System.out.println("Player swings through air hitting nothing.");
            }
        });

        // INITIALIZE ENTITIES
        entities.add(new Entity(EntityType.ENEMY_NPC, "Frank", 20, 10, Location.STORAGE, 5));
        entities.add(new Entity(EntityType.FRIENDLY_NPC, "Anna", 1000, 100, Location.STAIRWAY, 100));
        entities.add(new Entity(EntityType.BOSS, "Noicyy", 80, 40, Location.KINGSROOM, 20));

        // MAIN GAME LOOP runs while player is alive
        while (player.isAlive()) {

            // setups variables for command process
            String[] input = getInput();
            String cmd = (input.length != 0) ? input[0] : "";
            String arg = (input.length > 1) ? input[1] : "";

            // find command and execute it
            if (commands.keySet().contains(cmd)) {
                clearScreen();
                commands.get(cmd).execute(arg);
            }

            // each enemy that is in same room as player
            // either attacks player or regenerates depending on their energy if enemy
            for (Entity entity : entities) {
                /*if (!entity.isAlive()) { remove from list of entities, needs iterated approach}*/;
                if (entity.getLocation() != player.getLocation()) continue;
                if (entity.getType() == EntityType.ENEMY_NPC || entity.getType() == EntityType.BOSS) {
                    System.out.println(entity.name + " is in same room as player");
                
                    if (entity.isFizzled()) {
                        System.out.println(entity.name + " looks tired and does not swing weapon this round. " + entity.name + " has " + entity.getEnergy() + " energy.");
                        entity.regenerate();
                    } else {
                        entity.attack(player);
                        System.out.println(entity.name + " hits player for " + entity.getStrength() + ".");
                    }
                }
            }

        }
        s.close();
    }

    public static String[] getInput() {
        String input = s.nextLine();
        return input.split(" ");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}