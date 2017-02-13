/**
 * Created by mohz2 on 2/4/2017.
 */


package Kinematic;

import processing.core.PVector;

public class Character extends GameObject {

    PVector velocity;
    float rotation;
    long lastTime;

    public Character(){
        lastTime = System.currentTimeMillis();
        this.velocity = new PVector(0,0);
        this.rotation = 0;
    }

    public Character(PVector position, float orientation, PVector velocity, float rotation) {
        super(position, orientation);
        this.velocity = velocity;
        this.rotation = rotation;
    }


    public PVector getVelocity() {
        return velocity;
    }

    public void setVelocity(PVector velocity) {
        this.velocity = velocity;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void update(long time)
    {
        long deltaTime = (time - lastTime);

        setPosition(position.add(PVector.mult(getVelocity(),deltaTime)));
        setOrientation(orientation + rotation * deltaTime);

        lastTime = time;
    }

    public float getNewOrientation()
    {
        if(this.getVelocity().magSq() > 0)
            return this.getVelocity().heading();
        return this.getOrientation();
    }



}
