package FlockingBehavior;

import ArriveSteering.Align;
import ArriveSteering.Smotion;
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

    public Smotion getSarrive() {
        return Sarrive;
    }

    public void setSarrive(Smotion sarrive) {
        Sarrive = sarrive;
    }

    public ArrayList<PVector> getCrumps() {
        return crumps;
    }

    public void setCrumps(ArrayList<PVector> crumps) {
        this.crumps = crumps;
    }

    Smotion Sarrive;

    public Align getSalign() {
        return Salign;
    }

    public void setSalign(Align salign) {
        Salign = salign;
    }

    Align Salign;
    ArrayList<PVector> crumps;

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
