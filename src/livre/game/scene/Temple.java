package livre.game.scene;

import livre.game.player.*;

import livre.game.loader.*;
import org.json.simple.*;



public class Temple implements Interaction{

    private String type;

    public Temple(){
        this.type = "temple";
    }

    public String getType(){
        return this.type;
    }

    /**
     * permet de restaurer tous les ponts de vie du joueur
     * @param p
     * @return
     */
    public void restoreAllPv(Player p){
        p.recoveryMaxHealth();
    }

    /**
     * permet de sauavarger une partie
     * @param p
     * @param s
     * @return 
     */

    public void saveGame(Player p, Save s){
        s.exportSave(p);
    }

    /**
     * permet de savoir l'état du jeux (open / close)
     * @return
     */
    public boolean quitGame(){
        return false;
    }

    /**
     * permet de retourner sous fichier Json toutes les informations du Temple
     * @param
     * @return le Json
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("type", this.type);

        return json;
    }
}