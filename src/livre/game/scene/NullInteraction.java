package livre.game.scene;

import org.json.simple.*;



public class NullInteraction implements Interaction{
    public String type;

    public NullInteraction(){
        this.type = "null";
    }

    public String getType(){
        return this.type;
    }
    /**
     * permet de retourner sous fichier Json toutes les informations de la partie
     * @param
     * @return le Json
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("type", this.type);

        return json;
    }
}