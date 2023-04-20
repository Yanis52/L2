package livre.game.scene;

import livre.game.player.*;
import livre.game.loader.*;
import java.util.*;
import org.json.simple.*;


/**
 *sellable = determine si l'item est vendable ou pas
 *maxLenIten =constante
 *type = type de l'item ("shop")
 */
public class Shop implements Interaction{
    private ArrayList<Item> sellable;
    static int maxLenItem = 4;
    private String type;

    public Shop(){
        this.type = "shop";
        this.sellable = new ArrayList<>();
    }

    public Shop(ArrayList<Item> sellable){
        this.type = "shop";
        this.sellable = sellable;
    }

    public Shop(EveryItem allItem){
        this.type = "shop";
        this.sellable = new ArrayList<>();
        for(int i = 0; i<maxLenItem; i++){
            this.sellable.add(allItem.getRandomItem());
        }
    }
    //------GETTER-------------
    public String getType(){
        return this.type;
    }

    public ArrayList<Item> getSellable(){
        return this.sellable;
    }
    //--------------------------------
    /**
     * permet d'ajouter un nouvelle item au shop
     * @param newItem
     * @return
     */
    public void addItem(Item newItem){
        this.sellable.add(newItem);
    }

    /**
     * permet de recuperer le prix moyen des items(permet de calculer la Moyenne de tout ses attributs, rediviser par 4 pour fixer son prix)
     * @param id
     * @return le prix 
     */

    public int getItemCost(int id){
        return this.sellable.get(id).getCost();
    }

    /**
     * permet de retourner sous fichier Json toutes les informations du Shop
     * @param
     * @return le Json
     */

    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        
        json.put("type", this.type);

        ArrayList<String> itemAlias = new ArrayList<>();
        for(Item i : this.sellable){
            itemAlias.add(i.getAlias());
        }
        json.put("items", itemAlias);

        return json;
    }
}