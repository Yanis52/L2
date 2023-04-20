package livre.game.player;
import livre.game.scene.*;
import livre.game.loader.*;
import livre.game.*;
import java.util.*;
import org.json.simple.*;



/**
 * goldCount = nombre de Gold du player
 * ObjectifGold= nombre de gold objectif
 * actualSceneId= id de la scene actuelle
 * gotItem = 
 * gotEnoughtGold = est ce que la personne a assez d'or
 * inventory = inventaire du player
 * objectiveItem = l'item objectif
 * tree = arbre 
 */
public class Player extends Fighter{
    private int goldCount, objectiveGold, actualSceneId;
    private boolean gotItem, gotEnoughGold;
    private ArrayList<Item> inventory;
    private Item objectiveItem;
    private ArrayList<Scene> tree;


    public Player(Item objectiveItem, int objectiveGold, ArrayList<Scene> tree , EverySkill allSkill){
        super(10, 10, 100);

        this.inventory = new ArrayList<>();

        // Gold / Money
        this.goldCount = 0;

        // Objectif
        this.objectiveItem = objectiveItem;
        this.objectiveGold = objectiveGold;

        this.gotItem = false;
        this.gotEnoughGold = false;

        // Load basic skill
        super.addSkill(allSkill.getWithId(3));
        super.addSkill(allSkill.getWithId(5));
        super.addSkill(allSkill.getWithId(7));
        super.addSkill(allSkill.getWithId(8));

        // Scene
        this.actualSceneId = tree.get(0).getId();
        this.tree = tree;

    }

    // ---------GETTER----------- //
    public ArrayList<Item> getInventory(){
        return this.inventory;
    }
    public int getGoldCount(){
        return this.goldCount;
    }
    public Item getObjectiveItem(){
        return this.objectiveItem;
    }
    public int getObjectiveGold(){
        return this.objectiveGold;
    }
    public boolean getHasGotItem(){
        return this.gotItem;
    }
    public boolean getHasGotEnoughGold(){
        return this.gotEnoughGold;
    }
    public int getActualSceneId(){
        return this.actualSceneId;
    }
    public ArrayList<Scene> getTree(){
        return this.tree;
    }
    // -------------------- //

    // ----------SETTER---------- //
    public void setGotItem(boolean change){
        this.gotItem = change;
    }
    public void setEnoughGold(boolean change){
        this.gotEnoughGold = change;
    }
    public void setActualIdScene(int id){
        this.actualSceneId = id;
    }
    // -------------------- //

    /**
     * permet de recuprer toute sa vie (PV MAX)
     * @param
     * @return le nombre de points de vie
     */
    public void recoveryMaxHealth(){
       
        super.setActualHealth(super.getMaxHealth());
    }

    // -----------Inventory------------
    // Loop all inventory to apply methods
    /**
     * permet de parcourir tout l'inventaire
     * @param
     * @return
     */
    public void loopItemInInventory(){
        for(Item i : this.inventory){
            this.updateStatsWithItem(i);
            this.checkIfGotObjectiveItem(i);
        }
    }
    /**
     * permet de mettre a jour les stats du joueur avec les items bonus
     * @param i
     *@return
     */
    // Update player's stats with item's bonus
    public void updateStatsWithItem(Item i){
        super.addAttack(i.getBonusAttack());
        super.addDefense(i.getBonusDefense());
        super.addMaxHealth(i.getBonusMaxHealth());
    }
    /**
     * permet d'ajouter un item a l'inventaire
     * @param i
     * @return
     */
    // add item in inventory
    public void addItem(Item i){
        if(i.getAlias() != "null"){
            this.inventory.add(i);
        }
    }
    /**
     * regarde si il à l'objectif item dans sont iventaire
     * @param i
     * @return
     */
    public void checkIfGotObjectiveItem(Item i){
        if(i.getName() == this.objectiveItem.getName()){
            this.gotItem = true;
        }
    }
    /**
     * regarde si le joueur possède l'item dans sont iventaire a travers sont alias
     * @param alias
     * @return
     */
    public boolean checkIfGotItemWithAlias(String alias){
        for(Item inventoryItem : this.inventory){
            if(alias.equals(inventoryItem.getAlias())){
                return true;
            }
        }

        return false;
    }
    // -----------------------

    // -----------Money------------
    // Add some gold
    /***
     * permet de rajouter des golds au joueur
     * @param count
     * @return
     */
    public void addGold(int count){
        this.goldCount += count;
    }

    // Retire quelques golds
    /**
     * permet de retirer quelques golds au joueur
     * @param count
     * @return
     */
    public void removeGold(int count){
        if(this.goldCount - count < 0){
            this.goldCount = 0;
        }
        else{
            this.goldCount -= count;
        }
    }
    /**
     * regarde si le joueur à assez d'or dans sont inventaire
     * @param
     * @return
     */
    public void checkGotEnoughGold(){
        if(this.goldCount <= this.objectiveGold){
            this.gotEnoughGold = true;
        }
    }
    // -----------------------
    /**
     * permet de retourner sous fichier Json toutes les informations du Player
     * @param
     * @return le Json
     */

    public JSONObject toJSONObject(){
        JSONObject json = super.toJSONObject();        

        json.put("goldCount", new Integer(this.goldCount));
        json.put("gotItem", new Boolean(this.gotItem));
        json.put("gotEnoughGold", new Boolean(this.gotEnoughGold));

        json.put("objectiveGold", new Integer(this.objectiveGold));
        json.put("objectiveItem", this.objectiveItem.toJSONObject());
        json.put("actualSceneId", this.actualSceneId);

        ArrayList<JSONObject> treeJSON = new ArrayList<>();
        for(Scene s : this.tree){
            treeJSON.add(s.toJSONObject());
        }
        json.put("tree", treeJSON);

        ArrayList<JSONObject> inventoryJSON = new ArrayList<>();
        for(Item s : this.inventory){
            inventoryJSON.add(s.toJSONObject());
        }
        json.put("inventory", inventoryJSON);

        return json;
    }

}