/**
 * Created by mohz2 on 2/4/2017.
 */

import processing.core.PVector;

public class Kinematic {

    PVector position;
    PVector velocity;
    float orientation;
    float rotation;

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public PVector getVelocity() {
        return velocity;
    }

    public void setVelocity(PVector velocity) {
        this.velocity = velocity;
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void update(Kinematic kinematicMotion, long time)
    {
        PVector.add(position, PVector.mult(velocity,time));
        orientation += rotation * time;
    }
}
