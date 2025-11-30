package world;

import entities.NPC;
import engine.Item;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private final int id;
    private final String name;
    private final String description;
    private final Map<String, Integer> exits; 
    private NPC npc; 
    private Item item; 
    
    
    private String hiddenDescription; 

    public Room(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.npc = null;
        this.item = null;
        this.hiddenDescription = null;
    }

    public void addExit(String direction, int destinationRoomId) {
        this.exits.put(direction.toLowerCase(), destinationRoomId);
    }

    
    public void setHiddenDescription(String text) { this.hiddenDescription = text; }
    public String getHiddenDescription() { return hiddenDescription; }
    public boolean hasHiddenSecret() { return hiddenDescription != null; }

    
    public void setNpc(NPC npc) { this.npc = npc; }
    public NPC getNpc() { return npc; }
    public boolean hasEnemy() { return npc != null && npc.getHealth() > 0; }
    
    public void setItem(Item item) { this.item = item; }
    public Item getItem() { return item; }
    public boolean hasItem() { return item != null; }

    
    public int getId() { return id; }
    public String getName() { return name; }
    
    public int getNextRoomId(String direction) {
        return exits.getOrDefault(direction.toLowerCase(), -1);
    }

    public String getDescription() {
        String fullDesc = description;
        if (hasEnemy()) {
            fullDesc += "\n\n[!] PERIGO: " + npc.getName() + " esta aqui!";
        }
        if (hasItem()) {
            fullDesc += "\n\n[?] Voce ve um item aqui: " + item.getName();
        }
        return fullDesc;
    }
    
    public String getExitString() {
        return "Saidas: " + exits.keySet().toString();
    }
}