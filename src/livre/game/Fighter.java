package livre.game;

import livre.game.player.*;
import java.util.*;
import org.json.simple.*;

/**
 * attack= nombre de points d'attaque du joueur
 * defense= nombre de points de defense du joueur
 * maxHelath= vie maximum du joueur
 * actualHealth= PV actuells du joueur
 * skills= liste des skills du joueur
 * isAlive= retourne si le joueur est encore en vie ou pas
 * maxSkillCount= nombre de skill maximum dans l'inventaire (ici 4)
 */
public class Fighter{
    private int attack, defense, maxHealth, actualHealth;
    private ArrayList<Skill> skills;
    private boolean isAlive;

    private int maxSkillCount;

    public Fighter(int attack, int defense, int maxHealth){
        this.attack = attack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.actualHealth = maxHealth;
        this.skills = new ArrayList<>();
        this.isAlive = true;
        this.maxSkillCount = 4;
    }

    // Getter ------------------------------- //
    public int getAttack(){
        return this.attack;
    }
    public int getDefense(){
        return this.defense;
    }
    public int getMaxHealth(){
        return this.maxHealth;
    }
    public int getActualHealth(){
        return this.actualHealth;
    }
    public ArrayList<Skill> getSkills(){
        return this.skills;
    }
    public boolean getIsAlive(){
        return this.isAlive;
    }
    public int getMaxSkillCount(){
        return this.maxSkillCount;
    }
    // ------------------------------- //
    
    // Setter ------------------------------- //
    public void setAttack(int newValue){
        this.attack = newValue;
    }
    public void setDefense(int newValue){
        this.defense = newValue;
    }
    public void setMaxHealth(int newValue){
        this.maxHealth = newValue;
    }
    public void setActualHealth(int newValue){
        this.actualHealth = newValue;
    }
    // ------------------------------- //

    // Adder ------------------------------- //
    public void addAttack(int newValue){
        this.attack += newValue;
    }
    public void addDefense(int newValue){
        this.defense += newValue;
    }
    public void addMaxHealth(int newValue){
        this.maxHealth += newValue;
    }
    public void addActualHealth(int newValue){
        this.actualHealth += newValue;
    }
    // ------------------------------- //

    // Fighting methods
    /**
     * permet de recalculer le nombre de PV du joueur suite a une attaque et de verifier si il meurt par la suite ou pas
     * @param damage
     * @reutrn le nombre de pv actuel du joueur
     */
    public void beAttack(int damage){
        this.actualHealth -= damage;
        if(this.actualHealth <= 0){
            this.isAlive = false;
        }
    }
    /**
     * permet d'utiliser un skill en le recupérant par sont ID
     * @param id_skill
     * @return le skill que l'on veut utiliser
     */
    public int useSkill(int id_skill){
        return this.skills.get(id_skill).useSkill() + this.attack;
    }
    /**
     * permet de verifier si il reste de la place pour rajouter des skills dans l'inventaire ou pas
     * @param
     * @return true ou False
     */
    // Skill adding
    public boolean checkSkillSpace(){
        if(this.skills.size() < this.maxSkillCount){
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * permet de rajouter un skill à l'inventaire si celui ci n'esr pas plein et que le nom du Skill existe
     * @param newSkill
     * @return
     */
    public void addSkill(Skill newSkill){
        if(this.checkSkillSpace() && newSkill.getName() != "null"){
            this.skills.add(newSkill);
        }
    }
    /**
     * permet de placer un skill en fonction de sont ID
     * @param id
     * @param newSkill
     * @return
     */
    public void replaceSkillWithId(int id, Skill newSkill){
        this.skills.set(id, newSkill);
    }
    /**
     * peremt obtenir la moyenne des differents skills
     * @param
     * @return le total
     */

    public int getRankingPower(){
        int total = this.attack + this.defense + this.maxHealth;
        for(Skill s : this.skills){
            total += (s.getDamage() * 100) / s.getSuccessfullChance();
        }
        int totalf = (int)total / 4;
        return totalf;
    }

    /**
     * permet de retourner sous fichier Json toutes les informations du Player
     * @param
     * @return le Json
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();

        json.put("attack", new Integer(this.attack));
        json.put("defense", new Integer(this.defense));
        json.put("actualHealth", new Integer(this.actualHealth));
        json.put("maxHealth", new Integer(this.maxHealth));
        json.put("isAlive", new Boolean(this.isAlive));

        ArrayList<JSONObject> skillsJSON = new ArrayList<>();
        for(Skill s : this.skills){
            skillsJSON.add(s.toJSONObject());
        }
        json.put("skills", skillsJSON);

        return json;

    }
}