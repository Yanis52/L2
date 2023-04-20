package livre.graphics.controller;

import livre.game.player.Player;
import livre.graphics.GamePage;
import livre.graphics.Home;

import livre.game.loader.*;
import livre.game.scene.*;
import livre.game.player.*;

import java.awt.event.*;

import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class ControlGamePage {

    private GamePage vue;
    private Player player;

    public ControlGamePage(GamePage vue){
        this.vue = vue;
    }

    //Methods
    public void launchSkill(int idSkill){
        Skill skill = this.vue.player.getSkills().get(idSkill);
        
        int damageByPlayer = skill.useSkill();
        int damageByMonster = this.vue.monster.useSkill();

        if(damageByPlayer == 0){
            this.vue.interactionLabel.get(0).setText(this.vue.textHTML("Vous avez rate votre attaque."));
        }
        else{
            this.vue.interactionLabel.get(0).setText(this.vue.textHTML("Vous avez inflige " + damageByPlayer));

        }

        if(damageByMonster == 0){
            this.vue.interactionLabel.get(1).setText(this.vue.textHTML(this.vue.monster.getName() + " a rate son attaque." + ". Quelle competences voulez vous lancer ?"));
        }
        else{
            this.vue.interactionLabel.get(1).setText(this.vue.textHTML(this.vue.monster.getName() + " vous a inflige " + damageByMonster + ". Quelle competences voulez vous lancer ?"));

        }    

        this.vue.monster.beAttack(damageByPlayer);
        this.vue.player.beAttack(damageByMonster);

        this.vue.updateHP();

        if(!this.vue.player.getIsAlive()){
            JOptionPane.showMessageDialog(this.vue, this.vue.textHTML("Vous avez ete vaincu ..."), "Game Over", JOptionPane.ERROR_MESSAGE);
            this.vue.dispose();
            new Home();
        }

        else if(!this.vue.monster.getIsAlive()){
            JOptionPane.showMessageDialog(this.vue, this.vue.textHTML("Vous avez vaincu le monstre !"), "Bien joue !", JOptionPane.INFORMATION_MESSAGE);
            this.vue.resetActionListener();
            this.vue.updateDirection();
        }  
    }

    public void actionSkill1Button(ActionEvent event){
        int idSkill = 0;
        launchSkill(idSkill);

    }

    public void actionSkill2Button(ActionEvent event){
        int idSkill = 1;
        launchSkill(idSkill);
    }

    public void actionSkill3Button(ActionEvent event){
        int idSkill = 2;
        launchSkill(idSkill);
    }

    public void actionSkill4Button(ActionEvent event){
        int idSkill = 3;
        launchSkill(idSkill);
    }

    public void directionChange(JButton btn){
        Document document = Jsoup.parse(btn.getText());
        String directionName = document.select("h3").text();

        int idScene = Integer.valueOf(directionName.substring(
            directionName.indexOf("(") + 1, 
            directionName.lastIndexOf(")")));

        this.vue.player.setActualIdScene(idScene);
        this.vue.disableDirectionBtn();

        btn.removeActionListener(btn.getActionListeners()[0]);

        this.vue.interactionDisplay();
    }

    public void actionDirection1Button(ActionEvent event){
        JButton btn = this.vue.directionBtn.get(0);
        directionChange(btn);
    }

    public void actionDirection2Button(ActionEvent event){
        JButton btn = this.vue.directionBtn.get(1);
        directionChange(btn);
    }

    public void actionDirection3Button(ActionEvent event){
        JButton btn = this.vue.directionBtn.get(2);
        directionChange(btn);
    }

    public void actionDirection4Button(ActionEvent event){
        JButton btn = this.vue.directionBtn.get(3);
        directionChange(btn);
    }

    public void actionDirection5Button(ActionEvent event){
        JButton btn = this.vue.directionBtn.get(4);
        directionChange(btn);
    }

    public void actionReturnButton(ActionEvent event){
        int res = JOptionPane.showOptionDialog(
            this.vue, 
            "Que voulez faire",
            "Confirmation", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            new Object[] {this.vue.textHTML("Quitter"), this.vue.textHTML("Continuer"), this.vue.textHTML("Sauvegarder et continuer"), this.vue.textHTML("Sauvegarder et quitter")}, 
            JOptionPane.YES_OPTION
        );
        if(res == 0){
            this.vue.dispose();
            new Home();
        }
        else if(res == 1){
            // pass
        }
        else if(res == 2){
            Save s = new Save();
            s.exportSave(this.vue.player);
            JOptionPane.showMessageDialog(this.vue, "Partie sauvegarde");  
        }
        else if(res == 3){
            Save s = new Save();
            s.exportSave(this.vue.player);
            JOptionPane.showMessageDialog(this.vue, "Vous allez quitter la partie");  
            this.vue.dispose();
            new Home();
        }
    }

    public void nextTextBtn(ActionEvent event){
        Scene actualScene = this.vue.player.getTree().get(this.vue.player.getActualSceneId());
        Text interaction = (Text)actualScene.getInteraction();

        String txt = interaction.getTextInOrder();
        if(txt.equals("")){
            this.vue.interactionLabel.get(0).setText(this.vue.textHTML("Ou voulez vous aller ?"));
            this.vue.directionBtn.get(0).removeActionListener(this.vue.directionBtn.get(0).getActionListeners()[0]);
            this.vue.updateDirection();
        }
        else{
            this.vue.interactionLabel.get(0).setText(txt);
        }
    }

    // Shop Methods
    public void exitShop(ActionEvent event){
        int result = JOptionPane.showConfirmDialog(this.vue, "Voulez vous vraiment quitter le magasin ?",
                    "Quitter le magasin",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

        if(result == JOptionPane.YES_OPTION){
            // Reset all actionListener
            for(JButton btn : this.vue.directionBtn){
                try{
                    btn.removeActionListener(btn.getActionListeners()[0]);
                }
                catch(Exception e){

                }
            }

            this.vue.interactionLabel.get(1).setText("");
            
            this.vue.interactionLabel.get(0).setText(this.vue.textHTML("Ou voulez vous aller ?"));
            this.vue.updateDirection();
        }
    }

    public void buyItem(ActionEvent event){
        JButton btn = (JButton)event.getSource();

        Document document = Jsoup.parse(btn.getText());
        String directionName = document.select("h3").text();

        int price = Integer.valueOf(directionName.substring(
            directionName.indexOf("(") + 1, 
            directionName.lastIndexOf(")")));

        String name = directionName.substring(
            0, 
            directionName.indexOf(" |"));

        Item item = this.vue.allItem().getWithName(name);

        if(this.vue.player.getGoldCount() >= price){
            JOptionPane.showMessageDialog(this.vue, this.vue.textHTML("Vous venez d'acheter un objet . " + item.getName() + " s'ajoute a votre inventaire."), "Objet", JOptionPane.INFORMATION_MESSAGE); 
            this.vue.player.removeGold(price);
            this.vue.player.addItem(item);

            this.vue.updatePlayerStats();
            this.vue.updateInventory();
        }
    }



}
