/**
 * Created by maalbash on 2/15/2017.
 */

package ArriveSteering;

import DataStructures.*;
import processing.core.PVector;


public class Smotion {
    Agent player;
    GameObject target;

    public Smotion(){

    }

    public Smotion(float maxSpeed,float maxAccel, float radiusOfSatisfaction, float radiusOfDeceleration,float timeToTargetVelocity) {
        this.maxSpeed = maxSpeed;
        this.maxAccel = maxAccel;
        this.radiusOfSatisfaction = radiusOfSatisfaction;
        this.radiusOfDeceleration = radiusOfDeceleration;
        this.timeToTargetVelocity = timeToTargetVelocity;
    }

    public Agent getPlayer() {
        return player;
    }

    public void setPlayer(Agent player) {
        this.player = player;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }

    public void getSteering() {
        float goalSpeed;
        PVector dir = PVector.sub(this.target.getPosition(),this.player.getPosition());
        float dist = dir.mag();

        if (dist < radiusOfSatisfaction) {
            goalSpeed = 0;
        }
        else if(dist > radiusOfDeceleration) {
            goalSpeed = maxSpeed;
        }
        else {
            goalSpeed = maxSpeed * dist/radiusOfDeceleration;
        }

        dir.normalize();
        dir = PVector.mult(dir,goalSpeed);

        player.setLinear(PVector.sub(dir,player.getVelocity()));
        player.setLinear(PVector.div(player.getLinear(),timeToTargetVelocity));
    }

    public void getFlockingSteering(PVector converge, PVector seperate, PVector matchVel){
        float goalSpeed;
        PVector dir = PVector.sub(this.target.getPosition(),this.player.getPosition());
        float dist = dir.mag();

        if (dist < radiusOfSatisfaction) {
            goalSpeed = 0;
        }
        else if(dist > radiusOfDeceleration) {
            goalSpeed = maxSpeed;
        }
        else {
            goalSpeed = maxSpeed * dist/radiusOfDeceleration;
        }
        converge.normalize();
        seperate.normalize();
        matchVel.normalize();
        dir.normalize();
        dir = PVector.mult(dir,goalSpeed);
        converge = converge.mult(goalSpeed);
        seperate = seperate.mult(goalSpeed);
        matchVel = matchVel.mult(goalSpeed);
        dir = dir.add(PVector.mult(converge,0.09f));
        dir = dir.add(PVector.mult(seperate,0.9f));
        dir = dir.add(PVector.mult(matchVel,0.01f));
        player.setLinear(PVector.sub(dir,player.getVelocity()));
        player.setLinear(PVector.div(player.getLinear(),timeToTargetVelocity));
    }

    float maxAccel,maxSpeed, radiusOfSatisfaction, radiusOfDeceleration, timeToTargetVelocity;
}
