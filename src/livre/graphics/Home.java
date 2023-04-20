package livre.graphics;


import livre.graphics.controller.ControlHome;
import javax.swing.*;
import java.awt.*;


public class Home extends BasicPage{

    private ControlHome controller;
    
    JButton newGameButton = new JButton(textHTML("Nouvelle partie"), new ImageIcon("assets/newGame.png"));
    JButton loadGameButton = new JButton(textHTML("Charger une partie"), new ImageIcon("assets/loadGame.png"));
    JButton templateButton = new JButton(textHTML("Template"), new ImageIcon("assets/loadGame.png"));
    JButton exitGameButton = new JButton(textHTML("Quitter"), new ImageIcon("assets/exitGame.png"));

    public Home(){
        // Page settings
        super();

        // Background, ne marche pas 
        // setBackground();

        //Not working 
        
        // Ajout de boutton
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.controller = new ControlHome(this);

        gbc.ipady = 25;
        gbc.ipadx = 50;

        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridx = 0;  
        gbc.gridy = 0; 
        contentPane.add(newGameButton, gbc);
        newGameButton.addActionListener((e) -> this.controller.actionNewGameButton(e));

        gbc.gridx = 0;  
        gbc.gridy = 1; 
        contentPane.add(loadGameButton, gbc);
        loadGameButton.addActionListener((e) -> this.controller.actionLoadGameButton(e));

        gbc.gridx = 0;  
        gbc.gridy = 2; 
        contentPane.add(templateButton, gbc);
        templateButton.addActionListener((e) -> this.controller.actionTemplateButton(e));

        gbc.gridx = 0;  
        gbc.gridy = 3; 
        contentPane.add(exitGameButton, gbc);
        exitGameButton.addActionListener((e) -> this.controller.actionExitButton(e));

        setVisible(true);

        JLabel background;
        setLayout(null);
        ImageIcon img = new ImageIcon("assets/backGroundTest.jpg");
        background = new JLabel("", img , JLabel.CENTER);
        background.setBounds(0,0,1280,720);
        setLayout(new BorderLayout());
        add(background);

        setVisible(true);
    }
}

