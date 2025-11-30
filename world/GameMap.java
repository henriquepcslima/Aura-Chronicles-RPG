package world;

import entities.NPC;
import engine.Item;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    private final Map<Integer, Room> rooms;

    public GameMap() {
        this.rooms = new HashMap<>();
        initializeMap();
    }

    private void initializeMap() {
        // ==========================================
        // ÁREA 1: MONTE SILVER (Início) & VIOLET CITY
        // ==========================================
        Room mountSilver = new Room(100, "Topo do Monte Silver", "Neve e vento cortante. O local da batalha lendaria.");
        mountSilver.addExit("leste", 101);
        mountSilver.setItem(new Item("fragmento", "Um fragmento brilhante da Lagrima do Tempo", 0, true));
        rooms.put(100, mountSilver);

        Room route28 = new Room(101, "Rota da Montanha", "Uma descida perigosa em direcao a civilizacao.");
        route28.addExit("oeste", 100);
        route28.addExit("leste", 102);
        rooms.put(101, route28);

        Room violet = new Room(102, "Violet City", "O Hub central.\nNORTE: Ginasio.\nSUL: Azalea Town.\nLESTE: Ecruteak City.");
        violet.addExit("oeste", 101); 
        violet.addExit("norte", 103); 
        violet.addExit("leste", 104); 
        violet.addExit("sul", 108);   
        rooms.put(102, violet);

        Room gym = new Room(103, "Ginasio de Violet", "O ar aqui e rarefeito. O Lider Falkner te aguarda.");
        gym.addExit("sul", 102);
        
        NPC falkner = new NPC("Lider Falkner", 60, 10, 100, 103);
        falkner.setGoldReward(200);
        falkner.setDrop(new Item("superpocao", "Cura 50 HP", 50, false), 1.0);
        // IMAGEM: Falkner
        falkner.setImageUrl("https://play.pokemonshowdown.com/sprites/trainers/falkner.png");
        
        gym.setNpc(falkner);
        rooms.put(103, gym);

        // ==========================================
        // ÁREA 2: ECRUTEAK & TORRE QUEIMADA (Leste)
        // ==========================================
        Room route36 = new Room(104, "Rota 36", "Estrada para a cidade das lendas.");
        route36.addExit("oeste", 102);
        route36.addExit("leste", 105);
        rooms.put(104, route36);

        Room ecruteak = new Room(105, "Ecruteak City", "Cidade mistica.\nNORTE: Torre Queimada.\nLESTE: Ginasio (Morty).\nSUL: Rota para Olivine.");
        ecruteak.addExit("oeste", 104);
        ecruteak.addExit("norte", 106);
        ecruteak.addExit("leste", 117); 
        ecruteak.addExit("sul", 118);   
        rooms.put(105, ecruteak);

        Room burnedTower = new Room(106, "Torre Queimada", "Cinzas. Ha um buraco levando ao SUBSOLO (baixo).");
        burnedTower.addExit("sul", 105);
        burnedTower.addExit("baixo", 107);
        rooms.put(106, burnedTower);

        Room basement = new Room(107, "Subsolo da Torre", "Escuridao total. Presenca lendaria.");
        basement.addExit("cima", 106);
        
        NPC hooh = new NPC("Ho-Oh Sombrio", 5000, 50, 0, 107);
        hooh.setScriptedLoss("Ho-Oh prepara o Fogo Sagrado...\nMas para ao ver sua Aura.\nEle recua e desaparece.");
        // IMAGEM: Ho-Oh Animado
        hooh.setImageUrl("https://play.pokemonshowdown.com/sprites/xyani/hooh.gif");
        
        basement.setNpc(hooh);
        rooms.put(107, basement);

        Room mortyGym = new Room(117, "Ginasio de Ecruteak", "Um labirinto invisivel de sombras.");
        mortyGym.addExit("oeste", 105);
        
        NPC morty = new NPC("Lider Morty", 200, 30, 800, 117);
        morty.setGoldReward(800);
        morty.setDrop(new Item("insignia_nevoa", "Permite chamar Lugia em Olivine", 0, true), 1.0);
        // IMAGEM: Morty
        morty.setImageUrl("https://play.pokemonshowdown.com/sprites/trainers/morty.png");
        
        mortyGym.setNpc(morty);
        rooms.put(117, mortyGym);

        // ==========================================
        // ÁREA 3: AZALEA TOWN (Sul)
        // ==========================================
        Room route32 = new Room(108, "Rota 32", "Uma longa estrada costeira.");
        route32.addExit("norte", 102);
        route32.addExit("sul", 109);
        route32.setItem(new Item("antidoto", "Cura estatus", 0, false));
        rooms.put(108, route32);

        Room unionCave = new Room(109, "Union Cave", "Caverna umida.");
        unionCave.addExit("norte", 108);
        unionCave.addExit("oeste", 110);
        
        NPC onix = new NPC("Onix Selvagem", 80, 15, 50, 109);
        onix.setGoldReward(50);
        // IMAGEM: Onix Animado
        onix.setImageUrl("https://play.pokemonshowdown.com/sprites/xyani/onix.gif");
        
        unionCave.setNpc(onix);
        rooms.put(109, unionCave);

        Room azalea = new Room(110, "Azalea Town", "Cidade dos Slowpokes.\nO Ginasio esta ao CENTRO.\nA Floresta esta a OESTE.");
        azalea.addExit("leste", 109); 
        azalea.addExit("centro", 111);
        azalea.addExit("oeste", 112); 
        rooms.put(110, azalea);

        Room azaleaGym = new Room(111, "Ginasio de Azalea", "O chao e coberto de grama alta.");
        azaleaGym.addExit("fora", 110);
        
        NPC bugsy = new NPC("Lider Bugsy", 120, 20, 200, 111);
        bugsy.setGoldReward(500);
        bugsy.setDrop(new Item("mel", "Mel doce que cura 80 HP", 80, false), 1.0);
        // IMAGEM: Bugsy
        bugsy.setImageUrl("https://play.pokemonshowdown.com/sprites/trainers/bugsy.png");
        
        azaleaGym.setNpc(bugsy);
        rooms.put(111, azaleaGym);

        // ==========================================
        // ÁREA 4: GOLDENROD (Oeste de Azalea)
        // ==========================================
        Room ilex = new Room(112, "Ilex Forest", "Arvores densas bloqueiam a luz.");
        ilex.addExit("leste", 110); 
        ilex.addExit("norte", 113); 
        ilex.setItem(new Item("carvao", "Carvao vegetal", 0, false));
        rooms.put(112, ilex);

        Room route34 = new Room(113, "Rota 34", "O caminho para a metropole.");
        route34.addExit("sul", 112);
        route34.addExit("norte", 114);
        rooms.put(113, route34);

        Room goldenrod = new Room(114, "Goldenrod City", "A metropole. Ginasio a LESTE.");
        goldenrod.addExit("sul", 113);
        goldenrod.addExit("leste", 115);
        goldenrod.setHiddenDescription("Visao Temporal revela a Cripta ao NORTE.");
        rooms.put(114, goldenrod);

        Room whitneyGym = new Room(115, "Ginasio de Goldenrod", "Um ginasio rosa.");
        whitneyGym.addExit("oeste", 114);
        
        NPC whitney = new NPC("Lider Whitney", 150, 25, 400, 115);
        whitney.setGoldReward(1000);
        whitney.setDrop(new Item("insignia_simples", "Permite Visao Temporal", 0, true), 1.0);
        // IMAGEM: Whitney
        whitney.setImageUrl("https://play.pokemonshowdown.com/sprites/trainers/whitney.png");
        
        whitneyGym.setNpc(whitney);
        rooms.put(115, whitneyGym);

        Room crypt = new Room(116, "Cripta do Tempo", "Um lugar fora do tempo.");
        crypt.addExit("sul", 114);
        
        NPC gengar = new NPC("Gengar Eclipse", 300, 40, 2000, 116);
        gengar.setGoldReward(5000);
        // IMAGEM: Mega Gengar Animado
        gengar.setImageUrl("https://play.pokemonshowdown.com/sprites/xyani/gengar-mega.gif");
        
        crypt.setNpc(gengar);
        rooms.put(116, crypt);

        // ==========================================
        // ÁREA 5: OLIVINE & ATO III
        // ==========================================
        Room route38 = new Room(118, "Rota 38", "O cheiro de sal e mar fica mais forte.");
        route38.addExit("norte", 105); 
        route38.addExit("sul", 119);   
        rooms.put(118, route38);

        Room olivine = new Room(119, "Olivine City", "A cidade portuaria.\nO mar se estende infinitamente.\n(DICA: Use a Insignia da Nevoa aqui)");
        olivine.addExit("norte", 118);
        rooms.put(119, olivine);

        // ==========================================
        // RETA FINAL: RETORNO AO MONTE SILVER
        // ==========================================
        
        // 120: Base
        Room silverBase = new Room(120, "Base do Monte Silver", "A base da montanha final. O ar e rarefeito.\nUm caminho sobe a encosta (CIMA).");
        silverBase.addExit("cima", 121);
        rooms.put(120, silverBase);

        // 121: Encosta
        Room silverSlope = new Room(121, "Encosta Gelada", "O caminho para o CUME esta bloqueado por rochas enormes.\n(Dica: Use 'quebra-rocha' para abrir passagem)");
        silverSlope.addExit("baixo", 120); 
        rooms.put(121, silverSlope);

        // 122: O Cume (Red)
        Room summit = new Room(122, "Cume do Monte Silver", "O ponto mais alto do mundo.\nO Lendario Treinador Red esta aqui.");
        summit.addExit("baixo", 121);
        
        NPC red = new NPC("Red Onirico", 500, 60, 9999, 122);
        red.setGoldReward(9999);
        red.setDrop(new Item("lagrima_completa", "Chave para a Fortaleza Sombria", 0, true), 1.0);
        // IMAGEM: Red Clássico
        red.setImageUrl("https://play.pokemonshowdown.com/sprites/trainers/red-gen1.png");
        
        summit.setNpc(red);
        rooms.put(122, summit);

        // 123: Fortaleza Sombria (Final Boss)
        Room fortress = new Room(123, "Fortaleza Sombria", "Um castelo de pesadelos no vacuo. Darkrai flutua no centro.");
        fortress.addExit("sul", 122);
        
        NPC darkrai = new NPC("Darkrai (Forma Estatica)", 9999, 80, 0, 123);
        darkrai.setScriptedLoss("Seus ataques atravessam Darkrai...\nEle revida com um Pesadelo.\n(Dica: Tente 'compreender' a dor dele.)");
        // IMAGEM: Darkrai Animado
        darkrai.setImageUrl("https://play.pokemonshowdown.com/sprites/xyani/darkrai.gif");
        
        fortress.setNpc(darkrai);
        rooms.put(123, fortress);
    }
    
    public Room getRoom(int id) { return rooms.get(id); }

    public void removeNpcByName(String name) {
        for (Room room : rooms.values()) {
            if (room.hasEnemy() && room.getNpc().getName().equals(name)) {
                room.setNpc(null); 
            }
        }
    }
}