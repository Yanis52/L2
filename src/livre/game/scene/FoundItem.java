package livre.game.scene;

import livre.game.player.*;
import livre.game.loader.*;
import java.util.*;
import org.json.simple.*;


/**
 * type = type de l'item
 */
public class FoundItem implements Interaction{
    private String type;
    private Item foundItem;

    public FoundItem(EveryItem allItem){
        this.type = "foundItem";

        Random r = new Random();
		int randomInt = r.nextInt(3) + 1;
        if(randomInt == 1 || randomInt == 2){
            this.foundItem = allItem.getWithAlias("key");
        }
        else{
            this.foundItem = allItem.getRandomItem();
        }
    }
    //GETTER
    public String getType(){
        return this.type;
    }

    public Item getFoundItem(){
        return this.foundItem;
    }
    //SETTER
    public void setFoundItem(Item item){
        this.foundItem = item;
    }

    /**
     * permet de retourner sous fichier Json toutes les informations des items
     * @param
     * @return le Json
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("type", this.type);
        json.put("item", this.foundItem.getAlias());

        return json;
    }
}