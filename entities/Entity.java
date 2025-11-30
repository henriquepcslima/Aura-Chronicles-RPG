package entities;

public class Entity {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int currentRoomId;

    public Entity(String name, int maxHealth, int startRoomId) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.currentRoomId = startRoomId;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health < 0) this.health = 0;
    }
    
    public void move(int newRoomId) {
        this.currentRoomId = newRoomId;
    }
public int getMaxHealth() {
        return maxHealth;
    }
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getCurrentRoomId() { return currentRoomId; }
    public void setHealth(int health) { this.health = health; }
    
    private String imageUrl; 

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
}