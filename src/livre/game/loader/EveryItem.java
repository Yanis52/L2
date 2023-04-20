package livre.game.loader;

import livre.game.player.*;
import java.util.*;
import org.json.simple.*;


/**
 * allItem = liste de tout les items
 */
public class EveryItem extends JSONReader{
    
    private ArrayList<Item> allItem;

    public EveryItem(){
        super("data/items.json");
        this.allItem = new ArrayList<>();

        for(int i = 0; i < super.getFile().size(); i++){
            JSONObject data = (JSONObject)super.getFile().get(Integer.toString(i));
            String name = (String)data.get("name");
            String alias = (String)data.get("alias");
            String sprite = (String)data.get("sprite");

            long attack = (Long)data.get("attack");
            long defense = (Long)data.get("defense");
            long maxHealth = (Long)data.get("maxHealth");
            
            int a = (int)attack;
            int d = (int)defense;
            int h = (int)maxHealth;

            Item newItem = new Item(name, sprite, alias, a, d, h);
            this.allItem.add(newItem);
        }
    }
    /**
     * Getter : permet de recuperer tout les items 
     * @param
     * @return tout les items
     */
    public ArrayList<Item> getAllItem(){
        return this.allItem;
    }
    /**
     * permet de recuperer un item avec son ID
     * @param id
     * @return l'item
     */
    public Item getWithId(int id){
        return this.allItem.get(id);
    }
    /**
     * permet de recuperer un item avec son ALias 
     * @param alias
     * @return l'item
     */
    public Item getWithAlias(String alias){
        for(Item i : this.allItem){
            if(i.getAlias().equals(alias)){
                return i;
            }
        }
        return this.getWithId(0);
    }

    /**
     * permet de recuperer un item avec son nom 
     * @param alias
     * @return l'item
     */
    public Item getWithName(String name){
        for(Item i : this.allItem){
            if(i.getName().equals(name)){
                return i;
            }
        }
        return this.getWithId(0);
    }

    /**
     * permet de recuprer un item au hasard parmis la liste d'item
     * @param
     * @return l'item
     */
    public Item getRandomItem(){
        Random r = new Random();
		int randomInt = r.nextInt(this.allItem.size() - 1) + 1;
        return getWithId(randomInt);
    }
}