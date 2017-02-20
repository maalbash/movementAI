/**
 * Created by maalbash on 2/17/2017.
 */
package WanderSteering;

import ArriveSteering.*;
import DataStructures.*;
import processing.core.PVector;

import java.util.Random;


public class Wmotion {
    public Wmotion() {

    }

    public Agent getPlayer() {
        return player;
    }

    public void setPlayer(Agent player) {
        this.player = player;
    }

    public float getWanderRate() {
        return wanderRate;
    }

    public void setWanderRate(float wanderRate) {
        this.wanderRate = wanderRate;
    }

    public float getWanderOrientation() {
        return wanderOrientation;
    }

    public void setWanderOrientation(float wanderOrientation) {
        this.wanderOrientation = wanderOrientation;
    }

    public float getWanderOffset() {
        return wanderOffset;
    }

    public void setWanderOffset(float wanderOffset) {
        this.wanderOffset = wanderOffset;
    }

    public float getWanderRadius() {
        return wanderRadius;
    }

    public void setWanderRadius(float wanderRadius) {
        this.wanderRadius = wanderRadius;
    }

    float wanderRate, wanderOrientation, wanderOffset, wanderRadius;

    Agent player;
    GameObject target;

    public Smotion getSarrive() {
        return Sarrive;
    }

    public void setSarrive(Smotion sarrive) {
        Sarrive = sarrive;
    }

    public Align getSalign() {
        return Salign;
    }

    public void setSalign(Align salign) {
        Salign = salign;
    }

    Smotion Sarrive;
    Align Salign;
    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }



    public void getSteering(){
        //wanderOrientation = randBinomial() * wanderRate;
        wanderOrientation = (float)(Math.random() - Math.random()) * wanderRate; // change wander rate to 2 PI and see what happens
        float targetOrientation = wanderOrientation + player.getOrientation();
        PVector targetPosition = PVector.add(player.getPosition(),PVector.mult(PVector.fromAngle(player.getOrientation()),wanderOffset));
        targetPosition = PVector.add(targetPosition,PVector.mult(PVector.fromAngle(targetOrientation),wanderRadius));

        target.setOrientation(targetOrientation);
        target.setPosition(targetPosition);

        Sarrive.setTarget(target);
        Salign.setTarget(target);
    }

    public float randBinomial(){
        Random r = new Random();
        return r.nextFloat()-r.nextFloat();
    }
}
