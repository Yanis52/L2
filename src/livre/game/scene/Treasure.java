package livre.game.scene;

import livre.game.player.*;
import livre.game.loader.*;



import org.json.simple.*;


/**
 * type = string
 * treasureItem = item tresor
 */
public class Treasure implements Interaction{
    private String type;
    private Item treasureItem;

    public Treasure(EveryItem allItem){
        this.type = "treasure";

        this.treasureItem = allItem.getRandomItem();
    }

    //------GETTER-----
    public String getType(){
        return this.type;
    }

    public Item getTreasureItem(){
        return this.treasureItem;
    }
    //--------SETTER---------------
    public void setTreasureItem(Item item){
        this.treasureItem = item;
    }

    /**
     * permet d'ouvrir un coffre si on a la clef
     * @param p
     * @param allItem
     * @return reoturne l'item du coffre
     */
    public Item openTreasure(Player p, EveryItem allItem){
        if(p.checkIfGotItemWithAlias("key")){
            return this.treasureItem;
        }
        else {
            return allItem.getWithId(0); // Return null item
        }
    }

    
/**
     * permet de retourner sous fichier Json toutes les informations du Tresor
     * @param
     * @return le Json
     */
    
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("type", this.type);
        json.put("item", this.treasureItem.getAlias());

        return json;
    }
}