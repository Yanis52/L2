package livre.game.scene;


import java.util.*;
import org.json.simple.*;

/**
 * name= nom de la scene
 * alias = alias de la scene
 * id = id de la scene
 * nextScene = scene suivante
 * interaction = interactions avec la scene
 */

public class Scene {

    private String name, alias;
    private int id;
    private ArrayList<Integer> nextScene; 
    private Interaction interaction;

    public static int idCount = 0;

    public Scene(String name, String alias, Interaction interaction){
        this.name = name;
        this.alias = alias;
        this.interaction = interaction;
        this.nextScene = new ArrayList<>(); // Stock les id des scenes
        this.id = idCount;

        idCount ++;
    }

    public Scene(String name, String alias){
        this.name = name;
        this.alias = alias;
        this.nextScene = new ArrayList<>();
        this.id = idCount;

        idCount ++;
    }

    // ----------GETTER------------
    public String getName(){
        return this.name;
    }
    public String getAlias(){
        return this.alias;
    }
    public int getId(){
        return this.id;
    }
    public ArrayList<Integer> getNextScene(){
        return this.nextScene;
    }
    public Interaction getInteraction(){
        return this.interaction;
    }
    // ---------SETTER-------------
    public void setId(int newid){
        this.id = newid;
    }
    public void setInteraction(Interaction inter){
        this.interaction = inter;
    }
    //------------------------------

    /**
     * permet d'ajouter la scene suivante a travers sont id
     * @param id
     * @return 
     */


    public void addNextScene(int id){
        this.nextScene.add(id);
    }
    /**
     * permet de choisir la prochaine scene
     * @param i
     * @return la prochaine scene
     */

    public int chooseNextScene(int i){
        return this.nextScene.get(i);
    }

    public String toString(){
        return
        "id\t:" + this.id +
        "\nname\t: " + this.name +
        "\nalias\t: " + this.alias +
        "\nnext scene\t:" + this.nextScene.toString() +
        "\ninteraction\t:" + this.interaction.toString();
    }
    /**
     * permet de retourner sous fichier Json toutes les informations  relatives a la scene
     * @param
     * @return le Json
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("name", this.name);
        json.put("alias", this.alias);
        json.put("interaction", this.interaction.toJSONObject());

        ArrayList<Integer> nextSceneJSON = new ArrayList<>();
        for(int s : this.nextScene){
            nextSceneJSON.add(s);
        }
        json.put("nextScene", nextSceneJSON);

        return json;
    }
    
}