package entities;

public class NPC extends Entity {
    private int damage;
    private int xpReward; 
    private boolean isScriptedEvent; 
    private String defeatMessage; 

    public NPC(String name, int maxHealth, int damage, int xpReward, int startRoomId) {
        super(name, maxHealth, startRoomId);
        this.damage = damage;
        this.xpReward = xpReward;
        this.isScriptedEvent = false;
        this.defeatMessage = "";
    }

    public void setScriptedLoss(String storyText) {
        this.isScriptedEvent = true;
        this.defeatMessage = storyText;
    }

    public int attack() { return this.damage; }
    
   
    public int getXpReward() { return xpReward; }
    

    public boolean isScriptedEvent() { return isScriptedEvent; }
    public String getDefeatMessage() { return defeatMessage; }
    
    
  
    private engine.Item lootItem;   
    private double dropChance;      

    
    public void setDrop(engine.Item item, double chance) {
        this.lootItem = item;
        this.dropChance = chance;
    }

    public engine.Item getLootItem() { return lootItem; }
    public double getDropChance() { return dropChance; }
    
    private int goldReward = 10; 

    public void setGoldReward(int amount) {
        this.goldReward = amount;
    }

    public int getGoldReward() { return goldReward; }
}