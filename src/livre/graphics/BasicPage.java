package livre.graphics;


import livre.game.loader.*;
import javax.swing.*;
import java.awt.*;

/**
 * favicon = icone de la fenetre
 * backgroundImage = background du jeux (main menu)
 * contentPane = contenu
 * allItem = tout les items
 * allSkill =  tout les skills
 * allMonster = tout les monstres
 * gen = instance pour generer des arbres
 * X_SIZE = largeur de la fenetre de jeux
 * Y_SIZE = hauteur de la fenetre de jeux
 * css = import de fonctionnalités css dans notre content pane
 * startHtml = implémentation de balises (debut)  Html dans le jeux et du css das le html 
 * endHtml = implémentation de balises de fin (html)
 */
public class BasicPage extends JFrame{
    Image favicon = new ImageIcon("assets/icone_book.png").getImage();
    JLabel backgroundImage =  new JLabel("", new ImageIcon("assets/backGroundTest.jpg"), JLabel.CENTER);

    public JPanel contentPane;
    public EveryItem allItem;
    public EverySkill allSkill;
    public EveryMonster allMonster;
    public TreeGenerator gen;

    public static int X_SIZE = 1280;
    public static int Y_SIZE = 720;

    public String css = "    <style>        * {            font-family: Arial;            color: #212121;        }        h1 {            font-style: italic;       }    </style>    ";
    
    public String startHtml = "<html>" + css + "<h3>";
    public String endHtml = "</h3></html>";

    public BasicPage(){
        // Page settings
        super("Livres dont vous etes le heros");
        setSize(X_SIZE, Y_SIZE);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage(favicon);

        this.contentPane = (JPanel)this.getContentPane();

        this.allItem = new EveryItem();
        this.allSkill = new EverySkill();
        this.allMonster = new EveryMonster(allSkill);
        this.gen = new TreeGenerator();    
    }
    /**
     * permet d'ajouter et le positionner l'arriere plan du jeux
     */
    public void setBackground(){
        backgroundImage.setBounds(0, 0, X_SIZE, Y_SIZE);
        this.setLayout(new BorderLayout());
        this.add(backgroundImage);
    }
    /**
     * permet  de tranformer n'importe quel texte en texte HTML (entre deux balises HTML)
     * @param text
     * @return le texte en mode HTML
     */
    public String textHTML(String text){
        return startHtml + text + endHtml;
    }
    /**
     * permet de retourner tout les items
     * @return tout les items
     */
    public EveryItem allItem(){
        return this.allItem;
    }
    /**
     * permet de retourner tout les skills
     * @return tout les skills
     */
    public EverySkill allSkill(){
        return this.allSkill;
    }
    /**
     * permet de retourner tout les monstres
     * @return tout les monstres
     */
    public EveryMonster allMonster(){
        return this.allMonster;
    }
    /**
     * permet de generer un arbre
     * @return l'abre générer
     */
    public TreeGenerator treeGen(){
        return this.gen;
    }
}
