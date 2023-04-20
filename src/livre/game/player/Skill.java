package livre.game.player;
import java.util.*;
import org.json.simple.*;

/**
 * name = nom du skill
 * type = type du skill
 * alias = alias du skill
 * damage = nombre de dégats
 * successfullChance= si c'est réussit ou pas
 */

public class Skill{
    private String name, type, alias;
    private int damage, successfullChance;

    public Skill(String name, String alias, String type, int damage, int successfullChance){
        this.name = name;
        this.type = type;
        this.alias = alias;
        this.damage = damage;
        this.successfullChance = successfullChance;
    }

    // ---------GETTER----------- //
    public String getName(){
        return this.name;
    }
    public String getAlias(){
        return this.alias;
    }
    public String getType(){
        return this.type;
    }
    public int getDamage(){
        return this.damage;
    }
    public int getSuccessfullChance(){
        return this.successfullChance;
    }
    // -------------------- //

    /**
     * permet de voir si le skill à réussit
     * @param
     * @return
     */
    public boolean succesSkill(){
        Random random = new Random();
        int min = 0;
        int max = 100;
        int x = random.nextInt((max-min)+1) + min;
        
        if(x <= this.successfullChance){
            return true;
        } 
        else{
            return false;
        }
    }
    /**
     * permet d'utiliser les skills
     * @param
     * @return
     */

    public int useSkill(){
        if(this.succesSkill()){
            return this.damage;
        }
        else{
            return 0;
        }
    }

    public String toString(){
        return
        "name : " + this.name +
        "\ntype : " + this.type +
        "\ndamage : " + this.damage + 
        "\nchance : " + this.successfullChance;
    }
    /**
     * permet de retourner sous fichier Json toutes les informations du Player
     * @param
     * @return le Json
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();

        json.put("name" , this.name);
        json.put("type" , this.type);
        json.put("alias", this.alias);
        json.put("damage", new Integer(this.damage));
        json.put("successfullChance", new Integer(this.successfullChance));

        return json;
    }
}