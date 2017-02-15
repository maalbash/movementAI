/**
 * Created by mohz2 on 2/6/2017.
 */

package DataStructures;

import processing.core.PVector;

public class GameObject {

    PVector position;
    float orientation;

    public GameObject(){

    }

    public GameObject(PVector position, float orientation) {
        this.position = position;
        this.orientation = orientation;
    }


    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

}
