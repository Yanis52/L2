package livre.algorithm;

import livre.game.player.*;
import livre.game.scene.*;
import livre.game.loader.*;
import livre.game.*;

import livre.graphics.*;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;


public interface Algorithm{
    public void load();
    public Float getDif();
    public Float getDifMonster();
    public Float getAveragePowerMonster();
    public String toString();
}