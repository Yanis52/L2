package livre.algorithm;

import livre.game.player.*;
import livre.game.scene.*;
import livre.game.loader.*;
import livre.game.*;

import livre.graphics.*;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.Collections.*;

public class DFS implements Algorithm{ //Dept-First Search: Algorithm de parcours en profondeur

    public ArrayList<Scene> livre; //Nécessite un graphe de chemins a parcourir
    public ArrayList<Scene> marked; //liste des scènes marqués (sert lors du parcours du graphe)
    public ArrayList<Scene> shorterPath; //liste des scènes menant a la victoire le plus rapidement
    public ArrayList<Scene> tmpPath;
    static float compteurV = 0; //Compteur de chemin menant à une victoire
    static float compteurL = 0; //Compteur de chemin menant à une défaite (Game Over)
    static float compteurM = 0; //Compteur des monstres du scénario
    static float compteurP = 0; //Compteur de la puissance de tous les monstres du scénario
    static boolean loaded;
    public EverySkill skl;
    public EveryMonster everymstr; //Permet d'accèder a tous les monstres (nécessite la liste des skills du scénario pour être instanciée)

    public DFS(ArrayList<Scene> livre){
        this.skl = new EverySkill();
        this.everymstr = new EveryMonster(skl);
        this.livre = livre;
        this.marked = new ArrayList<>();
        this.shorterPath = new ArrayList<>();
        this.tmpPath = new ArrayList<>();
        this.compteurV = compteurV;
        this.compteurL = compteurL;
        this.compteurM = compteurM;
        this.compteurP = compteurP;
        this.loaded = false;
    }

    @Override
    public void load(){ //Permet un load propre de l'algorithme et de ne pas réutiliser les compteurs
        if (!this.loaded){
            DFS(this.livre.get(0));
            this.loaded = true;
        }
    }

    @Override
    public Float getDif(){ //Renvoie la difficulté en % d'un livre en fonction du nombre de chemins menant à la défaite et du nombre de chemins au total
        if (!this.loaded){
            load();
        }
        float res = (this.compteurL)/(this.compteurV+this.compteurL);
        return res;
    }

    @Override
    public Float getDifMonster(){ //Renvoie la difficulté en % des monstres d'un livre en fonction du nombre de monstre et du nombre de chemins au total
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

    public ArrayList<Scene> getShortestPath(){ //Renvoie le chemin le plus court
        if (!this.loaded){
            load();
        }
        if (!this.shorterPath.contains(this.livre.get(1))){
                this.shorterPath.add(this.livre.get(1));
        }
        return this.shorterPath;
    } 

    public Integer getNbSceneInShortestPath(){ //Renvoie le nb de scènes dans le chemin le plus court
        if (!this.loaded){
            load();
        }
        ArrayList<Scene> shortestPath = getShortestPath();
        return shorterPath.size();
    }

    public void DFS(Scene scene){ //Algorithme de parcours en profondeur (récursif)
        ArrayList<Scene> tmpNxtScene = new ArrayList<Scene>();
        for (Integer idNxtScene : scene.getNextScene()){ //Permet de réorganiser la liste des NextScene en mettant les scenes qui ne sont pas des fins en premières
            for (Scene tmp : this.livre){
                if (tmp.getId() == idNxtScene && tmp.getInteraction().getType()!="final"){
                    tmpNxtScene.add(tmp);
                }
            }
        }
        for (Integer idNxtScene : scene.getNextScene()){ //Permet de réorganiser la liste des NextScene en mettant les scenes qui sont des fins (gameOver et victoire) en dernières
            for (Scene tmp : this.livre){
                if (tmp.getId() == idNxtScene && tmp.getInteraction().getType()=="final"){
                    tmpNxtScene.add(tmp);
                }
            }
        }
        this.tmpPath.add(scene);
        if (scene != this.livre.get(1)){ //this.live.get(1) est le bloc finish d'un template
            this.marked.add(scene);
        }
        if (scene ==this.livre.get(1)){
            this.compteurV++;
            if (this.tmpPath.size()<this.shorterPath.size() || this.shorterPath.size()==0){ //Compare le chemin actuel et le plus court
                this.shorterPath.clear();
                for (Scene s : this.tmpPath){
                    this.shorterPath.add(s);
                }
            }
            this.tmpPath = reinitializeTmpPath(this.tmpPath);
            this.marked.clear();
        }
        if (scene.getInteraction().getType()=="gameOver"){
            this.compteurL++;
            this.tmpPath = reinitializeTmpPath(this.tmpPath);
            this.marked.clear();
        }
        if (scene.getInteraction().getType()=="battle"){
            addCompteurRankingPower(scene);
        }
        for (Scene s : tmpNxtScene){
            if (!this.marked.contains(s)){
                DFS(s);
            }
        }
    }

    public void addCompteurRankingPower(Scene scene){ // Ajoute la puissance du monstre d'une scène dans le compteur de puissance de tous les monstres d'un livre
        Battle battle = (Battle)scene.getInteraction();
        Monster monstre = this.everymstr.getWithAlias(battle.getMonster().getAlias());
        this.compteurM++;
        Fighter monstreFighter = new Fighter(monstre.getAttack(), monstre.getDefense(), monstre.getActualHealth());
        this.compteurP+=monstreFighter.getRankingPower();
    }

    public ArrayList<Scene> reinitializeTmpPath(ArrayList<Scene> path){
        for (int i = 0, j = path.size() - 1; i < j; i++) {
            path.add(i, path.remove(j));
        }        
        ArrayList<Scene> res = new ArrayList<Scene>();
        for (Scene s : path){
            res.add(s);
        }
        for (Scene s : path){
            if (s.getNextScene().size()>1){
                for (int i = 0, j = res.size() - 1; i < j; i++) {
                    res.add(i, res.remove(j));
                }
                return res;
            }
            else{
                if (res.size()!=0){
                    res.remove(0);
                }
            }
        }
        return res;
    }

    @Override
    public String toString(){
        if (!this.loaded){
            load();
        }
        return "Algorithme DFS : \nNombre de chemin menant a la victoire : " + this.compteurV + "\nNombre de chemin menant au game over : " + this.compteurL + "\nNombre monstres : " + this.compteurM + "\nPuissance de tous les monstres : " + this.compteurP + "\nDifficultee actuelle : " + getDif() + "\nNombre de scènes dans le chemin le plus court : " + getNbSceneInShortestPath();
    }

}