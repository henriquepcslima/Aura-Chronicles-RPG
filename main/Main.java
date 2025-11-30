package main;

import entities.Player;
import world.GameMap;
import engine.CommandParser;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameMap map = new GameMap();
        CommandParser parser = new CommandParser();
        Player player = new Player("Ardyn", 100); 

        System.out.println("=== AURA CHRONICLES ===");
        parser.processCommand("olhar", player, map);

        while(true) {
            System.out.print("\n> ");
            String input = scanner.nextLine();
            parser.processCommand(input, player, map);
        }
    }
}
