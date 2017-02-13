/**
 * Created by mohz2 on 2/6/2017.
 */

package Kinematic;

import processing.core.PVector;

public class Kmotion {
    float maxSpeed, maxRotation;

    Character player;
    GameObject target;

    public Kmotion(float maxSpeed, float maxRotation, Character player, GameObject target) {
        this.maxSpeed = maxSpeed;
        this.maxRotation = maxRotation;
        this.player = player;
        this.target = target;
    }

    public float getMaxSpeed() {return maxSpeed;}

    public void setMaxSpeed(float maxSpeed) {this.maxSpeed = maxSpeed;}

    public float getMaxRotation() {return maxRotation;}

    public void setMaxRotation(float maxRotation) {this.maxRotation = maxRotation;}

    public Character getPlayer() {return player;}

    public void setPlayer(Character player) {this.player = player;}

    public GameObject getTarget() {return target;}

    public void setTarget(GameObject target) {this.target = target;}

    public void getSteering()
    {
        PVector vel = PVector.sub(this.target.getPosition(),this.player.getPosition());
        vel.normalize();
        vel = vel.mult(maxSpeed);

        this.player.setVelocity(vel);
        this.player.setOrientation(this.player.getNewOrientation());
    }
}
