package livre.graphics.controller;

import livre.graphics.*;
import livre.graphics.Home;
import livre.game.loader.*;
import java.awt.event.*;
import javax.swing.*;



public class ControlHome extends JFrame{
    private Home graphics;

    public ControlHome(Home graphics){
        this.graphics = graphics;
    }


    //Methods 
    public void actionNewGameButton(ActionEvent event){
        new TreeChoice();
        this.graphics.dispose();
    }
    public void actionLoadGameButton(ActionEvent event){
        setVisible(false);
        Save s = new Save();
        new GamePage(s.importSave(this.graphics.allSkill, this.graphics.allItem, this.graphics.allMonster));
        this.graphics.dispose();
    }

    public void actionTemplateButton(ActionEvent event){
        new TemplateChoice();
        this.graphics.dispose();

    }

    public void actionExitButton(ActionEvent event){
        System.exit(0);
        this.graphics.dispose();
    }


}