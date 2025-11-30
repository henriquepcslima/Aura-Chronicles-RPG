package engine;

public class Item {
    private String name;
    private String description;
    private int healAmount; 
    private boolean isKeyItem; 

    public Item(String name, String description, int healAmount, boolean isKeyItem) {
        this.name = name;
        this.description = description;
        this.healAmount = healAmount;
        this.isKeyItem = isKeyItem;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getHealAmount() { return healAmount; }
    public boolean isKeyItem() { return isKeyItem; }
}