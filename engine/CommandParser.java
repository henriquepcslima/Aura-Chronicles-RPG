package engine;

import entities.Player;
import world.GameMap;
import world.Room;
import engine.Item;

public class CommandParser {
    private CombatEngine combat = new CombatEngine();

    public void processCommand(String input, Player player, GameMap map) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Por favor, digite um comando.");
            return;
        }

        String[] parts = input.trim().toLowerCase().split(" ", 2);
        String command = parts[0];
        String arg = (parts.length > 1) ? parts[1] : "";

        switch(command) {
            case "ir":
            case "andar":
                move(arg, player, map);
                break;
            case "olhar":
            case "ver":
                look(player, map);
                break;
            case "saidas":
                System.out.println(Color.gold(map.getRoom(player.getCurrentRoomId()).getExitString()));
                break;
            case "atacar":
                fight(arg, player, map);
                break;
            case "usar":
                handleUse(arg, player, map);
                break;
            case "pegar":
                takeItem(player, map);
                break;
            case "visao":
                handleVision(player, map);
                break;
            case "compreender":
                handleFinalBoss(player, map);
                break;
            case "mochila":
            case "inventario":
                player.printInventory();
                break;
            case "ficha":
            case "status":
                player.printStatus();
                break;
            case "habilidades":
                player.printSkills();
                break;
            case "salvar":
                SaveSystem.saveGame(player);
                break;
            case "carregar":
                SaveSystem.loadGame(player, map); 
                look(player, map); 
                break;
            case "curar":
                handleHeal(player);
                break;
            case "comprar":
                buyItem(arg, player);
                break;
            
            case "ajuda":
            case "help":
                printHelp();
                break;

            case "sair":
                System.exit(0);
                break;
            
            default:
                System.out.println("Nao entendi o comando '" + command + "'. (Digite 'ajuda')");
        }
    }

    
    private void printHelp() {
        System.out.println(Color.gold("\n=== AURA CHRONICLES: GUIA ==="));
        
        System.out.println(Color.cyan("--- OBJETIVO ---"));
        System.out.println("Voce e Ardyn. Junto com Riolu, viaje por Johto, derrote os");
        System.out.println("Lideres de Ginasio e impeca o Darkrai no Monte Silver.");

        System.out.println(Color.cyan("\n--- COMANDOS BASICOS ---"));
        System.out.println(" ir [direcao] : Mover-se (norte, sul, leste, oeste)");
        System.out.println(" olhar        : Ver detalhes da sala e inimigos");
        System.out.println(" atacar       : Lutar com inimigos");
        System.out.println(" usar [algo]  : Usar Item (pocao) ou Skill (quebra-rocha)");
        System.out.println(" pegar        : Pegar itens do chao");
        
        System.out.println(Color.cyan("\n--- COMANDOS UTEIS ---"));
        System.out.println(" curar        : Recuperar vida (apenas em Cidades)");
        System.out.println(" mapa         : Ver o mapa do mundo");
        System.out.println(" salvar       : Salvar o jogo");
        System.out.println(" ficha        : Ver seu Nivel e XP");
        
        System.out.println(Color.gold("============================="));
    }

    private void handleVision(Player p, GameMap map) {
        if (p.getItem("insignia_simples") != null) p.setTemporalVision(true);
        if (!p.hasTemporalVision()) { System.out.println("Precisa da Insignia."); return; }
        Room r = map.getRoom(p.getCurrentRoomId());
        System.out.println(Color.cyan("--- VISAO TEMPORAL ---"));
        if (r.hasHiddenSecret()) {
            System.out.println(Color.gold(r.getHiddenDescription()));
            if (p.getCurrentRoomId() == 114 && r.getNextRoomId("norte") == -1) {
                   System.out.println(Color.green(">>> Passagem secreta aberta!"));
                   r.addExit("norte", 116); 
            }
        } else { System.out.println("Nada aqui."); }
    }

    private void handleUse(String arg, Player p, GameMap map) {
        if (arg.equals("visao")) { handleVision(p, map); return; }
        if (arg.equals("quebra-rocha")) {
            Room r = map.getRoom(p.getCurrentRoomId());
            if (p.getSkill("quebra-rocha") != null && r.getId() == 121 && r.getNextRoomId("cima") == -1) {
                System.out.println(Color.gold(">>> Caminho aberto!"));
                r.addExit("cima", 122);
            } else { System.out.println("Nada acontece."); }
            return;
        }
        if (arg.equals("insignia_nevoa")) {
            if (p.getCurrentRoomId() == 119 && p.getItem("insignia_nevoa") != null) {
                System.out.println(Color.gold(">>> Voo de Lugia!"));
                p.move(120); look(p, map);
            } else { System.out.println("Nada acontece."); }
            return;
        }
        if (arg.equals("lagrima_completa")) {
            if (p.getCurrentRoomId() == 122 && p.getItem("lagrima_completa") != null) {
                System.out.println(Color.cyan(">>> Portal aberto!"));
                map.getRoom(122).addExit("portal", 123);
            } else { System.out.println("Nada acontece."); }
            return;
        }
        Item item = p.getItem(arg);
        if (item != null) {
            if (item.getHealAmount() > 0) {
                p.setHealth(Math.min(p.getHealth() + item.getHealAmount(), p.getMaxHealth()));
                System.out.println(Color.green("Usou " + item.getName()));
                p.removeItem(item); 
            } else { System.out.println("Nao usavel."); }
            return; 
        }
        fight(arg, p, map);
    }

    private void handleFinalBoss(Player p, GameMap map) {
        Room r = map.getRoom(p.getCurrentRoomId());
        if (r.getId() == 123 && r.hasEnemy() && r.getNpc().getName().contains("Darkrai")) {
            System.out.println(Color.gold("\n>>> FINAL PACIFISTA ALCANCADO <<<"));
            System.out.println(Color.green("*** FIM DE JOGO ***"));
            r.setNpc(null);
        } else { System.out.println("Nada para compreender."); }
    }

    private void handleHeal(Player p) {
        if (p.getCurrentRoomId() == 102 || p.getCurrentRoomId() == 105) { 
            System.out.println(Color.green("Curado!"));
            p.setHealth(p.getMaxHealth());
            p.setCurrentAP(p.getMaxAP());
        } else { System.out.println("Va a uma cidade."); }
    }

    private void buyItem(String item, Player p) {
        if (item.equals("pocao") && p.spendGold(50)) {
            p.addItem(new Item("pocao", "Cura 30 HP", 30, false));
            System.out.println(Color.green("Comprou Pocao!"));
        } else { System.out.println("Loja: pocao (50g)."); }
    }

    private void move(String dir, Player p, GameMap map) {
        int next = map.getRoom(p.getCurrentRoomId()).getNextRoomId(dir);
        if (next != -1) { p.move(next); checkForRandomEncounter(map.getRoom(next)); look(p, map); } 
        else { System.out.println("Caminho bloqueado."); }
    }

    private void checkForRandomEncounter(Room room) {
        int id = room.getId();
        if (id == 100 || id == 102 || id == 103 || id == 105 || id >= 110) return; 
        if (room.hasEnemy()) return;
        if (Math.random() < 0.30) {
            entities.NPC monster = new entities.NPC("Geodude Selvagem", 40, 8, 30, id);
            room.setNpc(monster);
            System.out.println(Color.red("\n!!! Inimigo apareceu! !!!"));
        }
    }

    private void look(Player p, GameMap map) {
        Room r = map.getRoom(p.getCurrentRoomId());
        System.out.println("=== " + r.getName().toUpperCase() + " ===");
        System.out.println(r.getDescription());
        System.out.println(r.getExitString());
    }

    private void fight(String skillName, Player p, GameMap map) {
        if (!map.getRoom(p.getCurrentRoomId()).hasEnemy()) { System.out.println("Ninguem aqui."); return; }
        if (skillName.isEmpty()) skillName = "soco";
        engine.Skill s = p.getSkill(skillName);
        if (s != null) combat.battleRound(p, map.getRoom(p.getCurrentRoomId()).getNpc(), s);
        else System.out.println("Habilidade desconhecida.");
    }

    private void takeItem(Player p, GameMap map) {
        Room r = map.getRoom(p.getCurrentRoomId());
        if (r.hasItem()) { p.addItem(r.getItem()); r.setItem(null); }
        else { System.out.println("Nada aqui."); }
    }
}