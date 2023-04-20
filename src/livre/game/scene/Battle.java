package livre.game.scene;

import livre.game.loader.*;
import livre.game.*;
import org.json.simple.*;



/**
 * type = type de combat
 * monstre= monstre 
 */
public class Battle implements Interaction{
    private String type;
    private Monster monster;

    public Battle(Monster monster){
        this.type = "battle";
        this.monster = monster;
    }

    public Battle(EveryMonster allMonster){
        this.type = "battle";
        this.monster = allMonster.getRandomMonster();
    }

    // ----------GETTER------------
    public String getType(){
        return this.type;
    }
    public Monster getMonster(){
        return this.monster;
    }
    // ----------------------

    public String messageStartFight(){
        return this.monster.getName() + " apparait !";
    }

    /**
     * permet de retourner sous fichier Json toutes les informations du combat
     * @param
     * @return le Json
     */
    
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("type", this.type);
        // json.put("monster", this.monster.toJSONObject());
        json.put("monster", this.monster.getAlias());

        return json;
    }
}