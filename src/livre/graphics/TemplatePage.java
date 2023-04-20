package livre.graphics;

import livre.game.player.*;

import livre.graphics.controller.*;
import java.util.*;
import org.json.simple.*;
import javax.swing.*;
import java.awt.*;


public class TemplatePage extends BasicPage{
    // Controller
    public ControlTemplatePage controller;

    // JSON
    public JSONObject spawn, finish, bloc, objective, blocNode, total;
    public ArrayList<JSONObject> allJson;

    public ArrayList<JSONObject> blocArray = new ArrayList<>();

    // Bot bar
    public ArrayList<String> botBar = new ArrayList<>(Arrays.asList(
        "Spawn", "Finish", "Objective", "Bloc"
    ));
    public ArrayList<JLabel> botJLabel = new ArrayList<>();

    // List WIDGET
    public ArrayList<JTextField> textField;
    public ArrayList<JTextArea> textArea;
    public ArrayList<JButton> buttons;
    public ArrayList<JSpinner> spinner;
    public ArrayList<JComboBox> comboBox;
    public ArrayList<ArrayList<JCheckBox>> checkBox; 
    public ArrayList<JPanel> panel;
    public ArrayList<JScrollPane> scroll;

    // List Interaction
    public ArrayList<String> interaction = new ArrayList<>(Arrays.asList(
        "text", "battle", "gameOver", "shop", "temple", "treasure"
    ));

    // Tableau de validation des champs

    // Tout les champs
    public ArrayList<Integer> valideArray = new ArrayList<>();

    // Bloc node
    public ArrayList<Integer> valideBlocNodeArray = new ArrayList<>();

    // Main onglet
    public JTabbedPane onglets;

    // Template Modify Variables
    public int idBloc = 0;
    public JSONObject tree;

    public TemplatePage(){
        super();

        this.controller = new ControlTemplatePage(this);

        // Init - JSON & Widget
        initListWidget();

        // this.allJson = new ArrayList<>(Arrays.asList(
        //     spawn, finish, bloc, objective, blocNode, total
        // ));
        // initListJson();

        // Obligation de les déclarer comme ça pour pouvoir y accéder depuis le controller
        this.spawn = new JSONObject();
        this.finish = new JSONObject();
        this.bloc = new JSONObject();
        this.objective = new JSONObject();
        this.blocNode = new JSONObject();
        this.total = new JSONObject();

        // Onglet - Spawn / Finish
        this.onglets = new JTabbedPane();

        this.onglets.add(textHTML("Spawn"), uniqueNodePage("spawn"));
        this.onglets.add(textHTML("Finish"), uniqueNodePage("finish"));

        // Onglet - Objective
        this.onglets.add(textHTML("Objective"), objectiveMethod());

        // Onglet - Ajout
        this.onglets.setPreferredSize(new Dimension(1050, 0));

        // this.contentPane.add(this.onglets, BorderLayout.WEST);
        
        // Onglet - Detection changement d'onglet
        onglets.addChangeListener((e) -> this.controller.stateChanged(e));

        // Info Side bar
        JPanel infoSide = infoSideBar();
        infoSide.setPreferredSize(new Dimension(210, 0));
        this.contentPane.add(infoSide, BorderLayout.EAST);

        // BotBar
        JPanel bot = botBarMethod();
        this.contentPane.add(bot, BorderLayout.SOUTH);

        // setVisible(true);
    }

    // Init Method -------------

    public void initListJson(){
        /** 
        Init tout les JSON nécessaires a l'enregistrement des données
        */
        for(JSONObject json : this.allJson){
            json = new JSONObject();
        }
    }

    public void initListWidget(){
        /** 
        Initialise toutes les listes contenants les widgets de la vue
        */
        buttons = new ArrayList<>();
        textField = new ArrayList<>();
        textArea = new ArrayList<>();
        spinner = new ArrayList<>();
        comboBox = new ArrayList<>();
        checkBox = new ArrayList<>();
        panel = new ArrayList<>();
        scroll = new ArrayList<>();
    }

    public GridBagConstraints initPositionTool(){
        /** 
        Initialise un GridBagConstraints avec les settings de base et le retourne
        Pour permettre le positionnement des widgets de manière plus propre
        */
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.ipady = 20;
        gbc.ipadx = 20;
        gbc.fill = GridBagConstraints.HORIZONTAL;  

        gbc.gridx = 0;  
        gbc.gridy = 0; 

        return gbc;
    }

    // Spawn / Finish page -------------

    public JPanel uniqueNodePage(String description){
        /** 
        Méthode permettant de créer une page de noeud simple.
        @param description
        l'argument description permet de savoir de quel page nous parlons et donc l'adpater
        Spawn - Finish ...
        */
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        
        JTextField name = new JTextField(20);
        textField.add(name);

        JTextArea text = new JTextArea();
        textArea.add(text);

        JButton btn = new JButton(textHTML("Valider"));
        buttons.add(btn);
        
        // Plug controller here
        if(description.equals("spawn")){
            btn.addActionListener((e) -> this.controller.valideSpawnAction(e));
        }
        else if(description.equals("finish")){
            btn.addActionListener((e) -> this.controller.valideFinishAction(e));
        }
        else if(description.equals("spawn-bloc")){
            btn.addActionListener((e) -> this.controller.spawnBlocAction(e));
        }
        else if(description.equals("finish-bloc")){
            btn.addActionListener((e) -> this.controller.finishBlocButton(e));
        }

        ArrayList<Object> widgets = new ArrayList<>(Arrays.asList(
            new JLabel(textHTML("Nom de la scene")),
            name,
            new JLabel(textHTML("interaction : text\nSeparer chaque ligne de dialogue par un `;`")),
            text
        ));

        int count = 0;
        GridBagConstraints gbc = initPositionTool();

        for(int i = 0; i < 2; i ++){
            for(int j = 0; j < 2; j ++){
                
                if(count == widgets.size()){
                    break;
                }

                gbc.gridx = j;
                gbc.gridy = i;
                gbc.gridwidth = 1; 

                if(count == 0 || count == 2){
                    pane.add((JLabel)widgets.get(count), gbc);
                }
                else if(count == 1){
                    pane.add((JTextField)widgets.get(count), gbc);
                }
                else if(count == 3){
                    pane.add((JTextArea)widgets.get(count), gbc);
                }               

                count ++;
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        pane.add(btn, gbc);

        return pane;
    }
    /**
     * l'onglet qui permet de recuperer la saisie de l'item et des golds objectifs
     * @return
     */
    public JPanel objectiveMethod(){

        JPanel pane = new JPanel();

        pane.setLayout(new GridBagLayout());  

        SpinnerModel valueGold = new SpinnerNumberModel(1, 1, 999, 1);
        JSpinner countGold = new JSpinner(valueGold);
        spinner.add(countGold);

        JComboBox objectiveItemSelec = new JComboBox<>();
        comboBox.add(objectiveItemSelec);

        ArrayList<Item> allItemArray = super.allItem.getAllItem();
        objectiveItemSelec.addItem("random");
        for(int i = 1; i < allItemArray.size() - 1; i++){
            objectiveItemSelec.addItem(allItemArray.get(i).getAlias());
        }

        JButton valideItemGold = new JButton(textHTML("Valider"));
        buttons.add(valideItemGold);
        valideItemGold.addActionListener((e) -> this.controller.valideObjectiveAction(e));

        ArrayList<Object> widgets = new ArrayList<>(Arrays.asList(
            new JLabel(textHTML("Nombre de gold : ")),
            new JLabel(textHTML("Item : ")),
            countGold,
            objectiveItemSelec
        ));

        int count = 0;
        GridBagConstraints gbc = initPositionTool();

        for(int i = 0; i < 2; i ++){
            for(int j = 0; j < 2; j ++){

                if(count == widgets.size()){
                    break;
                }

                gbc.gridx = i;
                gbc.gridy = j;
                gbc.gridwidth = 1;               

                if(count == 0 || count == 1){
                    pane.add((JLabel)widgets.get(count), gbc);
                }
                else if(count == 2){
                    pane.add((JSpinner)widgets.get(count), gbc);
                }
                else if(count == 3){
                    pane.add((JComboBox)widgets.get(count), gbc);
                }

                count ++;
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 2;
        pane.add(valideItemGold, gbc);

        return pane;
    }
    /**
     * permet d'afficher la méthode de l'algo pour tout template 
     * @return
     */
    public JTabbedPane algoMethod(){
        JTabbedPane algoOnglet = new JTabbedPane();

        // -------------- Difficulté --------------
        JPanel difBloc = new JPanel();
        difBloc.setLayout(new GridBagLayout());

        GridBagConstraints gbc = initPositionTool();
        gbc.gridwidth = 1; 


        JLabel textPres = new JLabel("Calculs sur 10 generations de livre a partir du template");
        gbc.gridx = 0;
        gbc.gridy = 0;
        difBloc.add(textPres, gbc);

        JLabel textDif = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 1;
        difBloc.add(textDif, gbc);

        JLabel textDifMonster = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 2;
        difBloc.add(textDifMonster, gbc);
        
        JLabel textPowMonster = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 3;
        difBloc.add(textPowMonster, gbc);

        algoOnglet.add(textHTML("Difficulte"), difBloc);
        
        botJLabel.add(textDif);
        botJLabel.add(textDifMonster);
        botJLabel.add(textPowMonster);
        
        // -------------- Chemin --------------        
        JPanel ShortestPathBloc = new JPanel();
        ShortestPathBloc.setLayout(new GridBagLayout());
    
        JLabel textPresSP = new JLabel("Sur une generation de livre a partir du template");
        gbc.gridx = 0;
        gbc.gridy = 0;
        ShortestPathBloc.add(textPresSP, gbc);

        JLabel textNbScene = new JLabel("- test -");
        gbc.gridx = 0;
        gbc.gridy = 1;
        ShortestPathBloc.add(textNbScene, gbc);
        
        JComboBox nbScenebox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        ShortestPathBloc.add(nbScenebox, gbc);

        botJLabel.add(textNbScene);
        comboBox.add(nbScenebox);

        algoOnglet.add(textHTML("Chemins"), ShortestPathBloc);

        return algoOnglet;
    }
    /**
     * ajout du panel de visualisation de l'arbre
     * @return
     */
    public JPanel graphVizMethod(){
        JPanel pane = new JPanel();
        panel.add(pane);
        return pane;
    }

    // Bloc Page Methods ------------
    /**
     * permet de creer les criteres du template ( spawn , finish...)
     * @return
     */
    public JTabbedPane blocCreatorMethod(){
        JTabbedPane tab = new JTabbedPane();

        tab.add(textHTML("Repetion de chaque blocs"), blocRepeat());
        tab.add(textHTML("Spawn"), uniqueNodePage("spawn-bloc"));
        tab.add(textHTML("Finish"), uniqueNodePage("finish-bloc"));
        tab.add(textHTML("Contenu du bloc"), blocInteraction());
        tab.add(textHTML("Valider le bloc"), blocValidation());

        return tab;
    }
    /**
     * permet de modifier les criteres du template ( spawn , finish...)
     * @return
     */
    public JTabbedPane blocModifyMethod(){
        JTabbedPane tab = new JTabbedPane();

        tab.add(textHTML("Repetion de chaque blocs"), blocRepeat());
        tab.add(textHTML("Spawn"), uniqueNodePage("spawn-bloc"));
        tab.add(textHTML("Finish"), uniqueNodePage("finish-bloc"));
        tab.add(textHTML("Contenu du bloc"), blocInteraction());
        tab.add(textHTML("Changer et valider le(s) bloc"), blocChange());

        return tab;
    }
    /**
     * permet de choisir le nombre de répétition de bloc souhaiter
     * @return
     */
    public JPanel blocRepeat(){
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());

        SpinnerModel value = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner spinnerBloc = new JSpinner(value);
        spinner.add(spinnerBloc);

        JButton btn = new JButton(textHTML("Valider"));
        buttons.add(btn);

        ArrayList<Object> widgets = new ArrayList<>(Arrays.asList(
            new JLabel(textHTML("Nombre de repetion de bloc : ")),
            spinnerBloc,
            btn
        ));

        GridBagConstraints gbc = initPositionTool();
        gbc.gridx = 0;

        for(int i = 0; i < widgets.size(); i ++){
            gbc.gridy = i;

            if(i == 0){
                pane.add((JLabel)widgets.get(i), gbc);
            }
            else if(i == 1){
                pane.add((JSpinner)widgets.get(i), gbc);
            }
            else if(i == 2){
                pane.add((JButton)widgets.get(i), gbc);
            }
        }

        return pane;
    }
    /**
     * permet de gerer les interactions avec le bloc ( le valider ...)
     * @return
     */
    public JPanel blocInteraction(){
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());

        SpinnerModel value = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner spinnerInteraction = new JSpinner(value);
        spinner.add(spinnerInteraction);

        JTextArea textAreaInteraction = new JTextArea();
        textArea.add(textAreaInteraction);

        ArrayList<JCheckBox> box = new ArrayList<>();
        for(String item : interaction){
            box.add(new JCheckBox(item));
        }
        checkBox.add(box);

        JButton btn = new JButton(textHTML("Valider")); 
        buttons.add(btn);
        btn.addActionListener((e) -> this.controller.blocInteractionAction(e));

        ArrayList<Object> widgets = new ArrayList<>(Arrays.asList(
            new JLabel(textHTML("Nombre de repetion de bloc : ")),
            spinnerInteraction,
            new JLabel(textHTML("Chaque scene devrat etre sous la forme `nom_scene:alias_scene;`, l'alias peut ne pas etre rentre")),
            textAreaInteraction,
            new JLabel(textHTML("Cochez les interactions qui pourront apparaitre sur chaque scene")),
            box,
            btn
        ));

        GridBagConstraints gbc = initPositionTool();
        gbc.gridx = 0;
        gbc.gridwidth = 3;

        for(int i = 0; i < widgets.size(); i ++){
            gbc.gridy = i;

            if(i == 0 || i == 2 || i == 4){
                pane.add((JLabel)widgets.get(i), gbc);
            }
            else if(i == 1){
                pane.add((JSpinner)widgets.get(i), gbc);
            }
            else if(i == 3){
                pane.add((JTextArea)widgets.get(i), gbc);
            }
            else if(i == 5){
                JPanel checkedPane = new JPanel();
                checkedPane.setLayout(new GridBagLayout());
                
                pane.add(checkedPane, gbc);

                int count = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;

                for(int j = 0; j < Math.round(box.size() / 2); j ++ ){
                    for(int k = 0; k < 2; k ++){
                        gbc.gridx = j;
                        gbc.gridy = k;
                        checkedPane.add(box.get(count), gbc);

                        count ++;
                    }
                }
            }

            else if(i == 6){
                pane.add((JButton)widgets.get(i), gbc);
            }
        }

        return pane;
    }
    /**
     * permet de valider le bloc
     * @return
     */
    public JPanel blocValidation(){
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());

        JButton btn = new JButton("Valider le bloc");
        buttons.add(btn);
        btn.addActionListener((e) -> this.controller.valideBlocAction(e));

        pane.add(btn);

        return pane;
    }
    /**
     * permet de repatir au blox précedent
     * @return
     */
    public JPanel blocChange(){
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());

        JButton prevBloc = new JButton("Bloc precedent");
        prevBloc.addActionListener((e) -> this.controller.prevBlocAction(e));
        pane.add(prevBloc);
        buttons.add(prevBloc);

        JButton valideBlocBtn = new JButton("Valider le bloc");
        valideBlocBtn.addActionListener((e) -> this.controller.valideBlocAction(e));
        pane.add(valideBlocBtn);
        buttons.add(valideBlocBtn);

        JButton nextBloc = new JButton("Bloc suivant");
        nextBloc.addActionListener((e) -> this.controller.nextBlocAction(e));
        pane.add(nextBloc);
        buttons.add(nextBloc);

        return pane;
    }
    /**
     * permet de visualiser la documentation pour pouvoir creer parfaitement sans difficulter un template
     * @return
     */
    public JPanel infoSideBar(){
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());

        JLabel infoTitle = new JLabel("<html>" + this.css + "<h1>Info sur le createur de template de livre</h1><br><h3> Si vous n'etes pas familier avec la maniere dont sont structures les livres, vous pouvez jeter un coup d'oeil a la documentation !</h3>");
        infoTitle.setPreferredSize(new Dimension(200, 400));

        JButton doc = new JButton(textHTML("Documentation"));
        buttons.add(doc);
        doc.addActionListener((e) -> this.controller.showDocAction(e));

        JButton enregistrer = new JButton(textHTML("Enregistrer"));
        buttons.add(enregistrer);
        enregistrer.addActionListener((e) -> this.controller.saveAction(e));

        JButton retour = new JButton(textHTML("Retour"));
        buttons.add(retour);
        retour.addActionListener((e) -> this.controller.returnAction(e));


        pane.add(infoTitle);
        pane.add(doc);
        pane.add(enregistrer);
        pane.add(retour);

        return pane;
    }
    /**
     * 
     * @return
     */
    public JPanel botBarMethod(){
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());

        for(String item : botBar){
            JLabel text = new JLabel(item + " : X");
            botJLabel.add(text);
            pane.add(text);
        }

        return pane;
    }

    // For Template Modify
    public void updateBloc(JSONObject node){

    }

    public static void main(String[] args){
        new TemplatePage();
    }

}