package entities;

import engine.Skill;
import engine.Item;
import engine.Color;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private int maxAP;
    private int currentAP;
    private int xp;
    private int level;
    private int xpToNextLevel;
    private boolean isEvolved; 
    private int gold = 0;
    private boolean temporalVision = false; 

    private List<Skill> skills;
    private List<Item> inventory;
    private List<String> defeatedEnemies;

    public Player(String name, int startRoomId) {
        super(name, 100, startRoomId); 
        this.maxAP = 20; 
        this.currentAP = 20;
        this.xp = 0;
        this.level = 1;
        this.xpToNextLevel = 100; 
        this.isEvolved = false;

        this.skills = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.defeatedEnemies = new ArrayList<>();

        skills.add(new Skill("soco", "Ataque basico", 8, 0)); 
        skills.add(new Skill("onda", "Onda de Aura", 15, 5));
        skills.add(new Skill("quebra-rocha", "Golpe esmagador de pedras", 25, 8));
        
        inventory.add(new Item("pocao", "Cura 30 HP", 30, false));
    }

    public void printStatus() {
        // Sem 'x:' aqui!
        System.out.println(Color.gold("=== FICHA DE TREINADOR ==="));
        System.out.println("Nome: " + getName() + " (" + getPokemonName() + ")");
        System.out.println("Nivel: " + level);
        System.out.println("HP: " + Color.green(health + "/" + maxHealth));
        System.out.println("AP: " + Color.cyan(currentAP + "/" + maxAP));
        System.out.println("XP: " + xp + "/" + xpToNextLevel);
        System.out.println("Gold: " + Color.gold(String.valueOf(gold)));
        System.out.println("==========================");
    }

    public void printSkills() {
        // Sem 'text:' aqui!
        System.out.println(Color.cyan("=== LISTA DE HABILIDADES ==="));
        for (Skill s : skills) {
            System.out.println("* " + Color.cyan(s.getName()) + " - Dano: " + s.getDamage() + " | Custo: " + s.getCostAP() + " AP");
            System.out.println("  Descricao: " + s.getDescription());
        }
        System.out.println("============================");
    }

    public String getPokemonName() { return isEvolved ? "Lucario" : "Riolu"; }
    public void setTemporalVision(boolean active) { this.temporalVision = active; }
    public boolean hasTemporalVision() { return temporalVision; }

    public void gainXp(int amount) {
        this.xp += amount;
        System.out.println(">>> Voce ganhou " + amount + " XP.");
        if (this.xp >= this.xpToNextLevel) levelUp();
    }

    private void levelUp() {
        this.level++;
        this.xp = this.xp - this.xpToNextLevel;
        this.xpToNextLevel = (int)(this.xpToNextLevel * 1.5); 
        this.maxHealth += 20;
        this.health = this.maxHealth;
        this.maxAP += 5;
        this.currentAP = this.maxAP;
        System.out.println(Color.gold("\n*** LEVEL UP! Nivel " + this.level + " ***"));
        if (this.level >= 2 && !isEvolved) evolve();
    }

    private void evolve() {
        this.isEvolved = true;
        System.out.println(Color.gold("\n🌀 ... Riolu evoluiu para LUCARIO!"));
        this.maxHealth += 50;
        this.health = this.maxHealth;
        this.maxAP += 20;
        skills.add(new Skill("aurasphere", "Esfera de Aura Suprema", 40, 10));
        System.out.println("Lucario aprendeu " + Color.cyan("aurasphere") + "!");
    }

    public void addGold(int amount) { this.gold += amount; System.out.println(Color.gold("💰 Voce ganhou " + amount + " Gold!")); }
    public boolean spendGold(int amount) {
        if (this.gold >= amount) { this.gold -= amount; return true; }
        System.out.println(Color.red("Voce nao tem dinheiro suficiente! (Tem: " + gold + ")"));
        return false;
    }

    public void addItem(Item item) { inventory.add(item); System.out.println("Pegou: " + item.getName()); }
    public Item getItem(String name) { for (Item i : inventory) { if (i.getName().equalsIgnoreCase(name)) return i; } return null; }
    public void removeItem(Item item) { inventory.remove(item); }
    public void addDefeated(String name) { defeatedEnemies.add(name); }
    public List<String> getDefeatedEnemies() { return defeatedEnemies; }

    public void printInventory() {
        System.out.println("--- MOCHILA (Nvl " + level + ") ---");
        System.out.println("Gold: " + gold);
        if (inventory.isEmpty()) System.out.println("(Vazia)");
        for (Item i : inventory) System.out.println("- " + i.getName() + ": " + i.getDescription());
    }

    public boolean useAP(int cost) { if (currentAP >= cost) { currentAP -= cost; return true; } return false; }
    public void recoverAP(int amount) { currentAP += amount; if (currentAP > maxAP) currentAP = maxAP; }
    public int getCurrentAP() { return currentAP; }
    public Skill getSkill(String skillName) { for (Skill s : skills) { if (s.getName().contains(skillName)) return s; } return null; }
    
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public int getXp() { return xp; }
    public void setXp(int xp) { this.xp = xp; }
    public boolean isEvolved() { return isEvolved; }
    public void setEvolved(boolean isEvolved) { this.isEvolved = isEvolved; }
    public int getMaxAP() { return maxAP; }
    public void setMaxAP(int maxAP) { this.maxAP = maxAP; }
    public void setCurrentAP(int currentAP) { this.currentAP = currentAP; }
    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; }
    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }
    public void addSkill(Skill skill) { this.skills.add(skill); }
    public void clearSkills() { this.skills.clear(); }
    public void clearInventory() { this.inventory.clear(); }
}