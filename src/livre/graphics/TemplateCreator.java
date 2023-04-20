package livre.graphics;


import java.awt.*;

public class TemplateCreator extends TemplatePage{

    public TemplateCreator(){
        super();

        // Onglet - Bloc
        this.onglets.add(textHTML("Bloc"), blocCreatorMethod());

        // Onglet - Algorithme
        this.onglets.add(textHTML("Algorithme"), algoMethod());

        // Onglet - GraphViz
        this.onglets.add(textHTML("Visualisation"), graphVizMethod());

        this.contentPane.add(this.onglets, BorderLayout.WEST);

        setVisible(true);
    }
}