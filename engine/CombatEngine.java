package engine;

import entities.NPC;
import entities.Player;

public class CombatEngine {
    
    public void battleRound(Player player, NPC enemy, Skill skill) {
        System.out.println("\n=== COMBATE ===");
        
        
        if (enemy.getImageUrl() != null && !enemy.getImageUrl().isEmpty()) {
            System.out.println("<img src='" + enemy.getImageUrl() + "' class='battle-img'>");
        }
       
        if (!player.useAP(skill.getCostAP())) {
            System.out.println("AP Insuficiente! Voce precisa descansar ou usar um item.");
            return;
        }
        
        int damage = skill.getDamage();
        enemy.takeDamage(damage);
        
        
        System.out.println("Riolu usa " + Color.cyan(skill.getName()) + 
                           " causando " + Color.red(damage + " dano") + ".");

        if (enemy.getHealth() > 0) {
            System.out.println("Inimigo: " + enemy.getName() + 
                               " | HP: " + Color.red(enemy.getHealth() + "/" + enemy.getMaxHealth()));
        }

        
        if (enemy.getHealth() <= 0) {
            System.out.println(">>> " + Color.green("VITORIA! " + enemy.getName() + " foi derrotado."));
            
            player.gainXp(enemy.getXpReward()); 
            player.addGold(enemy.getGoldReward());
            player.addDefeated(enemy.getName());

            if (enemy.getLootItem() != null) {
                double roll = Math.random(); 
                if (roll < enemy.getDropChance()) {
                    System.out.println(Color.green("🎁 SORTE! O inimigo deixou cair: " + enemy.getLootItem().getName()));
                    player.addItem(enemy.getLootItem());
                }
            }
            player.recoverAP(5);
            return;
        }

        
        int enemyDmg = enemy.attack();
        player.takeDamage(enemyDmg);
        
        System.out.println(enemy.getName() + " ataca causando " + Color.red(enemyDmg + " de dano") + " em voce!");

        
        if (player.getHealth() <= 0) {
            if (enemy.isScriptedEvent()) {
                
                System.out.println("\n" + Color.gold("--- EVENTO DE HISTORIA ---"));
                System.out.println(Color.gold(enemy.getDefeatMessage()));
                player.setHealth(1); 
                enemy.takeDamage(9999); 
            } else {
                System.out.println(Color.red(">>> VOCE FOI DERROTADO..."));
                System.out.println("Voce acorda no Centro Pokemon, dolorido mas vivo.");
                player.setHealth(player.getMaxHealth());
                player.setCurrentAP(player.getMaxAP());
                player.move(102); 
            }
        }
        
        System.out.println("Voce: HP " + player.getHealth() + " | AP " + player.getCurrentAP());
    }
}