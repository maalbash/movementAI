/**
 * Created by maalbash on 2/15/2017.
 */

package ArriveSteering;


import Other.*;

public class Align extends Smotion{

    public Align(){
    }

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

        float aaccel = goalRot - this.player.getRotation();
        aaccel /= timeToTargetVelocity;

        if(Math.abs(aaccel) > Math.abs(maxAccel)) {
            aaccel /= Math.abs(aaccel);
            aaccel *= maxAccel;
        }

        this.player.setAngular(aaccel);
    }

    public boolean orientationReached(){
        return Helper.checkOrientationReached(this.player,this.target);
    }
}
