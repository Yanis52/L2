package livre.algorithm;

import livre.game.player.*;
import livre.game.scene.*;
import livre.game.loader.*;
import livre.game.*;

import livre.graphics.*;

import java.io.*;
import java.util.*;
import java.lang.Math;
import org.json.simple.*;
import org.json.simple.parser.*;

public class BFS implements Algorithm{ //Dept-First Search: Algorithm de parcours en profondeur

    public ArrayList<Scene> livre; //Nécessite un graphe de chemins a parcourir
    static ArrayList<Scene> processed;
    static float compteurV = 0; //Compteur de chemin menant à une victoire
    static float compteurL = 0; //Compteur de chemin menant à une défaite (Game Over)
    static float compteurM = 0; //Compteur des monstres du scénario
    static float compteurP = 0; //Compteur de la puissance de tous les monstres du scénario
    static boolean loaded;

    public EverySkill skl;
    public EveryMonster everymstr; //Permet d'accèder a tous les monstres (nécessite la liste des skills du scénario pour être instanciée)

    public BFS(ArrayList<Scene> livre){
        this.skl = new EverySkill();
        this.everymstr = new EveryMonster(skl);
        this.livre = livre;
        this.compteurV = compteurV;
        this.compteurL = compteurL;
        this.compteurM = compteurM;
        this.compteurP = compteurP;
        this.loaded = false;;
        this.processed = new ArrayList<>();
    }
    
    @Override
    public void load(){ //Permet un load propre de l'algorithme et de ne pas réutiliser les compteurs
        if (!this.loaded){
            BFS(this.livre.get(0));
            this.loaded = true;
        }
    }

    @Override
    public Float getDif(){ //Renvoie la difficulté en % d'un livre en fonction du nombre de chemins menant à la défaite et du nombre de chemins au total
        if (!this.loaded){
            load();
        }
        float res = (this.compteurL)/(this.compteurV+this.compteurL)    ;
        return res;
    }
    
    @Override
    public Float getDifMonster(){  //Renvoie la difficulté en % des monstres d'un livre en fonction du nombre de monstre et du nombre de chemins au total
        if (!this.loaded){
            load();
        }
        float res = (this.compteurM)/(this.compteurV+this.compteurL);
        return res;
    }

    @Override
    public Float getAveragePowerMonster(){ //Renvoie la difficulté en % des monstres d'un livre en fonction du nombre de monstre et du nombre de chemins au total
        if (!this.loaded){
            load();
        }
        float res = (this.compteurP)/(this.compteurM);
        return res;
    }

    public int getNbSceneNotRelied(){ //Renvoie le nb de scènes non reliées dans un livre
        if (!this.loaded){
            load();
        }
        return this.livre.size()-this.processed.size();
    }

    public void BFS(Scene scenes){ //Algorithme de parcours en largeur (itératif)
        ArrayList<Scene> queue = new ArrayList<Scene>();
        ArrayList<Scene> tmpNxtScene = new ArrayList<Scene>();
        queue.add(scenes);
        this.processed.add(scenes);
        while (queue.size() != 0){
            scenes = queue.get(0);
            if (!this.processed.contains(scenes)){
                this.processed.add(scenes);
            }

            tmpNxtScene.clear();
            for (Integer idNxtScene : scenes.getNextScene()){
                for (Scene tmp : this.livre){
                    if (tmp.getId() == idNxtScene){
                        tmpNxtScene.add(tmp);
                    }
                }
            }
            if (scenes.getInteraction().getType()=="final"){
                this.compteurV++;
            }
            if (scenes.getInteraction().getType()=="gameOver"){
                this.compteurL++;
            }
            if (scenes.getInteraction().getType()=="battle"){
                addCompteurRankingPower(scenes);
            }
            for (Scene sc : tmpNxtScene){
                queue.add(sc);
            }
            queue.remove(0);
        }
    }

    public void addCompteurRankingPower(Scene scenes){ // Ajoute la puissance du monstre d'une scène dans le compteur de puissance de tous les monstres d'un livre
        Battle battle = (Battle)scenes.getInteraction();
        Monster monstre = this.everymstr.getWithAlias(battle.getMonster().getAlias());
        this.compteurM++;
        Fighter monstreFighter = new Fighter(monstre.getAttack(), monstre.getDefense(), monstre.getActualHealth());
        this.compteurP+=monstreFighter.getRankingPower();
    }

    @Override
    public String toString(){
        if (!this.loaded){
            load();
        }
        return "Algorithme BFS : \nNombre de chemin menant a la victoire : " + this.compteurV + "\nNombre de chemin menant au game over : " + this.compteurL + "\nNombre monstres : " + this.compteurM + "\nPuissance de tous les monstres : " + this.compteurP + "\nDifficultee actuelle : " + getDif() + "\nNombre de scenes non reliees : " + getNbSceneNotRelied();
    }
}
