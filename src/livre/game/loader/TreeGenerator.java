package livre.game.loader;


import livre.game.scene.*;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;


public class TreeGenerator extends JSONReader{

    public TreeGenerator(){
        super("data/static_tree.json");
    }
    /**
     * permet d'importer un arbre préchargé
     * @param s
     * @param allSkill
     * @param allItem
     * @param allMonster
     * @return l'arbre
     */
    public ArrayList<Scene> importSavedTree(Save s, EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        return s.importTree(allSkill, allItem, allMonster);
    }

    /**
     * permet de charger l'arbre de base
     * @param allSkill
     * @param allItem
     * @param allMonster
     * @return l'arbre
     */
    public ArrayList<Scene> loadBasicTree(EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        return loadTreeWithTemplate("data/static_tree.json", allSkill, allItem, allMonster);
        // JSONObject data = super.getFile();

        // ArrayList<Scene> tree = new ArrayList<>();

        // JSONObject spawnData = (JSONObject)data.get("spawn");
        // JSONObject finishData = (JSONObject)data.get("finish");

        // Scene spawn = loadNodeTree(spawnData, allSkill, allItem, allMonster);
        // Scene finish = loadNodeTree(finishData, allSkill, allItem, allMonster);

        // tree.add(spawn);
        // tree.add(finish);

        // long nbBlocRepeatL = (long)data.get("nbBlocRepeat");
        // int nbBlocRepeat = (int)nbBlocRepeatL;

        // Scene topScene = spawn;

        // for(int i = 0; i<nbBlocRepeat; i++){

        //     ArrayList<JSONObject> blocData = (ArrayList)data.get("bloc");
            
        //     for(JSONObject bloc : blocData){
        //         JSONObject startNodeData = (JSONObject)bloc.get("start_node");
        //         JSONObject endNodeData = (JSONObject)bloc.get("end_node");

        //         Scene startNode = loadNodeTree(startNodeData, allSkill, allItem, allMonster);
        //         Scene endNode = loadNodeTree(endNodeData, allSkill, allItem, allMonster);

        //         tree.add(startNode);
        //         tree.add(endNode);

        //         ArrayList<ArrayList<String>> nameBetween = (ArrayList)bloc.get("nameBetween");

        //         ArrayList<String> aliasList = new ArrayList<>();
        //         ArrayList<String> nameList = new ArrayList<>();

        //         for(ArrayList<String> tuple : nameBetween){
        //             aliasList.add(tuple.get(0));
        //             nameList.add(tuple.get(1));
        //         }

        //         ArrayList<String> possibleInteraction = (ArrayList)bloc.get("possibleInteraction");

        //         long nbRepeatL = (Long)bloc.get("nbRepeat");
        //         int nbRepeat = (int)nbBlocRepeatL;

        //         createRandScene(startNode, endNode, nbRepeat, nameList, aliasList, possibleInteraction, tree, allSkill, allItem, allMonster);

        //         topScene.addNextScene(startNode.getId());
        //         topScene = endNode;

        //     }
        // }

        // topScene.addNextScene(finish.getId());

        // return tree;
    }
    /**
     * permet de creer une scene aléatoire
     * @param startNode
     * @param endNode
     * @param nbRepeat
     * @param nameList
     * @param aliasList
     * @param possibleInteraction
     * @param tree
     * @param allSkill
     * @param allItem
     * @param allMonster
     */

    public void createRandScene(Scene startNode, Scene endNode, int nbRepeat, ArrayList<String> nameList, ArrayList<String> aliasList, ArrayList<String> possibleInteraction, ArrayList<Scene> tree, EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        
        ArrayList<ArrayList<Scene>> data = new ArrayList<>();

        for(int i = 0; i < nbRepeat; i++){
            ArrayList<Scene> row = new ArrayList<>();
            for(int j = 0; j < aliasList.size(); j++){
                Scene scn = new Scene(nameList.get(j), aliasList.get(j));
                int index = (int)(Math.random() * possibleInteraction.size());
                SetInteractionToScene.doIt(scn, possibleInteraction.get(index), allSkill, allItem, allMonster);
                row.add(scn);
                tree.add(scn);
            }
            data.add(row);
        }

        for(int i = 0; i < data.size()-1; i++){
            for(Scene top : data.get(i)){
                for(Scene bot : data.get(i+1)){
                    top.addNextScene(bot.getId());
                }
            }
        }

        for(Scene firstRow : data.get(0)){
            startNode.addNextScene(firstRow.getId());
        }

        for(Scene lastRow : data.get(data.size()-1)){
            lastRow.addNextScene(endNode.getId());
        }
    }
    /**
     * permet de charger l'arborescence des nœuds
     * @param node
     * @param allSkill
     * @param allItem
     * @param allMonster
     * @return
     */
    public Scene loadNodeTree(JSONObject node, EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        String name = (String)node.get("name");
        String alias = (String)node.get("alias");

        JSONObject interaction = (JSONObject)node.get("interaction");
        String type = (String)interaction.get("type");

        Scene returned =  new Scene(name, alias);

        SetInteractionToScene.doItDetail(returned, type, interaction, allSkill, allItem, allMonster);

        return returned;
    }
    /**
     * permet de charger un arbre template
     * @param pathJson
     * @param allSkill
     * @param allItem
     * @param allMonster
     * @return
     */
    public ArrayList<Scene> loadTreeWithTemplate(String pathJson, EverySkill allSkill, EveryItem allItem, EveryMonster allMonster){
        Scene.idCount = 0;
        JSONParser parser = new JSONParser();
        try {
            String path = new File(pathJson).getAbsolutePath(); 
            Object obj = parser.parse(new FileReader(path));

            JSONObject data = (JSONObject)obj;

            ArrayList<Scene> tree = new ArrayList<>();

            JSONObject spawnData = (JSONObject)data.get("spawn");
            JSONObject finishData = (JSONObject)data.get("finish");

            

            Scene spawn = loadNodeTree(spawnData, allSkill, allItem, allMonster);
            Scene finish = loadNodeTree(finishData, allSkill, allItem, allMonster);

            tree.add(spawn);
            tree.add(finish);

            long nbBlocRepeatL = (Long)data.get("nbBlocRepeat");
            int nbBlocRepeat = (int)nbBlocRepeatL;

            Scene topScene = spawn;

            for(int i = 0; i<nbBlocRepeat; i++){

                ArrayList<JSONObject> blocData = (ArrayList)data.get("bloc");
                
                for(JSONObject bloc : blocData){
                    JSONObject startNodeData = (JSONObject)bloc.get("start_node");
                    JSONObject endNodeData = (JSONObject)bloc.get("end_node");

                    Scene startNode = loadNodeTree(startNodeData, allSkill, allItem, allMonster);
                    Scene endNode = loadNodeTree(endNodeData, allSkill, allItem, allMonster);
                    tree.add(startNode);
                    tree.add(endNode);

                    ArrayList<ArrayList<String>> nameBetween = (ArrayList)bloc.get("nameBetween");

                    ArrayList<String> aliasList = new ArrayList<>();
                    ArrayList<String> nameList = new ArrayList<>();

                    for(ArrayList<String> tuple : nameBetween){
                        aliasList.add(tuple.get(0));
                        nameList.add(tuple.get(1));
                    }

                    ArrayList<String> possibleInteraction = (ArrayList)bloc.get("possibleInteraction");

                    long nbRepeatL = (Long)bloc.get("nbRepeat");
                    int nbRepeat = (int)nbBlocRepeatL;

                    createRandScene(startNode, endNode, nbRepeat, nameList, aliasList, possibleInteraction, tree, allSkill, allItem, allMonster);

                    topScene.addNextScene(startNode.getId());
                    topScene = endNode;
                }
            }

            for (Scene scn : tree){
                if (scn.getInteraction().getType()=="gameOver"){
                    scn.getNextScene().clear();
                }
            }

            topScene.addNextScene(finish.getId());

            return tree;
        }
        catch(Exception e){
            System.out.println("Erreur");
        }

        ArrayList<Scene> a = new ArrayList<>();
        return a;
    }    
}