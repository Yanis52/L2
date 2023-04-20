package livre.graphics;



import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.*;



public class TemplateChoice extends BasicPage{

    JButton createTemplateButton = new JButton(textHTML("Creer un template"));
    JButton modifyTemplateButton = new JButton(textHTML("Modifier un template"));
    JButton returnButton = new JButton(textHTML("Retour"));

    public TemplateChoice(){
        // Page settings
        super();

        // Ajout de boutton
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();  

        gbc.fill = GridBagConstraints.HORIZONTAL;  
        gbc.gridx = 0;  
        gbc.gridy = 0; 
        contentPane.add(createTemplateButton, gbc);
        createTemplateButton.addActionListener((e) -> actionCreateTemplateButton(e));

        gbc.gridx = 1;  
        gbc.gridy = 0; 
        contentPane.add(modifyTemplateButton, gbc);
        modifyTemplateButton.addActionListener((e) -> actionModifyTemplateButton(e));

        gbc.gridx = 2;  
        gbc.gridy = 0; 
        contentPane.add(returnButton, gbc);
        returnButton.addActionListener((e) -> actionReturnButton(e));

        setVisible(true);

        JLabel background;
        setLayout(null);
        ImageIcon img = new ImageIcon("assets/backGroundTest.jpg");
        background = new JLabel("", img , JLabel.CENTER);
        background.setBounds(0,0,1280,720);
        setLayout(new BorderLayout());
        add(background);
    }
    /**
     * permet d'ouvrir la page pour creer un template
     * @param event
     */
    public void actionCreateTemplateButton(ActionEvent event){
        this.setVisible(false);
        new TemplateCreator();
        dispose();
    }
    /**
     * permet d'ouvrir la page de modification de template
     * @param event
     */
    public void actionModifyTemplateButton(ActionEvent event){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON Template game doc", "json"));

        int option = fileChooser.showOpenDialog(this);

        if(option == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            
            JSONParser parser = new JSONParser();
            try {
                String path = file.getAbsolutePath(); 
                Object obj = parser.parse(new FileReader(path));
                JSONObject fileJSON = (JSONObject)obj;

                this.setVisible(false);
                new TemplateModify(fileJSON);
                dispose();
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }
    /**
     * permet de retourner au menu principal
     * @param event
     */
    public void actionReturnButton(ActionEvent event){
        setVisible(false);
        new Home();
        dispose();
    }
}