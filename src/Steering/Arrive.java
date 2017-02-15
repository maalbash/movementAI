package Steering;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
import DataStructures.*;
import java.util.ArrayList;

/**
 * Created by mohz2 on 2/15/2017.
 */
public class Arrive extends PApplet{
    PShape fullShape,body,head,breadCrumb;
    Agent player;
    PVector initPos;
    ArrayList<PVector> crumbs;
    long crumbTime;

    public void updateCrumbs(){
        if(crumbs.size() > 8)
        {
            crumbs.remove(0);
        }
        crumbs.add(player.getPosition().copy());
    }

    public void drawCrumbs(){
        for(PVector crumb: crumbs) {
            pushMatrix();
            translate(crumb.x,crumb.y);
            shape(breadCrumb);
            popMatrix();
        }
    }

    public void init(){
        initPos = new PVector(50, 450);
        player = new Agent();
        player.setPosition(initPos);
        player.setOrientation(0);

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

    public void settings(){
        size(500,500);
    }

    public void setup() {
        init();
    }

    public void draw(){
        background(255);

        long time = System.currentTimeMillis();


        pushMatrix();
        translate(player.getPosition().x,player.getPosition().y);
        rotate(player.getOrientation());
        shape(fullShape);
        popMatrix();
    }

    public void mousePressed(){
        PVector tpos = new PVector(mouseX,mouseY);
        GameObject target = new GameObject(tpos,0);
    }

    public static void main(String args[]){ PApplet.main(new String[]{"Steering.Arrive"}); }
}
