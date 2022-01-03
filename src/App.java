import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {

        // initialize variables
        List<Entity> entities = new ArrayList<>();
        List<Command> commands = new ArrayList<>();
        Player player = new Player(100, 100);
        int playedRounds = 0;

        // VALID GAME COMMANDS
        commands.add(new Command("quit") {
            @Override
            public void execute(String arg) {
                player.setHealth(0);
            }
        });

        commands.add(new Command("help") {
            @Override
            public void execute(String arg) {
                System.out.println("Welcome to my Dungeon Crawler!");
                System.out.println("---------------------------------");
                System.out.println("> command 'help' will show you what commands can you use to wander around dungeon");
                System.out.println("> command 'compass' will show you where in dungeon you are");
                System.out.println("> command 'move' will move your character through a dungeon");
                System.out.println("> command 'unlock' will combined with 'next' or 'back' will try to unlock doors behind or in front of you");
                System.out.println("> command 'quit' will exit the dungeon");
            }
        });

        commands.add(new Command("compass") {
            @Override
            public void execute(String arg) {
                System.out.println("You are in " + player.getLocation().name);
                System.out.println("You are in " + player.getLocation().description);
            }
        });

        commands.add(new Command("move") {
            @Override
            public void execute(String arg) {
                if (arg.equals("next")) {
                    if (!player.getLocation().next().isLocked()) {
                        player.moveForward();
                        System.out.println(player.getLocation().name);
                        System.out.println(player.getLocation().description);
                    } else {
                        System.out.println("Next location is locked!");
                    }
                } else if (arg.equals("back")) {
                    if (!player.getLocation().previous().isLocked()) {
                        player.moveBackward();
                        System.out.println(player.getLocation().name);
                        System.out.println(player.getLocation().description);
                    } else {
                        System.out.println("Next location is locked!");
                    }
                } else {
                    System.out.println("I don't understand where to move.");
                }
            }
        });

        commands.add(new Command("unlock") {
            @Override
            public void execute(String arg) {
                if (arg.equals("next")) {
                    Location nextRoom = player.getLocation().next();
                    if (nextRoom.isLocked()) {
                        //check for key
                        nextRoom.unlock();
                        System.out.println("Unlocked!");
                    } else {
                        System.out.println("Next location is not locked!");
                    }
                } else if (arg.equals("back")) {
                    Location previousRoom = player.getLocation().previous();
                    if (previousRoom.isLocked()) {
                        //check for key
                        previousRoom.unlock();
                        System.out.println("Unlocked!");
                    } else {
                        System.out.println("Next location is not locked!");
                    }
                } else {
                    System.out.println("I don't understand what to unlock.");
                }
            }
        });

        // INITIALIZE ENTITIES
        entities.add(new Entity(EntityType.ENEMY_NPC, "Frank", 20, 10, Location.STORAGE, 5));
        entities.add(new Entity(EntityType.FRIENDLY_NPC, "Anna", 1000, 100, Location.STAIRWAY, 100));
        entities.add(new Entity(EntityType.BOSS, "Noicyy", 80, 40, Location.KINGSROOM, 20));

        // MAIN GAME LOOP runs while player is alive
        while (player.isAlive()) {

            String[] input = getInput();
            String arg = "";
            if (input.length > 1) {
                arg = input[1];
            }

            for (Command command : commands) {
                if (command.name.equals(input[0])) {
                    clearScreen();
                    command.execute(arg);
                    break;
                }
            }
            playedRounds++;
        }

        System.out.println("Quitting after playing " + playedRounds + "rounds.");
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