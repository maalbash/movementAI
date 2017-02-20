/**
 * Created by maalbash on 2/6/2017.
 */

package BasicMotion;

import DataStructures.Agent;
import DataStructures.GameObject;
import Other.Helper;
import processing.core.PConstants;
import processing.core.PVector;

public class Kmotion {
    float maxSpeed, maxRotation;

    Agent player;
    GameObject target;

    public Kmotion(float maxSpeed, float maxRotation, Agent player, GameObject target) {
        this.maxSpeed = maxSpeed;
        this.maxRotation = maxRotation;
        this.player = player;
        this.target = target;
    }

    public float getMaxSpeed() {return maxSpeed;}

    public void setMaxSpeed(float maxSpeed) {this.maxSpeed = maxSpeed;}

    public float getMaxRotation() {return maxRotation;}

    public void setMaxRotation(float maxRotation) {this.maxRotation = maxRotation;}

    public Agent getPlayer() {return player;}

    public void setPlayer(Agent player) {this.player = player;}

    public GameObject getTarget() {return target;}

    public void setTarget(GameObject target) {this.target = target;}

    public void orientationReached(){
        if(Math.abs(player.getOrientation() - target.getOrientation()) % (2 * PConstants.PI) < PConstants.PI/30)
        {
            player.setRotation(0);
        }
        else if(player.getVelocity().mag() > .2f)
        {
            player.setRotation(maxRotation);
        }
    }

    public void getKinematic()
    {
        PVector vel = PVector.sub(this.target.getPosition(),this.player.getPosition());
        vel.normalize();
        vel = vel.mult(maxSpeed);

        this.player.setVelocity(vel);

        if(this.player.getVelocity().mag() != 0)
        {
            float goalOrientation = (float)Math.atan(this.getTarget().getPosition().x/this.getTarget().getPosition().y);
            goalOrientation = Helper.mapToRange(goalOrientation);
            float goalRot = goalOrientation - this.player.getOrientation();
            float dir = goalRot/Math.abs(goalRot);
            goalRot = (goalRot < maxRotation)? goalRot : dir * maxRotation;
            this.player.setRotation(goalRot);
        }

        orientationReached();

        //orientationReached();
        //this.player.setOrientation(this.player.getNewOrientation());
    }

    public void getSteeringFlock(){

    }
}
