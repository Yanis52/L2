package livre.graphics.controller;
import livre.graphics.*;
import livre.graphics.Home;
import livre.game.scene.*;
import livre.graphics.GamePage;
import livre.game.player.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;




public class ControlTreeChoice {

    private TreeChoice vue;

    public ControlTreeChoice(TreeChoice vue){
            this.vue = vue;
    }
   
    //METHODS
    public void actionBasicTreeButton(ActionEvent event){
        ArrayList<Scene> basicTree = this.vue.treeGen().loadBasicTree(this.vue.allSkill(), this.vue.allItem(), this.vue.allMonster());
        new GamePage(new Player(this.vue.allItem().getRandomItem(), 50, basicTree, this.vue.allSkill()));
        this.vue.dispose();
    }

    public void actionPersoTreeButton(ActionEvent event){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON Template game doc", "json"));

        int option = fileChooser.showOpenDialog(this.vue);

        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            
            ArrayList<Scene> tree = this.vue.treeGen().loadTreeWithTemplate(file.getAbsolutePath(),this.vue.allSkill(), this.vue.allItem(), this.vue.allMonster());
            if(tree.size() == 0){
                // Show dialogue windows, is not tree
                JOptionPane.showMessageDialog(this.vue, "Le fichier n'est pas en template de jeu.", "Alert", JOptionPane.WARNING_MESSAGE);
            }
            else {
                new GamePage(new Player(this.vue.allItem().getRandomItem(), 50, tree, this.vue.allSkill()));
                this.vue.dispose();
            }
        }
    }

    public void actionReturnButton(ActionEvent event){
            new Home();
            this.vue.dispose();
        }
    
   
   

    
}
