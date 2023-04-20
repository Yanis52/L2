package livre.game;

import livre.game.player.*;
import livre.game.scene.*;
import livre.game.loader.*;
import java.util.*;

public class Main{

    public static void main(String[] args){

        // Chargement des items/skills/monstres qui peuvent exister
        EveryItem item = new EveryItem();
        EverySkill skill = new EverySkill();
        EveryMonster monster = new EveryMonster(skill);
        Save save = new Save();
        TreeGenerator gen = new TreeGenerator();

        ArrayList<Scene> tree = gen.loadBasicTree(skill, item, monster);
        Item objectiveItem = item.getRandomItem();
        int objectiveGold = 50;
        
        Player player = new Player(objectiveItem, objectiveGold, tree, skill);
        save.exportSave(player);
    }
}