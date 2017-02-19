/**
 * Created by mohz2 on 2/4/2017.
 */


package DataStructures;

import Other.Helper;
import processing.core.PConstants;
import processing.core.PVector;

public class Agent extends GameObject {

    PVector velocity;
    float rotation;
    PVector linear;
    float angular;


    public PVector getLinear() {
        return linear;
    }

    public void setLinear(PVector linear) {
        this.linear = linear;
    }

    public float getAngular() {
        return angular;
    }

    public void setAngular(float angular) {
        this.angular = angular;
    }


    public Agent(){
        this.position = new PVector(0,0);
        this.velocity = new PVector(0,0);
        this.rotation = 0;
        this.linear = new PVector(0,0);
        this.angular = 0;
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



    public void update()
    {
        setPosition(position.add(velocity));
        float goalOrientation = orientation + rotation; //* deltaTime
        goalOrientation = Helper.mapToRange(goalOrientation);
        setOrientation(goalOrientation);

        //setOrientation(this.getNewOrientation());
        setVelocity(PVector.add(velocity,linear));
        setRotation(rotation + angular);


    }

    public float getNewOrientation()
    {
        if(this.getVelocity().mag() > 0)
            return this.getVelocity().heading();
        return this.getOrientation();
    }





}
