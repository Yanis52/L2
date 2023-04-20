package livre.game.loader;


import livre.game.*;
import java.util.*;
import org.json.simple.*;


public class EveryMonster extends JSONReader{
    
    private ArrayList<Monster> allMonster;

    public EveryMonster(EverySkill allSkill){
        super("data/monsters.json");
        this.allMonster = new ArrayList<>();

        for(int i = 0; i < super.getFile().size(); i++){
            JSONObject data = (JSONObject)super.getFile().get(Integer.toString(i));
            String name = (String)data.get("name");
            String alias = (String)data.get("alias");
            String sprite = (String)data.get("sprite");

            long attack = (Long)data.get("atk");
            long defense = (Long)data.get("def");
            long maxHealth = (Long)data.get("hp");
            
            int a = (int)attack;
            int d = (int)defense;
            int h = (int)maxHealth;
            
            // unchecked error -Xlint:unchecked
            ArrayList<String> types = (ArrayList)data.get("types");
            ArrayList<String> possibleSkill = (ArrayList)data.get("possibleSkill");

            Monster newMonster = new Monster(a, d, h, name, alias, sprite, types, possibleSkill, allSkill);
            this.allMonster.add(newMonster);
        }
    }
    /**
     * Getter : permet de recuperer tout les monstres 
     * @param
     * @return tout les monstres
     */
    public ArrayList<Monster> getAllMonster(){
        return this.allMonster;
    }
    /**
     * permet de recuperer un monstre avec sont ID
     * @param id
     * @return le monstre
     */
    public Monster getWithId(int id){
        return this.allMonster.get(id);
    }
    /**
     * permet de recuperer un monstre avec sont ALias 
     * @param alias
     * @return le monstre
     */
    public Monster getWithAlias(String alias){
        for(Monster i : this.allMonster){
            if(i.getAlias().equals(alias)){
                return i;
            }
        }
        return this.getWithId(0);
    }
     /**
     * permet de recuprer un monstre au hasard parmis la liste des monstres
     * @param
     * @return le monstre
     */
    public Monster getRandomMonster(){
        Random r = new Random();
		int randomInt = r.nextInt(this.allMonster.size() - 1) + 1;
        return getWithId(randomInt);
    }

}