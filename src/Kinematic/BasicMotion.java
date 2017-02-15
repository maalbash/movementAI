 /**
 * Created by mohz2 on 12/18/2016.
 */

package Kinematic;

import DataStructures.*;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;

import processing.core.PVector;
import java.util.ArrayList;

 public class BasicMotion extends PApplet{
    PShape fullShape,body,head,breadCrumb;
    Agent player;
    GameObject t1,t2,t3,t4;
    PVector initPos,t1pos,t2pos,t3pos,t4pos;
    ArrayList<GameObject> targets;
    ArrayList<PVector> crumbs;
    Kmotion kseek;
    int targetIndex;
    long crumbTime;

    public boolean checkTargetReached() {
        return((Math.abs(targets.get(targetIndex).getPosition().x - player.getPosition().x) <= 1.f) && (Math.abs(targets.get(targetIndex).getPosition().y - player.getPosition().y) <= 1.f));
    }

    public boolean checkOrientationReached(Agent O1, GameObject O2){
        return Math.abs(O2.getOrientation()- O1.getOrientation()) % 2 * PI < PI/30;
    }

    public void updateTarget()
    {
        targetIndex = (targetIndex + 1)% targets.size();
    }

    public void init() {
        t1pos = new PVector(50,450);
        t1 = new GameObject(t1pos,-PI/2);

        t2pos = new PVector(450,450);
        t2 = new GameObject(t2pos,0);

        t3pos = new PVector(450,50);
        t3 = new GameObject(t3pos,PI/2);

        t4pos = new PVector(50,50);
        t4 = new GameObject(t4pos,PI);

        initPos = new PVector(50, 450);
        player = new Agent();
        player.setPosition(initPos);
        player.setOrientation(0);

        targets = new ArrayList<GameObject>();
        targets.add(t1);
        targets.add(t2);
        targets.add(t3);
        targets.add(t4);

        targetIndex = 0;

        breadCrumb = createShape(ELLIPSE,0, 0 , 4, 4);
        breadCrumb.setFill(155);
        crumbs = new ArrayList<PVector>();

        body = createShape(ELLIPSE,0, 0, 20, 20);
        head = createShape(TRIANGLE,0 ,- 10, 0 , 10, 20, 0);
        fullShape = createShape(GROUP);
        fullShape.addChild(body);
        fullShape.addChild(head);
        body.setFill(0);
        head.setFill(0);

        crumbTime = System.currentTimeMillis();
    }

    public void updateCrumbs(){
        if(crumbs.size() > 54)
        {
            crumbs.remove(0);
        }
        crumbs.add(player.getPosition().copy());
    }

    public void drawCrumbs(){
        for(PVector crumb: crumbs)
        {
            pushMatrix();
            translate(crumb.x,crumb.y);
            shape(breadCrumb);
            popMatrix();
        }
    }

    public void settings(){
        size(500,500);
    }

    public void setup() {
        init();
        kseek = new Kmotion(.25f,PI/450,player,t2);
    }

    public void draw() {
        background(255);
        long time = System.currentTimeMillis();

        if(checkTargetReached())
            updateTarget();
        kseek.setTarget(targets.get(targetIndex));
        kseek.getSteering();
        player.update(time);

        if(checkOrientationReached(player,targets.get(targetIndex)))
        {
            player.setRotation(0);
        }
        if(time - crumbTime > 100) {
            updateCrumbs();
            crumbTime = time;
        }

        drawCrumbs();

        pushMatrix();
        translate(player.getPosition().x,player.getPosition().y);
        rotate(player.getOrientation());
        shape(fullShape);
        popMatrix();
    }

     public static void main(String args[]) {
         PApplet.main( new String[]{"Kinematic.BasicMotion"});
     }
}
