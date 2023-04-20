package livre.game.scene;

import livre.game.player.*;
import org.json.simple.*;



public class Final extends Text{

    public Final(){
        super();
        super.setType("final");
    }

    /**
     * verifie si la portie est gagné ou non
     * @param p
     * @return true ou false
     */
    public boolean haveWin(Player p){
        if(p.getHasGotEnoughGold() && p.getHasGotItem()){
            return true; // win
        }
        else {
            return false; // pas item + gold = perdu
        }
    }
    
    /**
     * permet de retourner sous fichier Json toutes les informations de la partie
     * @param
     * @return le Json
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("type", this.type);
        json.put("text", this.textParagraph);

        return json;
    }
}