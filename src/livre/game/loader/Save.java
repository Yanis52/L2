package livre.game.loader;

import livre.game.player.*;
import livre.game.scene.*;
import java.io.*;
import java.util.*;
import org.json.simple.*;



public class Save extends JSONReader{
    // Export and import Player
    private String pathJson;

    public Save(){
        super("data/save.json");
        this.pathJson = "data/save.json";
    }
    //revoir ces contrats
    
    /**
     * permet de charger une scene de l'arbre
     * @param node
     * @param allSkill
     * @param allItem
     * @param allMonster
     * @return
     */

    public Scene loadNodeTree(JSONObject node, EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        String name = (String)node.get("name");
        String alias = (String)node.get("alias");
        long idL = (Long)node.get("id");
        int id = (int)idL;

        JSONObject interaction = (JSONObject)node.get("interaction");
        String type = (String)interaction.get("type");

        Scene returned =  new Scene(name, alias);
        returned.setId(id);

        ArrayList<Long> nextScene = (ArrayList)node.get("nextScene");
        for(long next : nextScene){
            int nextInt = (int)next;
            returned.addNextScene(nextInt);
        }

        if(type.equals("text")){
            Text inter = new Text();
            ArrayList<String> paragraph = (ArrayList)interaction.get("text");
            for(String s : paragraph){
                inter.addTextParagraph(s);
            }
            returned.setInteraction(inter);
            return returned;
        }
        else if(type.equals("battle")){
            String monsterAlias = (String)interaction.get("monster");
            Battle inter = new Battle(allMonster.getWithAlias(monsterAlias));
            returned.setInteraction(inter);
            return returned;
        }

        else if(type.equals("shop")){
            Shop inter = new Shop();
            ArrayList<String> items = (ArrayList)interaction.get("items");
            for(String s : items){
                inter.addItem(allItem.getWithAlias(s));
            }
            returned.setInteraction(inter);
            return returned;
        }

        else if(type.equals("temple")){
            Temple inter = new Temple();
            returned.setInteraction(inter);
            return returned;
        }

        else if(type.equals("foundItem")){
            FoundItem inter = new FoundItem(allItem);
            String aliasItem = (String)interaction.get("item");
            inter.setFoundItem(allItem.getWithAlias(aliasItem));
            returned.setInteraction(inter);
            return returned;
        }
        
        else if(type.equals("treasure")){
            Treasure inter = new Treasure(allItem);
            String aliasItem = (String)interaction.get("item");
            inter.setTreasureItem(allItem.getWithAlias(aliasItem));
            returned.setInteraction(inter);
            return returned;
        }

        else if(type.equals("gameOver")){
            GameOver inter = new GameOver();
            returned.setInteraction(inter);
            return returned;
        }

        else if(type.equals("final")){
            Final inter = new Final();
            returned.setInteraction(inter);
            return returned;
        }

        return new Scene(name, alias, new NullInteraction());
    }
    
    /**
     * permet d'importer l'arbre de jeux depuis le fichier de sauvegarde
     * @param allSkill
     * @param allItem
     * @param allMonster
     * @return
     */
    public ArrayList<Scene> importTree(EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        JSONObject data = super.getFile();

        ArrayList<JSONObject> treeJSON = (JSONArray)data.get("tree");
        ArrayList<Scene> tree = new ArrayList<>();
        for(JSONObject node : treeJSON){
            tree.add(loadNodeTree(node, allSkill, allItem, allMonster));
        }

        return tree;
    }
    /**
     * permet d'importer une partie précédement sauvegardé
     * @param allSkill
     * @param allItem
     * @param allMonster
     * @return
     */
    public Player importSave(EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        JSONObject data = super.getFile();
        JSONObject objectiveItemJS = (JSONObject)data.get("objectiveItem");
        Item objectiveItem = allItem.getWithAlias((String)objectiveItemJS.get("alias"));
        long objectiveGoldL = (Long)data.get("objectiveGold");
        int objectiveGold = (int)objectiveGoldL;
        long actualSceneIdL = (Long)data.get("actualSceneId");
        int actualSceneId = (int)actualSceneIdL;

        ArrayList<JSONObject> treeJSON = (JSONArray)data.get("tree");
        ArrayList<Scene> tree = new ArrayList<>();
        for(JSONObject node : treeJSON){
            tree.add(loadNodeTree(node, allSkill, allItem, allMonster));
        }

        Player p = new Player(objectiveItem, objectiveGold, tree, allSkill);

        boolean gotEnoughGold = (Boolean)data.get("gotEnoughGold");
        boolean gotItem = (Boolean)data.get("gotItem");
        p.setGotItem(gotItem);
        p.setEnoughGold(gotEnoughGold);

        long goldCountL = (Long)data.get("goldCount");
        int goldCount = (int)goldCountL;
        p.addGold(goldCount);


        long attackL = (Long)data.get("attack");
        long defenseL = (Long)data.get("defense");
        long maxHealthL = (Long)data.get("maxHealth");
        long actualHealthL = (Long)data.get("maxHealth");
            
        int attack = (int)attackL;
        int defense = (int)defenseL;
        int maxHealth = (int)maxHealthL;
        int actualHealth = (int)actualHealthL;
        p.setAttack(attack);
        p.setDefense(defense);
        p.setMaxHealth(maxHealth);
        p.setActualHealth(actualHealth);

        ArrayList<JSONObject> skills = (ArrayList)data.get("skills");
        for(JSONObject skill : skills){
            Skill newSkill = allSkill.getWithAlias((String)skill.get("alias"));
            p.addSkill(newSkill);
        }

        ArrayList<JSONObject> inventory = (ArrayList)data.get("inventory");
        for(JSONObject i : inventory){
            Item newItem = allItem.getWithAlias((String)i.get("alias"));
            p.addItem(newItem);
        }

        return p;
    }
    /**
     * permet d'exporter une sauvegarde
     * @param p
     * @return
     */

    public void exportSave(Player p){
        try{
            FileWriter file = new FileWriter(this.pathJson);
            file.write(p.toJSONObject().toJSONString());
            file.flush();
            file.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}


