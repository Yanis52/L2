package livre.graphics;

import livre.game.player.*;
import livre.game.scene.*;
import livre.game.*;    

import livre.graphics.controller.ControlGamePage;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import java.nio.charset.StandardCharsets;


/**
 * Classe permettant de jouer un livre avec une interface graphique
 * controller = controller de la classe GamePage
 * contentPAne = contentPane
 * skillBtn, directionBtn = bouttons permettants de gérer les skills et les directions du personnage dans le jeux
 * objectiveList, playerStats, interactionLabel =
 * tableList =
 * player=
 * interactionFinish=
 */


public class GamePage extends BasicPage{

    private ControlGamePage controller;

    private JPanel contentPane;

    public ArrayList<JButton> skillBtn, directionBtn;
    public ArrayList<JLabel> objectiveList, playerStats, interactionLabel;
    public ArrayList<DefaultTableModel> tableList;
    public Monster monster;
    
    public Player player;

    public boolean interactionFinish;
    
    /** 
    Initialise la fenêtre
    */
    public GamePage(Player player){
        super();
        this.controller = new ControlGamePage(this);
        this.player = player;

        this.player.loopItemInInventory();

        this.interactionFinish = true;

        this.contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(null);

        initArrayList();

        this.monster = null;

        // Place widgets
        initVue();
        updateVue();
        disableDirectionBtn();
        interactionDisplay();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    /**
     * permet d'initialiser toutes les listes
     */

    public void initArrayList(){
        this.skillBtn = new ArrayList<>();
        this.directionBtn = new ArrayList<>();

        this.objectiveList = new ArrayList<>();
        this.playerStats = new ArrayList<>();

        this.tableList = new ArrayList<>();

        this.interactionLabel = new ArrayList<>();
    }

    // ---------------------------------- //
    public void initVue(){
        skill();
        infoObjective();
        playerStats();
        returnBtn();
        directionBtn();
        inventory();
        interactionWidgets();
    }
    /**
     * permet d'ajouter et de positionner dans le contentPane les différents boutons de skills
     */
    public void skill(){
        JButton buttonSkill1 = new JButton("Skill 1");
        buttonSkill1.setBounds(865, 484, 200, 100);
        this.contentPane.add(buttonSkill1);
        this.skillBtn.add(buttonSkill1);

        JButton buttonSkill2 = new JButton("Skill 2");
        buttonSkill2.setBounds(1065, 484, 200, 100);
        this.contentPane.add(buttonSkill2);
        this.skillBtn.add(buttonSkill2);

        JButton buttonSkill3 = new JButton("Skill 3");
        buttonSkill3.setBounds(865, 584, 200, 100);
        this.contentPane.add(buttonSkill3);
        this.skillBtn.add(buttonSkill3);

        JButton buttonSkill4 = new JButton("Skill 4");
        buttonSkill4.setBounds(1065, 584, 200, 100);
        this.contentPane.add(buttonSkill4);
        this.skillBtn.add(buttonSkill4);
    }
    /**
     * permet d'ajouter et de positionner dans le content Pane les informations sur les objectifs d'or et d'item
     *      
     */
    public void infoObjective(){
        JLabel infoObjectiveGold = new JLabel(textHTML("Argent objectif : <br>" + this.player.getObjectiveGold()));
        infoObjectiveGold.setBounds(1100, 384, 200, 50);
        this.contentPane.add(infoObjectiveGold);
        this.objectiveList.add(infoObjectiveGold);
        
        JLabel infoObjectiveItem = new JLabel(textHTML("Item objectif : <br>" + this.player.getObjectiveItem().getName()));
        infoObjectiveItem.setBounds(1100, 300, 200, 50);
        this.contentPane.add(infoObjectiveItem);
        this.objectiveList.add(infoObjectiveItem);
    }
    /**
     * permet d'ajouter et de positionner dans le content pane des informations sur le joueur
     * notament les pts d'attaque / defense / nombre de pv / argent
     */
    public void playerStats(){
        JLabel infoAttack = new JLabel(textHTML("Attaque : " + this.player.getAttack()));
        infoAttack.setBounds(1100, 50, 200, 50);
        this.contentPane.add(infoAttack);
        this.playerStats.add(infoAttack);

        JLabel infoDefense = new JLabel(textHTML("Defense : " + this.player.getDefense()));
        infoDefense.setBounds(1100, 100, 200, 50);
        this.contentPane.add(infoDefense);
        this.playerStats.add(infoDefense);

        JLabel infoHealth = new JLabel(textHTML("Pv : " + this.player.getActualHealth() + "/" + this.player.getMaxHealth()));
        infoHealth.setBounds(1100, 150, 200, 50);
        this.contentPane.add(infoHealth);
        this.playerStats.add(infoHealth);

        JLabel infoMoney = new JLabel(textHTML("Argent : " + this.player.getGoldCount()));
        infoMoney.setBounds(1100, 200, 200, 50);
        this.contentPane.add(infoMoney);
        this.playerStats.add(infoMoney);
    }
    /**
     * permet d'ajouter à l'ui un boutton retour vers le main menu de lancement
     */
    public void returnBtn(){
        JButton ButtonRetour = new JButton("retour");
        ButtonRetour.setBounds(360, 50, 144, 75);
        ButtonRetour.addActionListener((e) -> this.controller.actionReturnButton(e));
        this.contentPane.add(ButtonRetour);
    }
    /**
     * peremt d'ajouter et de positioner dans le content pane les differents bouttons de directions que pourra prendre le joueur
     */
    public void directionBtn(){
        JButton Direction1 = new JButton("Direction 1");
        Direction1.setBounds(0, 533, 288, 150);
        this.contentPane.add(Direction1);
        this.directionBtn.add(Direction1);

        JButton Direction2 = new JButton("Direction 2");
        Direction2.setBounds(288, 533, 288, 150);
        this.contentPane.add(Direction2);
        this.directionBtn.add(Direction2);

        JButton Direction3 = new JButton("Direction 3");
        Direction3.setBounds(576, 533, 288, 150);
        this.contentPane.add(Direction3);
        this.directionBtn.add(Direction3);

        JButton Direction4 = new JButton("Sauvegarder Item");
        Direction4.setBounds(0, 0, 288, 150);
        this.contentPane.add(Direction4);
        this.directionBtn.add(Direction4);

        JButton Direction5 = new JButton("Direction 5/Quitter Magasin");
        Direction5.setBounds(576, 0, 288, 150);
        this.contentPane.add(Direction5);
        this.directionBtn.add(Direction5);
    }
    /**
     * permet d'ajouter à l'ui un tableau de l'inventaire
     */
    public void inventory(){
        DefaultTableModel table = new DefaultTableModel();
        tableList.add(table);
        
        table.addColumn("Item");
        table.addColumn("Attaque");
        table.addColumn("Defense");
        table.addColumn("PV");

        for(Item item : this.player.getInventory()){
            String row[] = {"" + item.getName() ,"" + item.getBonusAttack() ,"" + item.getBonusDefense() ,"" + item.getBonusMaxHealth()};
            table.addRow(row);
        }
        JTable inventory = new JTable(table);
        inventory.setEnabled(true);
        
        JScrollPane scrollpane = new JScrollPane(inventory);
        this.contentPane.add(scrollpane);
        scrollpane.setBounds(865, 2, 200, 464);
    }
    /**
     * permet d'afficher du texte à l'ecran selon les interactions du joueur
     */
    public void interactionWidgets(){
        JLabel topLabel = new JLabel("");
        topLabel.setBounds(160, 250, 600, 100);
        this.contentPane.add(topLabel);
        this.interactionLabel.add(topLabel);

        JLabel botLabel = new JLabel("");
        botLabel.setBounds(160, 350, 600, 100);
        this.contentPane.add(botLabel);
        this.interactionLabel.add(botLabel);
    }
    // ---------------------------------- //

    // ---------------------------------- //
    public void updateVue(){
        updatePlayerStats();
        updateSkillName();
        updateInventory();
        updateObjective();
    }

    public void updatePlayerStats(){
        this.player.loopItemInInventory();

        this.playerStats.get(0).setText(textHTML("Attaque : " + this.player.getAttack()));
        this.playerStats.get(1).setText(textHTML("Defense : " + this.player.getDefense()));
        this.playerStats.get(2).setText(textHTML("Pv : " + this.player.getActualHealth() + "/" + this.player.getMaxHealth()));
        this.playerStats.get(3).setText(textHTML("Argent : " + this.player.getGoldCount()));
    }

    public void updateAtkDef(){
        this.playerStats.get(0).setText(textHTML("Attaque : " + this.player.getAttack()));
        this.playerStats.get(1).setText(textHTML("Defense : " + this.player.getDefense()));
    }

    public void updateHP(){
        this.playerStats.get(2).setText(textHTML("Pv : " + this.player.getActualHealth() + "/" + this.player.getMaxHealth()));
    }

    public void updateGold(){
        this.playerStats.get(3).setText(textHTML("Argent : " + this.player.getGoldCount()));
    }

    public void updateSkillName(){
        int c = 0;
        for(Skill s : this.player.getSkills()){
            this.skillBtn.get(c).setText(textHTML(s.getName()));
            c ++;
        }

        for(int i = 3; i > c; i--){
            this.skillBtn.get(i).setText("");
            this.skillBtn.get(i).setEnabled(false);
        }
    }

    public void updateDirection(){
        // Reset unabled btn
        for(JButton btn : directionBtn){
            btn.setEnabled(true);
            btn.setText("");
        }

        resetActionListener();

        Scene actualScene = this.player.getTree().get(this.player.getActualSceneId());

        ArrayList<Scene> nextScene = new ArrayList<>();
        for(int id : actualScene.getNextScene()){
            for(Scene scene : this.player.getTree()){
                if(scene.getId() == id){
                    nextScene.add(scene);
                }
            }
        }

        int c = 0;
        for(Scene s : nextScene){
            this.directionBtn.get(c).setText(textHTML(s.getName() + " (" + s.getId() + ")"));

            if(c == 0){
                this.directionBtn.get(c).addActionListener((e) -> this.controller.actionDirection1Button(e));
            }
            else if(c == 1){
                this.directionBtn.get(c).addActionListener((e) -> this.controller.actionDirection2Button(e));
            }
            else if(c == 2){
                this.directionBtn.get(c).addActionListener((e) -> this.controller.actionDirection3Button(e));    
            }
            else if(c == 3){
                this.directionBtn.get(c).addActionListener((e) -> this.controller.actionDirection4Button(e));    
            }
            else if(c == 4){
                this.directionBtn.get(c).addActionListener((e) -> this.controller.actionDirection5Button(e));                    
            }

            c ++;   
        }

        for(int i = 4; i > c-1; i--){
            this.directionBtn.get(i).setText("");
            this.directionBtn.get(i).setEnabled(false);
        }
    }

    public void updateInventory(){
        while(this.tableList.get(0).getRowCount() > 0){
            this.tableList.get(0).removeRow(0);
        }

        for(Item item : this.player.getInventory()){
            String row[] = {"" + new String(item.getName().getBytes(), StandardCharsets.UTF_8) , "" + item.getBonusAttack() ,"" + item.getBonusDefense() ,"" + item.getBonusMaxHealth()};
            this.tableList.get(0).addRow(row);
        }
    }

    public void updateObjective(){
        this.objectiveList.get(0).setText(textHTML("Argent objectif : <br>" + this.player.getGoldCount() + "/" + this.player.getObjectiveGold()));
        this.objectiveList.get(1).setText(textHTML("Item objectif : <br>" + this.player.getObjectiveItem().getName()));
    }
    /**
     * peremt de controller l'interaction avec l'utilisateur avec du texte
     */
    public void interactionDisplay(){
        Scene actualScene = this.player.getTree().get(this.player.getActualSceneId());

        if(actualScene.getInteraction().getType().equals("text")){
            Text interaction = (Text)actualScene.getInteraction();

            directionBtn.get(0).removeActionListener((e) -> this.controller.actionDirection1Button(e));

            directionBtn.get(0).setEnabled(true);
            directionBtn.get(0).setText(textHTML("Suivant"));
            directionBtn.get(0).addActionListener((e) -> this.controller.nextTextBtn(e));

            this.interactionLabel.get(0).setText(textHTML(interaction.getTextInOrder()));
        }
        else if(actualScene.getInteraction().getType().equals("gameOver")){
            JOptionPane.showMessageDialog(this, textHTML("Vous etes tombes dans un piege mortel ..."), "Game Over", JOptionPane.ERROR_MESSAGE);
            dispose();
            new Home();
        }
        else if(actualScene.getInteraction().getType().equals("final")){
            this.player.checkGotEnoughGold();
            this.player.loopItemInInventory();
            if(this.player.getHasGotEnoughGold() && this.player.getHasGotItem()){
                // Victoire
                JOptionPane.showMessageDialog(this, textHTML("Vous avez mene votre quete a bien ! Felicitation !"), "Fini !", JOptionPane.INFORMATION_MESSAGE);  
            }
            else{
                // Défaite
                JOptionPane.showMessageDialog(this, textHTML("Vous n'avez pas reussi a rassembler tout ce qui vous avez ete demande"), "Fini !", JOptionPane.INFORMATION_MESSAGE);  
            }

            dispose();
            new Home();
        }
        else if(actualScene.getInteraction().getType().equals("battle")){

            Battle battle = (Battle)actualScene.getInteraction();
            this.monster = battle.getMonster();

            resetActionListener();
            interactionLabel.get(0).setText(textHTML("Vous venez de rencontrez le monster " + this.monster.getName() + " !"));
            interactionLabel.get(1).setText(textHTML("Quel compétence voulez vous utiliser ?"));

            for(int i = 0; i < 4; i++){
                JButton btn = this.skillBtn.get(i);
                if(btn.isEnabled()){
                    if(i == 0){
                        btn.addActionListener((e) -> this.controller.actionSkill1Button(e));
                    }
                    else if(i == 1){
                        btn.addActionListener((e) -> this.controller.actionSkill2Button(e));
                    }
                    else if(i == 2){
                        btn.addActionListener((e) -> this.controller.actionSkill3Button(e));
                    }
                    else if(i == 3){
                        btn.addActionListener((e) -> this.controller.actionSkill4Button(e));
                    }
                }
            }


        }
        else if(actualScene.getInteraction().getType().equals("shop")){
            directionBtn.get(4).setEnabled(true);
            directionBtn.get(4).setText(textHTML("Quitter le magasin"));
            directionBtn.get(4).addActionListener((e) -> this.controller.exitShop(e));

            interactionLabel.get(0).setText(textHTML("Vous etes entre dans un magasin"));
            interactionLabel.get(1).setText(textHTML("Que voulez vous acheter ?"));

            // Display all item and put price
            Shop shop = (Shop)actualScene.getInteraction();
            
            int c = 0;
            for(Item i : shop.getSellable()){
                directionBtn.get(c).setText(textHTML(i.getName() + " | " + i.getBonusAttack() + " . " + i.getBonusDefense() + " . " + i.getBonusMaxHealth() + " | Prix : (" + i.getCost() + ")"));
                directionBtn.get(c).setEnabled(true);
                
                try{
                    directionBtn.get(c).removeActionListener(directionBtn.get(c).getActionListeners()[0]);
                }
                catch(Exception e){

                }

                directionBtn.get(c).addActionListener((e) -> this.controller.buyItem(e));
                
                c ++;
            }
        }
        else if(actualScene.getInteraction().getType().equals("temple")){
            this.player.recoveryMaxHealth();
            JOptionPane.showMessageDialog(this, textHTML("Vous venez d'entrer dans un temple. Vous regagnez tous vos points de vie !"), "Temple", JOptionPane.INFORMATION_MESSAGE);  
            interactionLabel.get(0).setText(textHTML("Ou voulez vous aller ?"));
            updateDirection();
            updateHP();
        }
        else if(actualScene.getInteraction().getType().equals("treasure")){
            
            boolean flag = false;
            for(Item i : this.player.getInventory()){
                if(i.getAlias().equals("key")){
                    flag = true;
                }
            }

            if(flag){
                int result = JOptionPane.showConfirmDialog(this, "Vous avez trouve un coffre, voulez vous l'ouvrir ?",
                    "Coffre",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

                if(result == JOptionPane.YES_OPTION){
                    Treasure inter = (Treasure)actualScene.getInteraction();
                    Item item = inter.getTreasureItem();

                    JOptionPane.showMessageDialog(this, textHTML("Dans le coffre il y a  " + new String(item.getName().getBytes(), StandardCharsets.UTF_8)), "Coffre", JOptionPane.INFORMATION_MESSAGE);  
                    this.player.addItem(item);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, textHTML("Vous avez trouve un coffre, mais malheuresement, vous n'avez pas de clef, et donc vous ne pouvez pas l'ouvrir"), "Coffre", JOptionPane.INFORMATION_MESSAGE);  
            }

            interactionLabel.get(0).setText(textHTML("Ou voulez vous aller ?"));

            updateInventory();
            updatePlayerStats();
            updateDirection();
        }
        else if(actualScene.getInteraction().getType().equals("foundItem")){
            FoundItem inter = (FoundItem)actualScene.getInteraction();
            Item item = inter.getFoundItem();

            JOptionPane.showMessageDialog(this, textHTML("Vous venez de trouver un objet par terre. " + new String(item.getName().getBytes(), StandardCharsets.UTF_8) + " s'ajoute a votre inventaire."), "Objet", JOptionPane.INFORMATION_MESSAGE);  
            interactionLabel.get(0).setText(textHTML("Ou voulez vous aller ?"));
            
            this.player.addItem(item);

            updateInventory();
            updatePlayerStats();
            updateDirection();
        }
    }
    /**
     * permet de desactiver des bouttons
     */
    public void disableDirectionBtn(){
        for(JButton btn : directionBtn){
            btn.setEnabled(false);
            btn.setText("");
        }
    }

    public void resetActionListener(){
        // Reset all actionListener
        for(JButton btn : this.directionBtn){
            try{
                btn.removeActionListener(btn.getActionListeners()[0]);
            }
            catch(Exception e){

            }
        }

        for(JButton btn : this.skillBtn){
            try{
                btn.removeActionListener(btn.getActionListeners()[0]);
            }
            catch(Exception e){

            }
        }
    }
    // ---------------------------------- //
}