package livre.game.player;

import org.json.simple.*;



// Item give some bonus to Player's stats when he got it in is
// inventory
/**
 *name = nom de l'item 
 *alias = alias de l'item
 *sprite = image de l'item
 */
public class Item{
    private String name, alias, sprite;
    private int bonusAttack, bonusDefense, bonusMaxHealth;

    public Item(String name, String sprite, String alias, int bonusAttack, int bonusDefense, int bonusMaxHealth){
        this.name = name;
        this.bonusAttack = bonusAttack;
        this.bonusDefense = bonusDefense;
        this.bonusMaxHealth = bonusMaxHealth;
        this.alias = alias;
        this.sprite = sprite;
    }

    // ---------GETTER----------- //
    public String getName(){
        return this.name;
    }
    public int getBonusAttack(){
        return this.bonusAttack;
    }
    public int getBonusDefense(){
        return this.bonusDefense;
    }
    public int getBonusMaxHealth(){
        return this.bonusMaxHealth;
    }
    public String getAlias(){
        return this.alias;
    }
    public String getSprite(){
        return this.sprite;
    }
    // -------------------- //

    public String toString(){
        return 
        "name : " + this.name +
        "\nattack : " + this.bonusAttack +
        "\ndefense : " + this.bonusDefense +
        "\nmaxHealth : " + this.bonusMaxHealth;
    }

  
    /**
     * permet de calculer la Moyenne de tout ses attributs, rediviser par 4 pour fixer son prix
     * @param
     * @return le prix de l'item
     */

    public int getCost(){
        int mean = (int)((this.bonusAttack + this.bonusDefense + this.bonusMaxHealth) / 3) / 4;
        return mean;
    }
    /**
     * permet de retourner sous fichier Json toutes les informations du Player
     * @param
     * @return le Json
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("alias", this.alias);
        json.put("sprite", this.sprite);
        json.put("bonusAttack", this.bonusAttack);
        json.put("bonusDefense", this.bonusDefense);
        json.put("bonusMaxHealth", this.bonusMaxHealth);

        return json;

    }
}
