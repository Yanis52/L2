package livre.game.scene;



import org.json.simple.*;


public interface Interaction {

    public String getType();
    public JSONObject toJSONObject();
}