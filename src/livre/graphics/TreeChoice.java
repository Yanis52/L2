package livre.graphics;


import livre.graphics.controller.ControlTreeChoice;
import javax.swing.*;
import java.awt.*;


public class TreeChoice extends BasicPage{

    private ControlTreeChoice controller_1;

    //JLabel backgroundImage =  new JLabel("", new ImageIcon("assets/backGroundTest.jpg"), JLabel.CENTER);
    JButton basicTreeButton = new JButton(textHTML("Utiliser l'abre de base"));
    JButton persoTreeButton = new JButton(textHTML("Charger un arbre personalise"));
    JButton returnButton = new JButton(textHTML("Retour"));
    
    public TreeChoice(){
        // Page settings
        super();
        this.controller_1 = new ControlTreeChoice(this);
        // Background, ne marche pas 
        // setBackground();

        // Ajout de boutton
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();  

        gbc.ipady = 25;
        gbc.ipadx = 50;

        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridx = 0;  
        gbc.gridy = 0; 
        contentPane.add(basicTreeButton, gbc);

        gbc.gridx = 1;  
        gbc.gridy = 0; 
        contentPane.add(persoTreeButton, gbc);

        gbc.gridx = 2;  
        gbc.gridy = 0; 
        contentPane.add(returnButton, gbc);
                
        basicTreeButton.addActionListener((e) -> this.controller_1.actionBasicTreeButton(e));
        persoTreeButton.addActionListener((e) -> this.controller_1.actionPersoTreeButton(e));
        returnButton.addActionListener((e) -> this.controller_1.actionReturnButton(e));

        setVisible(true);

        JLabel background;
        setLayout(null);
        ImageIcon img = new ImageIcon("assets/backGroundTest.jpg");
        background = new JLabel("", img , JLabel.CENTER);
        background.setBounds(0,0,1280,720);
        setLayout(new BorderLayout());
        add(background);
    }

}