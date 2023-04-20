package livre.graphics;

import java.util.*;
import org.json.simple.*;
import javax.swing.*;
import java.awt.*;




public class TemplateModify extends TemplatePage{

    public TemplateModify(JSONObject tree){
        super();

        this.tree = tree;

        // Onglet - Bloc
        this.onglets.add(textHTML("Bloc"), blocModifyMethod());

        // Onglet - Algorithme
        this.onglets.add(textHTML("Algorithme"), algoMethod());

        // Onglet - GraphViz
        this.onglets.add(textHTML("Visualisation"), graphVizMethod());

        this.contentPane.add(this.onglets, BorderLayout.WEST);

        updateField();

        setVisible(true);
    }

/**
 * permet de mettre a jour les différents champs de l'ui
 */    
    public void updateField(){
        // Update all field with tree loaded

        // Objective
        JSONObject objectiveTree = (JSONObject)this.tree.get("objective");
        spinner.get(0).setValue(objectiveTree.get("objectiveGold"));
        comboBox.get(0).setSelectedItem(objectiveTree.get("objectiveItem"));

        // Spawn
        JSONObject spawnTree = (JSONObject)this.tree.get("spawn");
        textField.get(0).setText((String)spawnTree.get("name"));
        JSONObject interactionSpawn = (JSONObject)spawnTree.get("interaction");
        ArrayList<String> textSpawn = (ArrayList)interactionSpawn.get("text");
        String paragraphSpawn = "";
        for(String row : textSpawn){
            paragraphSpawn += row + ";\n";
        }
        textArea.get(0).setText(paragraphSpawn);

        // Finish
        JSONObject FinishTree = (JSONObject)this.tree.get("finish");
        textField.get(1).setText((String)FinishTree.get("name"));
        JSONObject interactionFinish = (JSONObject)FinishTree.get("interaction");
        ArrayList<String> textFinish = (ArrayList)interactionFinish.get("text");
        String paragraphFinish = "";
        for(String row : textFinish){
            paragraphFinish += row + ";\n";
        }
        textArea.get(1).setText(paragraphFinish);

        // Nb Bloc Repeat
        spinner.get(1).setValue(this.tree.get("nbBlocRepeat"));
        ArrayList<JSONObject> bloc = (ArrayList)this.tree.get("bloc");
        if(bloc.size() >= 1){
            JSONObject nodeBloc = bloc.get(idBloc);
            updateBloc(nodeBloc);
        }
    }

    
    /**
     * permet de creer un bloc et tout ce qui ya dedans
     * @param node
     * @return
     */
    @Override
    public void updateBloc(JSONObject node){
        // Start
        JSONObject start_node = (JSONObject)node.get("start_node");
        textField.get(2).setText((String)start_node.get("name"));
        JSONObject interactionStart = (JSONObject)start_node.get("interaction");
        ArrayList<String> textStart = (ArrayList)interactionStart.get("text");
        String paragraphStart = "";
        for(String row : textStart){
            paragraphStart += row + ";\n";
        }
        textArea.get(2).setText(paragraphStart);

        // Finish
        JSONObject finish_node = (JSONObject)node.get("end_node");
        textField.get(3).setText((String)finish_node.get("name"));
        JSONObject interactionFinish = (JSONObject)finish_node.get("interaction");
        ArrayList<String> textFinish = (ArrayList)interactionFinish.get("text");
        String paragraphFinish = "";
        for(String row : textStart){
            paragraphFinish += row + ";\n";
        }
        textArea.get(3).setText(paragraphFinish);

        // Repeat
        long nbRepeatL = (Long)node.get("nbRepeat");
        int nbRepeat = (int)nbRepeatL;

        spinner.get(2).setValue(nbRepeat);

        ArrayList<String> possibleInteraction = (ArrayList)node.get("possibleInteraction");
        for(JCheckBox check : checkBox.get(0)){
            for(String selected : possibleInteraction){
                if(check.getText().equals(selected)){
                    check.setSelected(true);
                }
            }
        }
        ArrayList<ArrayList<String>> nameBetween = (ArrayList)node.get("nameBetween");
        String text = "";
        for(ArrayList<String> row : nameBetween){
            text += row.get(0) + ":" + row.get(1) + ";\n";
        }
        textArea.get(4).setText(text);
    }

}