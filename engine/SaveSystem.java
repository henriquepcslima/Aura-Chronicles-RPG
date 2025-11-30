package engine;

import entities.Player;
import world.GameMap; 
import java.io.*;

public class SaveSystem {

    private static final String SAVE_FILE = "savegame.txt";

    
    public static void saveGame(Player p) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            writer.println("NAME:" + p.getName());
            writer.println("ROOM:" + p.getCurrentRoomId());
            writer.println("LEVEL:" + p.getLevel());
            writer.println("XP:" + p.getXp());
            writer.println("HP:" + p.getHealth());
            writer.println("MAXHP:" + p.getMaxHealth());
            writer.println("AP:" + p.getCurrentAP());
            writer.println("MAXAP:" + p.getMaxAP());
            writer.println("EVOLVED:" + p.isEvolved());
            writer.println("GOLD:" + p.getGold());
            
            
            for (String dead : p.getDefeatedEnemies()) {
                writer.println("DEAD:" + dead);
            }
            
            System.out.println("Jogo salvo com sucesso em " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    
    public static void loadGame(Player p, GameMap map) {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("Nenhum jogo salvo encontrado.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            p.clearInventory(); 
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length < 2) continue;

                String key = parts[0];
                String value = parts[1];

                switch (key) {
                    case "ROOM": p.move(Integer.parseInt(value)); break;
                    case "LEVEL": p.setLevel(Integer.parseInt(value)); break;
                    case "XP": p.setXp(Integer.parseInt(value)); break;
                    case "HP": p.setHealth(Integer.parseInt(value)); break;
                    case "MAXHP": p.setMaxHealth(Integer.parseInt(value)); break;
                    case "AP": p.setCurrentAP(Integer.parseInt(value)); break;
                    case "MAXAP": p.setMaxAP(Integer.parseInt(value)); break;
                    case "EVOLVED": 
                        boolean evolved = Boolean.parseBoolean(value);
                        p.setEvolved(evolved);
                        if(evolved && p.getSkill("aurasphere") == null) {
                            p.addSkill(new Skill("aurasphere", "Esfera de Aura Suprema", 40, 10));
                        }
                        break;
                        case "GOLD": p.setGold(Integer.parseInt(value)); break;
                    
                    
                    case "DEAD":
                        p.addDefeated(value);      
                        map.removeNpcByName(value); 
                        break;
                }
            }
            System.out.println("Jogo carregado! Inimigos derrotados foram removidos.");
            
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
    }
}