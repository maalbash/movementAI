package FlockingBehavior;

import BasicMotion.Kmotion;
import DataStructures.Agent;
import WanderSteering.Wmotion;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by mohz2 on 2/18/2017.
 */
public class Boid {

    public Wmotion getSwander() {
        return Swander;
    }

    public void setSwander(Wmotion swander) {
        Swander = swander;
    }

    Wmotion Swander;
    Kmotion Kseek;
    ArrayList<PVector> crumps;

    public Kmotion getKseek() {
        return Kseek;
    }

    public void setKseek(Kmotion kseek) {
        Kseek = kseek;
    }

    public PShape getBoidShape() {
        return BoidShape;
    }

    public void setBoidShape(PShape boidShape) {
        BoidShape = boidShape;
    }

    PShape BoidShape;

    public Boid() {
    }

}
