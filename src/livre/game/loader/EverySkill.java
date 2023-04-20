package livre.game.loader;

import livre.game.player.*;
import java.util.*;
import org.json.simple.*;



public class EverySkill extends JSONReader{
    
    private ArrayList<Skill> allSkill;

    public EverySkill(){
        super("data/skills.json");
        this.allSkill = new ArrayList<>();

        for(int i = 0; i < super.getFile().size(); i++){
            JSONObject data = (JSONObject)super.getFile().get(Integer.toString(i));
            String name = (String)data.get("name");
            String type = (String)data.get("type");
            String alias = (String)data.get("alias");

            long attack = (Long)data.get("basePower");
            long chance = (Long)data.get("successChance");
            
            int a = (int)attack;
            int c = (int)chance;

            Skill newSkill = new Skill(name, alias, type, a, c);
            this.allSkill.add(newSkill);
        }
    }
    
    /**
    * Getter : permet de recuperer tout les skills 
     * @param
     * @return tout les skills
     */
    public ArrayList<Skill> getAllSkill(){
        return this.allSkill;
    }

    /**
     * permet de recuprer un skill avec son ID
     * @param id
     * @return le skill
     */
    public Skill getWithId(int id){
        return this.allSkill.get(id);
    }
    /**
     * permet de recuperer un skill grace a sont nom
     * @param name
     * @return le skill voulu
     */
    public Skill getWithName(String name){
        for(Skill i : this.allSkill){
            if(i.getName().equals(name)){
                return i;
            }
        }
        return this.getWithId(0);
    }
    /**
     * permet de recuperer un skill grace a sont alias
     * @param alias
     * @return le skill voulu
     */
    public Skill getWithAlias(String alias){
        for(Skill i : this.allSkill){
            if(i.getAlias().equals(alias)){
                return i;
            }
        }
        return this.getWithId(0);
    }
    /**
     * permet de recuperer aléatoirement un skill parmis les autres
     * @return le skill
     */
    public Skill getRandomSkill(){
        Random r = new Random();
		int randomInt = r.nextInt(this.allSkill.size() - 1) + 1;
        return getWithId(randomInt);
    }
}