package livre.graphics.controller;


import livre.game.scene.*;
import livre.algorithm.*;
import livre.graphview.*;
import livre.graphics.*;
import java.util.*;
import org.json.simple.*;
import javax.swing.event.ChangeEvent;
import javax.swing.*;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import org.apache.commons.collections15.Transformer;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;


public class ControlTemplatePage{
    private TemplatePage vue;

    public ControlTemplatePage(TemplatePage vue){
        this.vue = vue;
    }

    public String textHTML(String text){
        String css = "    <style>        * {            font-family: Arial;            color: #212121;        }        h1 {            font-style: italic;       }    </style>    ";
        String startHtml = "<html>" + css + "<h3>";
        String endHtml = "</h3></html>";

        return startHtml + text + endHtml;
    }

    // Method
    public void returnAction(ActionEvent event){
        /** 
        Permet de retourner a la homePage, avec un message de validation
        */
        int res = JOptionPane.showOptionDialog(
            this.vue, 
            "Etes vous sur de quitter ?",
            "Confirmation", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            new Object[] {textHTML("Oui"), textHTML("Non")}, 
            JOptionPane.YES_OPTION
        );

        if (res == JOptionPane.YES_OPTION) {
            new Home();
            this.vue.dispose();
        } 
    }

    public void showDocAction(ActionEvent e){
        File htmlFile = new File("info/template_documentation/index.html");
        try{
            Desktop.getDesktop().browse(htmlFile.toURI());
        }
        catch(Exception error){
            error.printStackTrace();
        }
    }

    public void valideSpawnAction(ActionEvent event){
        /** 
        Enregistre dans le JSONObject Spawn, les informations entrées
        pour le noeud spawn
        */
        JTextField nameSpawn = this.vue.textField.get(0);
        JTextArea textAreaSpawn = this.vue.textArea.get(0);

        JLabel spawnBot = this.vue.botJLabel.get(0);

        if(!nameSpawn.getText().equals("")){
            Scene spawn = new Scene(nameSpawn.getText(), "spawn", new Text());
            Text text = (Text)spawn.getInteraction();

            String dialogueRow = textAreaSpawn.getText().replace("\n", "");
            String[] dialogue = dialogueRow.split(";");
            for(String row : dialogue){
                text.addTextParagraph(row);
            }

            this.vue.spawn = spawn.toJSONObject();
            this.vue.spawn.remove("nextScene");
            this.vue.spawn.remove("id");

            this.vue.valideArray.add(1);
            spawnBot.setText("spawn : OK");
        }
    }

    public void valideFinishAction(ActionEvent event){
        /** 
        Enregistre dans le JSONObject Finish, les informations entrées
        pour le noeud finish
        */
        JTextField nameFinish = this.vue.textField.get(1);
        JTextArea textAreaFinish = this.vue.textArea.get(1);

        JLabel finishBot = this.vue.botJLabel.get(1);

        if(!nameFinish.getText().equals("")){
            Scene finish = new Scene(nameFinish.getText(), "finish", new Text());
            Text text = (Text)finish.getInteraction();

            String dialogueRow = textAreaFinish.getText().replace("\n", "");
            String[] dialogue = dialogueRow.split(";");
            for(String row : dialogue){
                text.addTextParagraph(row);
            }

            this.vue.finish = finish.toJSONObject();
            this.vue.finish.remove("nextScene");
            this.vue.finish.remove("id");
            
            this.vue.valideArray.add(2);
            finishBot.setText("finish : OK" );
        }
    }

    public void valideObjectiveAction(ActionEvent event){
        // Marche pas !
        JLabel objectiveBot = this.vue.botJLabel.get(2);

        JSpinner countGold = this.vue.spinner.get(0);
        JComboBox objectiveItemSelec = this.vue.comboBox.get(0);

        this.vue.objective.put("objectiveGold", countGold.getValue());

        if(objectiveItemSelec.getSelectedItem().equals("random")){
            this.vue.objective.put("objectiveItem", this.vue.allItem.getRandomItem().getAlias());
        }
        else {
            this.vue.objective.put("objectiveItem", this.vue.allItem.getWithAlias((String)objectiveItemSelec.getSelectedItem()).getAlias());
        }
        
        this.vue.valideArray.add(3);
        objectiveBot.setText("objective : OK" );
    }

    public void nbBlocRepeatAction(ActionEvent event){
        this.vue.total.put("nbBlocRepeat", this.vue.spinner.get(1).getValue());
        this.vue.valideBlocNodeArray.add(1);
    }

    public void spawnBlocAction(ActionEvent event){
        JTextField nameSpawnBloc = this.vue.textField.get(2);
        JTextArea textAreaSpawn = this.vue.textArea.get(2);

        if(!nameSpawnBloc.getText().equals("")){
            String alias = nameSpawnBloc.getText().toLowerCase().replace(" ", "_");
            Scene spawn = new Scene(nameSpawnBloc.getText(), alias, new Text());
            Text text = (Text)spawn.getInteraction();

            String dialogueRow = textAreaSpawn.getText().replace("\n", "");
            String[] dialogue = dialogueRow.split(";");
            for(String row : dialogue){
                text.addTextParagraph(row);
            }

            JSONObject spawnNode = spawn.toJSONObject();
            spawnNode.remove("nextScene");
            spawnNode.remove("id");

            this.vue.blocNode.put("start_node", spawnNode);
            this.vue.valideBlocNodeArray.add(2);
        }
    }

    public void finishBlocButton(ActionEvent event){
        JTextField nameFinishBloc = this.vue.textField.get(3);
        JTextArea textAreaFinish = this.vue.textArea.get(3);

        if(!nameFinishBloc.getText().equals("")){
            String alias = nameFinishBloc.getText().toLowerCase().replace(" ", "_");
            Scene finish = new Scene(nameFinishBloc.getText(), alias, new Final());
            Final text = (Final)finish.getInteraction();

            String dialogueRow = textAreaFinish.getText().replace("\n", "");
            String[] dialogue = dialogueRow.split(";");
            for(String row : dialogue){
                text.addTextParagraph(row);
            }

            JSONObject finishNode = finish.toJSONObject();
            finishNode.remove("nextScene");
            finishNode.remove("id");

            this.vue.blocNode.put("end_node", finishNode);
            this.vue.valideBlocNodeArray.add(3);
        }
    }    

    public void blocInteractionAction(ActionEvent event){
        this.vue.blocNode.put("nbRepeat", this.vue.spinner.get(1).getValue());

        ArrayList<ArrayList<String>> nameBetween = new ArrayList<>();

        String dialogueRow = this.vue.textArea.get(4).getText().replace("\n", "");
        String[] dialogue = dialogueRow.split(";");
        for(String row : dialogue){
            ArrayList<String> tupleFinal = new ArrayList<>();

            String[] tuple = row.split(":");
            if(tuple.length == 1){
                String alias = tuple[0].toLowerCase().replace(" ", "_");
                tupleFinal.add(alias);
                tupleFinal.add(tuple[0]);    
            }
            else {
                tupleFinal.add(tuple[1]);
                tupleFinal.add(tuple[0]);  
            }

            nameBetween.add(tupleFinal);
        }
        this.vue.blocNode.put("nameBetween", nameBetween);

        ArrayList<String> boxRes = new ArrayList<>();
        for(JCheckBox check : this.vue.checkBox.get(0)){
            if(check.isSelected()){
                boxRes.add(check.getText());
            }
        }
        this.vue.blocNode.put("possibleInteraction", boxRes);
    }

    public void valideBlocAction(ActionEvent event){
        ArrayList<String> keys = new ArrayList<>(Arrays.asList(
            "start_node",
            "end_node",
            "nbRepeat",
            "nameBetween",
            "possibleInteraction"
        ));

        int count = 0;
        for(String key : keys){
            if(this.vue.blocNode.containsKey(key)){
                count ++;
            }
            else{
                break;
            }
        }

        if(count == keys.size()){
            // Modale validé, + clean les champs + add this.bloc
            JOptionPane.showMessageDialog(this.vue, "Bloc valide et ajoute !");

            // Clean les champs
            this.vue.textField.get(2).setText("");
            this.vue.textField.get(3).setText("");

            this.vue.textArea.get(2).setText("");
            this.vue.textArea.get(3).setText("");
            this.vue.textArea.get(4).setText("");

            this.vue.spinner.get(1).setValue(1);

            for(JCheckBox box : this.vue.checkBox.get(0)){
                box.updateUI();
            }

            this.vue.blocArray.add(this.vue.blocNode);
            this.vue.blocNode = new JSONObject();

            this.vue.botJLabel.get(3).setText("bloc : OK");
            this.vue.valideArray.add(4);
        }
        else {
            JOptionPane.showMessageDialog(this.vue, "Un champ n'a pas ete complete (correctement).", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void saveAction(ActionEvent event){
        // Spawn, Finish, Objective, Bloc de validé
        if(this.vue.valideArray.contains(1) && this.vue.valideArray.contains(2) && this.vue.valideArray.contains(3) && this.vue.valideArray.contains(4)){
            try{
                this.vue.total.put("spawn", this.vue.spawn);
                this.vue.total.put("finish", this.vue.finish);
                this.vue.total.put("objective", this.vue.objective);
                this.vue.total.put("bloc", this.vue.blocArray);
                this.vue.total.put("nbBlocRepeat", this.vue.spinner.get(1).getValue());

                String path = JOptionPane.showInputDialog(this.vue, "Entrez le nom du fichier");  

                FileWriter file = new FileWriter("custom_template/" + path + ".json");
                file.write(this.vue.total.toJSONString());
                file.flush();
                file.close();

                int res = JOptionPane.showOptionDialog(this.vue, "Fichier enregistre !\nVoulez vous continuer ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { textHTML("Oui"), textHTML("Non") }, JOptionPane.YES_OPTION);

                if (res == JOptionPane.NO_OPTION) {
                    new Home();
                    this.vue.dispose();
                } 
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            JOptionPane.showMessageDialog(this.vue, "Un champ n'a pas ete complete (correctement).", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void refreshAlgoAction(int adj){
        if(this.vue.valideArray.contains(1) && this.vue.valideArray.contains(2) && this.vue.valideArray.contains(3) && this.vue.valideArray.contains(4)){
            try{
                this.vue.total.put("spawn", this.vue.spawn);
                this.vue.total.put("finish", this.vue.finish);
                this.vue.total.put("objective", this.vue.objective);
                this.vue.total.put("bloc", this.vue.blocArray);
                this.vue.total.put("nbBlocRepeat", this.vue.spinner.get(1).getValue());
                File file = new File("custom_template/tempAlgo.json");
                FileWriter filew = new FileWriter(file);
                filew.write(this.vue.total.toJSONString());
                filew.flush();
                filew.close();

                ArrayList<Scene> livre = this.vue.treeGen().loadTreeWithTemplate("custom_template/tempAlgo.json", this.vue.allSkill(), this.vue.allItem(), this.vue.allMonster());

                /*if(file.delete()) // Supprime le fichier tempAlgo.json (ne fonctionne pas)
                {  
                    System.out.println(file.getName() + " deleted");   //getting and printing the file name  
                }
                else{
                    System.out.println(file.getName() + " deletion failed");
                }*/

                if (adj==4){
                    refreshDiff(livre);
                    refreshShortestPath(livre);
                }
                if (adj==5){
                    refreshGraphViz(livre);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else {
            JOptionPane.showMessageDialog(this.vue, "Un champ n'a pas ete complete (correctement).", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void refreshDiff(ArrayList<Scene> livre){
        double res = 0;
        double resMonster = 0;
        double resPowMonster = 0;
        int val = 10;
        for (int i=0; i<val; i++){
            DFS parcoursLongueur = new DFS(livre);
            res+=parcoursLongueur.getDif();
            resMonster+=parcoursLongueur.getDifMonster();
            resPowMonster+=parcoursLongueur.getAveragePowerMonster();
        }
        res= 100*res/val;
        resMonster= 100*resMonster/val;
        res = Math.round(res*100.0)/100.0;
        resMonster = Math.round(resMonster*100.0)/100.0;
        resPowMonster = Math.round(resPowMonster*100.0)/100.0;

        String difStr = "-Difficulte du jeu :   "+ res + "%";
        String difMonsterStr = "-Difficulte des monstres :  "+ resMonster + "%";
        String PowMonsterStr = "-Difficulte moyenne des monstres :  "+ resPowMonster;
        this.vue.botJLabel.get(4).setText(difStr);
        this.vue.botJLabel.get(5).setText(difMonsterStr);
        this.vue.botJLabel.get(6).setText(PowMonsterStr);    
    }

    public void refreshShortestPath(ArrayList<Scene> livre){
        DFS parcoursLongueur = new DFS(livre);
        ArrayList<Scene> shortestPath = parcoursLongueur.getShortestPath();
        String nbSceneStr = "Nombre de scenes dans le chemin le plus court :  " + parcoursLongueur.getNbSceneInShortestPath();

        this.vue.botJLabel.get(7).setText(nbSceneStr); 

        this.vue.comboBox.get(1).removeAllItems();
        for (Scene s : shortestPath){
            String txt = s.getName();
            if (s.getInteraction().getType()=="battle"){
                txt+= " (battle)";
            }
            if (s == livre.get(0)){
                txt+= " (spawn)";
            }
            if (s == livre.get(1)){
                txt+= " (finish)";
            }
            this.vue.comboBox.get(1).addItem(txt);  
        } 
    }

    public void refreshGraphViz(ArrayList<Scene> livre){
        SimpleGraphView sgv = new SimpleGraphView(livre);
        Layout<Integer, String> layout = new FRLayout<>(sgv.graph);
        layout.setSize(new Dimension(900, 500));
        BasicVisualizationServer<Integer, String> vv = new BasicVisualizationServer<>(layout);        
        Transformer<Integer, String> vertexName = new Transformer<Integer, String>() {
            @Override 
            public String transform(Integer arg0){
                String res = "test failed";
                for (Scene scn : livre){
                    if (scn.getId() == arg0){
                        res = scn.getName();
                        if (scn == livre.get(0)){
                            res +=" (Spawn)";
                        }
                        if (scn == livre.get(1)){
                            res += " (Finish)";
                        }
                        if (scn.getInteraction().getType()!="text" && scn.getInteraction().getType()!="null" && scn.getInteraction().getType()!="final"){
                            res+=" ("+scn.getInteraction().getType()+")";
                        }
                    }
                }
                return res;
            }
        };

        Transformer<Integer, Paint> vertexPaint = new Transformer<Integer, Paint>() {
            @Override
            public Paint transform(Integer arg1) {
                Paint res = Color.BLUE;
                for (Scene scn : livre){
                    if (scn.getId() == arg1){
                        if (scn == livre.get(0)){
                            res = Color.YELLOW;
                        }
                        if (scn == livre.get(1)){
                            res = Color.GREEN;
                        }
                        if (scn.getInteraction().getType()=="gameOver"){
                            res = Color.RED;
                        }
                    }
                }
                return res;
            }
        
        };

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexLabelTransformer(vertexName);

        JScrollPane scroll = new JScrollPane(vv);
        scroll.setPreferredSize(new Dimension(1000, 500));

        JButton saveGraphToPdfButton = new JButton(textHTML("Enregistrer png"));
        saveGraphToPdfButton.addActionListener((e) -> saveGraphToPng(e, scroll));

        this.vue.panel.get(0).removeAll();
        this.vue.panel.get(0).add(saveGraphToPdfButton);
        this.vue.panel.get(0).add(scroll);
    }

    public void saveGraphToPng(ActionEvent event, JScrollPane scroll){ 
        scroll.setPreferredSize(new Dimension(2000, 1000));   
        BufferedImage bi = new BufferedImage(scroll.getSize().width, scroll.getSize().height, BufferedImage.TYPE_INT_ARGB); 
        Graphics g = bi.createGraphics();
        scroll.paint(g);
        g.dispose();
        String path = JOptionPane.showInputDialog(this.vue, "Entrez le nom du fichier");  
        try{ImageIO.write(bi,"png",new File(path+".png"));}catch (Exception e) {System.out.println("error");}
    }


    public void prevBlocAction(ActionEvent event){
        if(this.vue.idBloc != 0){
            this.vue.idBloc -= 1;
            ArrayList<JSONObject> bloc = (ArrayList)this.vue.tree.get("bloc");
            JSONObject nodeBloc = bloc.get(this.vue.idBloc);
            this.vue.updateBloc(nodeBloc);
        }
    }

    public void nextBlocAction(ActionEvent event){
        ArrayList<JSONObject> bloc = (ArrayList)this.vue.tree.get("bloc");
        if((this.vue.idBloc + 1) < bloc.size()){
            this.vue.idBloc += 1;
            JSONObject nodeBloc = bloc.get(this.vue.idBloc);
            this.vue.updateBloc(nodeBloc);
        }
    }

    public void stateChanged(ChangeEvent evt) {
        int indexAlgo = 4;
        int indexGraphviz = 5;
        if (this.vue.onglets.getSelectedIndex() == indexAlgo || this.vue.onglets.getSelectedIndex() == indexGraphviz){
            refreshAlgoAction(this.vue.onglets.getSelectedIndex());}
    }
}