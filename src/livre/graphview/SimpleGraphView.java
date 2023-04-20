package livre.graphview;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

import edu.uci.ics.jung.samples.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.*;

import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

import livre.algorithm.*;
import livre.game.player.*;
import livre.game.scene.*;
import livre.game.loader.*;
import livre.game.*;

public class SimpleGraphView{

    public final Graph<Integer, String> graph;

    public ArrayList<Scene> livre;

    public SimpleGraphView(ArrayList<Scene> livre){ //Transforme un livre en un graphe type de la librairie JUNG2
        graph = new DirectedSparseGraph<>();
        Integer compt = 0;
        for (Scene scn : livre){
            graph.addVertex(scn.getId());
        }
        for (Scene nxtScn : livre){
            for (Integer i : nxtScn.getNextScene()){
                graph.addEdge("Edge-"+compt.toString(), nxtScn.getId(), i);
                compt++;
            }
        }
    }
}
