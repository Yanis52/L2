package livre.game;
import livre.game.player.*;
import livre.game.loader.*;
import java.util.*;
import org.json.simple.*;

/**
 * name = nom du monstre
 * alias = alias du monstre
 * sprite = image du monstre
 * types = types des monstres
 * possibleSkill = pouvoirs possibles d'un monstre
 */
public class Monster extends Fighter{
    private String name, alias, sprite;
    private ArrayList<String> types, possibleSkill;

    public Monster(int attack, int defense, int maxHealth, String name, String alias, String sprite, ArrayList<String> types, ArrayList<String> possibleSkill, EverySkill allSkill){
        super(attack, defense, maxHealth);
        this.name = name;
        this.alias = alias;
        this.sprite = sprite;
        this.types = types;
        this.possibleSkill = possibleSkill;

        Random r = new Random();
        // Load random Skill
        for(int i = 0; i<super.getMaxSkillCount(); i++){
            if(this.possibleSkill.size() != 0){
                int id_ = r.nextInt(this.possibleSkill.size());
                String aliasSkillSelected = this.possibleSkill.get(id_);
                Skill skillSelected = allSkill.getWithAlias(aliasSkillSelected);

                super.addSkill(skillSelected);
            }
        }

        // Check null Skill
        for(int i = 0; i <super.getSkills().size() ; i++){
            if(super.getSkills().get(i).getName().equals("null")){
                Skill s = super.getSkills().get(i);

                int id_ = r.nextInt(this.possibleSkill.size());
                String aliasSkillSelected = this.possibleSkill.get(id_);
                Skill skillSelected = allSkill.getWithAlias(aliasSkillSelected);

                super.replaceSkillWithId(i, skillSelected);
            }
        }
    }

    // Getter ------------------------------- //
    public String getName(){
        return this.name;
    }
    public String getAlias(){
        return this.alias;
    }
    public String getSprite(){
        return this.sprite;
    }
    public ArrayList<String> getTypes(){
        return this.types;
    }
    public ArrayList<String> getPossibleSkill(){
        return this.possibleSkill;
    }
    // ------------------------------- //
    /**
     * permet d'utiliser un skill random
     * @param
     * @return le skill tiré au sort aléatoirement parmis la liste des skills
     */
    public int useSkill(){
        Random r = new Random();
        int id_ = r.nextInt(3);
        System.out.println(id_);
        return super.getSkills().get(id_).useSkill();
    }

    public String toString(){
        return 
        "name : " + this.name +
        "\nalias : " + this.alias +
        "\nsprite : " + this.sprite +
        "\ntypes : " + this.types.toString() +
        "\nskills : " + super.getSkills();
    }
    /**
     * permet de retourner sous fichier Json toutes les informations du Monster
     * @param
     * @return le Json 
     */

    public JSONObject toJSONObject(){
        JSONObject json = super.toJSONObject(); 
        json.put("name", this.name);
        json.put("alias", this.alias);
        json.put("sprite", this.sprite);

        // ArrayList<String> typesL = new ArrayList<>();
        // for(String s : this.types){
        //     types.add(s);
        // }
        json.put("types", types);

        // ArrayList<String> possibleSkillL = new ArrayList<>();
        // for(String s : this.possibleSkill){
        //     possibleSkillL.add(s);
        // }
        json.put("possibleSkill", possibleSkill);

        return json;
    }
}