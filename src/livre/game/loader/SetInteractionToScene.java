package livre.game.loader;


import livre.game.scene.*;
import java.util.*;
import org.json.simple.*;



public class SetInteractionToScene{

    /**
     * permet d'effectuer une interaction et de l'ajouter a la scene
     * @param returned
     * @param type
     * @param interaction
     * @param allSkill
     * @param allItem
     * @param allMonster
     */
    public static void doItDetail(Scene returned, String type, JSONObject interaction, EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        if(type.equals("text")){
            Text inter = new Text();
            ArrayList<String> paragraph = (ArrayList)interaction.get("text");
            for(String s : paragraph){
                inter.addTextParagraph(s);
            }
            returned.setInteraction(inter);
        }
        else if(type.equals("battle")){
            String monsterAlias = (String)interaction.get("monster");
            Battle inter = new Battle(allMonster.getWithAlias(monsterAlias));
            returned.setInteraction(inter);
        }

        else if(type.equals("shop")){
            Shop inter = new Shop();
            ArrayList<String> items = (ArrayList)interaction.get("items");
            for(String s : items){
                inter.addItem(allItem.getWithAlias(s));
            }
            returned.setInteraction(inter);
        }

        else if(type.equals("temple")){
            Temple inter = new Temple();
            returned.setInteraction(inter);
        }

        else if(type.equals("foundItem")){
            FoundItem inter = new FoundItem(allItem);
            String aliasItem = (String)interaction.get("item");
            inter.setFoundItem(allItem.getWithAlias(aliasItem));
            returned.setInteraction(inter);
        }
        
        else if(type.equals("treasure")){
            Treasure inter = new Treasure(allItem);
            String aliasItem = (String)interaction.get("item");
            inter.setTreasureItem(allItem.getWithAlias(aliasItem));
            returned.setInteraction(inter);
        }

        else if(type.equals("final")){
            Final inter = new Final();
            ArrayList<String> paragraph = (ArrayList)interaction.get("text");
            for(String s : paragraph){
                inter.addTextParagraph(s);
            }
            returned.setInteraction(inter);
        }

        else if(type.equals("gameOver")){
            GameOver inter = new GameOver();
            returned.setInteraction(inter);
        }

        else {
            returned.setInteraction(new NullInteraction());
        }
    }
    /**
     * 
     * @param returned
     * @param type
     * @param allSkill
     * @param allItem
     * @param allMonster
     */
    public static void doIt(Scene returned, String type, EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        if(type.equals("text")){
            Text inter = new Text();
            returned.setInteraction(inter);
        }
        else if(type.equals("battle")){
            Battle inter = new Battle(allMonster);
            returned.setInteraction(inter);
        }

        else if(type.equals("shop")){
            Shop inter = new Shop(allItem);
            returned.setInteraction(inter);
        }

        else if(type.equals("temple")){
            Temple inter = new Temple();
            returned.setInteraction(inter);
        }

        else if(type.equals("foundItem")){
            FoundItem inter = new FoundItem(allItem);
            returned.setInteraction(inter);
        }
        
        else if(type.equals("treasure")){
            Treasure inter = new Treasure(allItem);
            returned.setInteraction(inter);
        }

        else if(type.equals("final")){
            Final inter = new Final();
            returned.setInteraction(inter);
        }

        else if(type.equals("gameOver")){
            GameOver inter = new GameOver();
            returned.setInteraction(inter);
        }

        else {
            returned.setInteraction(new NullInteraction());
        }
    }
}