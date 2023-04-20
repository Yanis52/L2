package livre.game.scene;


import java.util.*;
import org.json.simple.*;

/**
 * type = string
 * textParagraph= liste de paragraphes
 * id = id du texte
 */
public class Text implements Interaction{

    public String type;
    public ArrayList<String> textParagraph;
    private int id;

    public Text(){
        this.type = "text";
        this.textParagraph = new ArrayList<>();
        this.id = 0;
    }

    // ----------GETTER------------
    public String getType(){
        return this.type;
    }

    public ArrayList<String> getTextParagraph(){
        return this.textParagraph;
    }
    // ----------------------

    // ---------SETTER-----------
    public void setType(String type){
        this.type = type;
    }
    // ---------------------------

    /**
     * permet d'ajouter du texte au paragraphe 
     * @param text
     * @return
     */

    public void addTextParagraph(String text){
        this.textParagraph.add(text);
    }

    /**
     * permet de mettre le texte dans l'ordre
     * @param
     * @return le texte
     */

    public String getTextInOrder(){
        if(id == this.textParagraph.size()){
            this.resetId();
            return "";
        }
        else{
            String txt = this.textParagraph.get(id);
            id ++;
            return txt;
        }
    }
    /**
     * permet de reset les id du texte
     * @param
     * @return
     */
    public void resetId(){
        this.id = 0;
    }
    /**
     * permet de retourner sous fichier Json toutes les informations du Text
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