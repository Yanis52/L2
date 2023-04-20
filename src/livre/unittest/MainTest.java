package livre.unittest;

import livre.*;
import livre.algorithm.*;
import livre.game.*;
import livre.game.loader.*;
import livre.game.player.*;
import livre.game.scene.*;
import livre.graphics.*;
import livre.graphics.controller.*;
import livre.graphview.*;

import java.util.*;

public class MainTest implements UnitTest{

    @Override
    public boolean isValid(){
        Boolean checker = true;
        Integer counter = 0;
        Integer validCounter = 0;
        ArrayList<Scene> livre = makeTypeBook();

        //---- Algorithm checker
        AlgorithmTest algorithmTester = new AlgorithmTest(livre);
        counter++;
        if(!algorithmTester.isValid()){
            checker=false;
        }
        else{
            validCounter++;
        }
        //----

        if(checker){
            System.out.println("Tous les tests sont valides ("+validCounter+" blocs de tests valides sur "+counter+")");
        }
        else{
            System.out.println("Error : Tous les tests ne sont pas valides ("+validCounter+" blocs de tests valides sur "+counter+")");
        }
        return checker;
    }

    public ArrayList<Scene> makeTypeBook(){ //Instanciation d'un court livre type sur lesquels se basent les tests (4 chemins vers la victoire, un chemin vers la défaite, une scène par types d'interactions, une scène non reliée)
        ArrayList<Scene> livre = new ArrayList<>();
        EveryItem itm = new EveryItem();
        EverySkill skl = new EverySkill();
        EveryMonster mstr = new EveryMonster(skl);
        Text interactTxt = new Text();
        Temple interactTemple = new Temple();
        Treasure interactTreasure = new Treasure(itm);
        Shop interactShop = new Shop(itm);
        Battle interactBattle = new Battle(mstr);
        GameOver interactGameOver = new GameOver();
        Final interactFinal = new Final();
        NullInteraction interactNull = new NullInteraction();

        Scene spawn = new Scene("Village", "spawn", interactTxt);
        spawn.setId(0);
        spawn.addNextScene(1);
        spawn.addNextScene(2);
        spawn.addNextScene(3);
        spawn.addNextScene(4);
        spawn.addNextScene(5);
        livre.add(spawn);

        Scene finished = new Scene("finisher", "finish", interactTxt);
        finished.setId(8);
        livre.add(finished);

        Scene glade = new Scene("Clairiere", "glade", interactTemple);
        glade.setId(1);
        glade.addNextScene(6);
        livre.add(glade);

        Scene lake = new Scene("Lac", "lake", interactTreasure);
        lake.setId(2);
        lake.addNextScene(6);
        livre.add(lake);

        Scene cave = new Scene("Grotte", "cave", interactBattle);
        cave.setId(3);
        cave.addNextScene(6);
        livre.add(cave);

        Scene backstreet = new Scene("Ruelle", "back_street", interactShop);
        backstreet.setId(4);
        backstreet.addNextScene(6);
        livre.add(backstreet);

        Scene cliff = new Scene("Falaise", "cliff", interactGameOver);
        cliff.setId(5);
        livre.add(cliff);

        Scene forest = new Scene("Foret", "forest", interactFinal);
        forest.setId(6);
        forest.addNextScene(8);
        livre.add(forest);

        Scene nullScene = new Scene("vide", "nullScene", interactNull);
        nullScene.setId(7);
        livre.add(nullScene);

        return livre;   
    }
}