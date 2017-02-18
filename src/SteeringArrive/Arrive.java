package SteeringArrive;

import processing.core.PApplet;
import processing.core.PConstants;
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
    GameObject initTarget;
    PVector initPos;
    Smotion Sarrive;
    Align Salign;
    ArrayList<PVector> crumbs;
    long crumbTime, time;

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
        initTarget = new GameObject();
        player.setPosition(initPos);
        player.setOrientation(0);
        initTarget.setPosition(initPos);
        initTarget.setOrientation(0);

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

        crumbTime = 0;
    }

    public void settings(){
        size(500,500);
    }

    public void setup() {
        init();
        Sarrive = new Smotion(5.f,2.f,5.f,20.f, 2.f);
        Salign = new Align( PConstants.PI/300, PConstants.PI/30,PConstants.PI/10, PConstants.PI/2, 30f );
        Sarrive.setPlayer(player);
        Sarrive.setTarget(initTarget);
        Salign.setPlayer(player);
        Salign.setTarget(initTarget);
    }

    public void draw(){
        background(255);

        time++;

        Sarrive.getSteering();
        Salign.getSteering();
        if (Salign.orientationReached()) {
            player.setRotation(0);
            player.setAngular(0);
        }
        player.update();

        if(time-crumbTime > 3)
        {
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

    public void mousePressed(){
        PVector tpos = new PVector(mouseX,mouseY);
        GameObject target = new GameObject(tpos,0);

        PVector dir = PVector.sub(tpos,player.getPosition());
        Sarrive.setTarget(target);
        Salign.setTarget(target);
        Salign.getTarget().setOrientation(dir.heading());
    }

    public static void main(String args[]){ PApplet.main(new String[]{"SteeringArrive.Arrive"}); }
}
