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

public class AlgorithmTest implements UnitTest{

    public ArrayList<Scene> livre;
    public DFS parcoursLongueur;
    public BFS parcoursLargueur;

    public AlgorithmTest(ArrayList<Scene> livre){ 
        this.livre= livre;
        this.parcoursLongueur = new DFS(livre);
        this.parcoursLargueur = new BFS(livre);
    }

    @Override
    public boolean isValid(){
        Boolean checker = true;
        if(this.parcoursLongueur.getDif()!=0.20000000298023224){
            System.out.println("Error : DFS -> getDif() not working (resultat souhaite: 0.2 / resultat obtenu:"+this.parcoursLongueur.getDif()+")");
            checker=false;
        }
        if(this.parcoursLongueur.getDifMonster()!=0.20000000298023224){
            System.out.println("Error : DFS -> getDifMonster() not working (resultat souhaite: 0.2 / resultat obtenu :"+this.parcoursLongueur.getDif()+")");
            checker=false;
        }
        if(this.parcoursLongueur.getNbSceneInShortestPath()!=4){
            System.out.println("Error : DFS -> getNbSceneInShortestPath() not working (resultat souhaite: 4 / resultat obtenu :"+this.parcoursLongueur.getNbSceneInShortestPath()+")");
            checker=false;
        }
        if(this.parcoursLargueur.getDif()!=0.20000000298023224){
            System.out.println("Error : BFS -> getDif() not working (resultat souhaite: 0.2 / resultat obtenu :"+this.parcoursLongueur.getDif()+")");
            checker=false;
        }
        if(this.parcoursLargueur.getDifMonster()!=0.20000000298023224){
            System.out.println("Error : BFS -> getDifMonster() not working (resultat souhaite: 0.2 / resultat obtenu :"+this.parcoursLongueur.getDif()+")");
            checker=false;
        }
        if(this.parcoursLargueur.getNbSceneNotRelied()!=1){
            System.out.println("Error : BFS -> getNbSceneNotRelied() not working (resultat souhaite: 1 / resultat obtenu :"+this.parcoursLargueur.getNbSceneNotRelied()+")");
            checker=false;
        }
        return checker;
    }

}