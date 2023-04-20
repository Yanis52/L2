package livre.game.scene;

import org.json.simple.*;

public class GameOver implements Interaction{

    private String type;

    public GameOver(){
        this.type = "gameOver";
    }

    public String getType(){
        return this.type;
    }
    /**
     * permet de retourner sous fichier Json toutes les informations sur la partie
     * @param
     * @return le Json
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("type", this.type);

        return json;
    }
}