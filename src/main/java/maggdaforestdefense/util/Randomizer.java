/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maggdaforestdefense.util;

import java.util.List;
import java.util.Vector;

/**
 *
 * @author DavidPrivat
 */
public class Randomizer {

    private final Vector<RandomEvent> events;

    public Randomizer() {
        events = new Vector();
    }

    public void addEvent(RandomEvent e) {
        events.add(e);
    }

    public int throwDice() {
        double probsum = 0;
        for (RandomEvent event : events) {
            probsum += event.getProbability();
        }
        double currProb = 0;
        double random = Math.random() * probsum;
        for (RandomEvent event : events) {
            if (currProb < random && random < currProb + event.getProbability()) {
                return event.getNumber();
            } else {
                currProb += event.getProbability();
            }
        }
        return 0;
    }
    
    public static void performWithProb(RandomHandler h, double prob) {
        double ran = Math.random();
        if(prob > ran) {
            h.handle();
        }
    }
    
    public static interface RandomHandler {
        public abstract void handle();
    }
    
    public static double randDouble(double min, double max) {
        return min + Math.random()*(max-min);
    }
    
    public static int randSign() {
        return -1 + 2*(int)(Math.random()+0.5);
    }
    
    public static Object getRandomElement(List list) {
        return list.get((int)(list.size()*Math.random()));
    }
}
