/**
 * Created by mohz2 on 2/4/2017.
 */


package DataStructures;

import processing.core.PConstants;
import processing.core.PVector;

public class Agent extends GameObject {

    PVector velocity;
    float rotation;
    long lastTime;

    public Agent(){
        lastTime = System.currentTimeMillis();
        this.velocity = new PVector(0,0);
        this.rotation = 0;
    }

    public Agent(PVector position, float orientation, PVector velocity, float rotation) {
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

    public float mapToRange(float goalOrientation)
    {
        float r = goalOrientation % PConstants.PI * 2;
        if (Math.abs(r) <= PConstants.PI) {
            return r;
        }
        else
        {
            return (r > PConstants.PI) ? r - 2 * PConstants.PI : r + 2 * PConstants.PI;
        }
    }

    public void update(long time)
    {
        long deltaTime = (time - lastTime);

        setPosition(position.add(PVector.mult(getVelocity(),deltaTime)));
        float goalOrientation = orientation + rotation * deltaTime;
        goalOrientation = mapToRange(goalOrientation);
        setOrientation(goalOrientation);

        lastTime = time;
    }

    public float getNewOrientation()
    {
        if(this.getVelocity().mag() > .2f)
            return this.getVelocity().heading();
        return this.getOrientation();
    }





}
