 /**
 * Created by mohz2 on 12/18/2016.
 */

package Kinematic;

import processing.core.PApplet;
import processing.core.PShape;
import Kinematic.*;
import processing.core.PVector;
import java.util.ArrayList;

 public class Processingtest extends PApplet{
    PShape fullShape,body,head;
    Character player;
    GameObject t1,t2,t3,t4;
    PVector initPos,t1pos,t2pos,t3pos,t4pos;
    ArrayList<GameObject> targets;
    Kmotion kseek;
    int targetIndex;

    public boolean checkTargetReached() {
        return((Math.abs(targets.get(targetIndex).getPosition().x - player.getPosition().x) <= 1.f) && (Math.abs(targets.get(targetIndex).getPosition().y - player.getPosition().y) <= 1.f));
    }

    public void updateTarget()
    {
        targetIndex = (targetIndex + 1)% targets.size();
    }

    public void init() {
        t1pos = new PVector(50,450);
        t1 = new GameObject(t1pos,PI/2);

        t2pos = new PVector(450,450);
        t2 = new GameObject(t2pos,0);

        t3pos = new PVector(450,50);
        t3 = new GameObject(t3pos,-PI/2);

        t4pos = new PVector(50,50);
        t4 = new GameObject(t4pos,-PI);

        initPos = new PVector(50, 450);
        player = new Character();
        player.setPosition(initPos);
        player.setOrientation(0);

        targets = new ArrayList<GameObject>();
        targets.add(t1);
        targets.add(t2);
        targets.add(t3);
        targets.add(t4);

        targetIndex = 0;

        body = createShape(ELLIPSE,0, 0, 20, 20);
        head = createShape(TRIANGLE,0 ,- 10, 0 , 10, 20, 0);
        fullShape = createShape(GROUP);
        fullShape.addChild(body);
        fullShape.addChild(head);
        body.setFill(0);
        head.setFill(0);
    }

    public static void main(String args[]) {
        PApplet.main( new String[]{"Kinematic.Processingtest"});
    }

    public void settings(){
        size(500,500);
    }

    public void setup()
    {
        init();
        kseek = new Kmotion(.25f,PI/500,player,t2);
    }

    public void draw()
    {
        background(255);
        long time = System.currentTimeMillis();

        if(checkTargetReached())
            updateTarget();
        kseek.setTarget(targets.get(targetIndex));
        kseek.getSteering();
        player.update(time);

        pushMatrix();
        translate(player.position.x,player.position.y);
        rotate(player.getOrientation());
        shape(fullShape);
        popMatrix();
    }
}
