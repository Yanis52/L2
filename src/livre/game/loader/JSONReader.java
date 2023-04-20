package livre.game.loader;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * file = fichier  Json
 */
public class JSONReader {
    private JSONObject file;
/**
 * permet de lire le contenu d'un fichier Json
 * @param pathJson
 */
    public JSONReader(String pathJson){
        JSONParser parser = new JSONParser();
        try {
            String path = new File(pathJson).getAbsolutePath(); 
            Object obj = parser.parse(new FileReader(path));

            this.file = (JSONObject)obj;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
//Getter
    public JSONObject getFile(){
        return (JSONObject)this.file;
    }
}