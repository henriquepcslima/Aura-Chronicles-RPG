package engine;

public class Skill {
    private String name;
    private String description;
    private int damage;
    private int costAP;

    public Skill(String name, String description, int damage, int costAP) {
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.costAP = costAP;
    }

    public String getName() { return name; }
    public String getDescription() { return description; } 
    public int getDamage() { return damage; }
    public int getCostAP() { return costAP; }
}