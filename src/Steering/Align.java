package Steering;

/**
 * Created by mohz2 on 2/15/2017.
 */

import DataStructures.Agent;
import DataStructures.GameObject;
import Other.*;
import processing.core.PVector;

public class Align extends Smotion{

    public Align(float maxAngular, float maxRot, float ROS, float ROD, float timeToTargetAccel) {
        this.maxAccel = maxAngular;
        this.maxSpeed = maxRot;
        this.radiusOfDeceleration = ROD;
        this.radiusOfSatisfaction = ROS;
        this.timeToTargetVelocity = timeToTargetAccel;
    }

    public void getSteering(){
        float goalRot;
        float rotation = this.target.getOrientation() - this.player.getOrientation();
        rotation = Helper.mapToRange(rotation);

        float rotSize = Math.abs(rotation);
        if(rotSize < this.radiusOfSatisfaction)
            goalRot = 0;
        else if(rotSize > this.radiusOfDeceleration)
            goalRot = this.maxSpeed;
        else
            goalRot = this.maxSpeed * rotSize/this.radiusOfDeceleration;

        goalRot *= rotation/Math.abs(rotation);

        this.player.setAngular(goalRot - this.player.getRotation());
        this.player.setAngular(this.player.getAngular()/this.timeToTargetVelocity);
    }

    public boolean orientationReached(){
        return Helper.checkOrientationReached(this.player,this.target);
    }
}
