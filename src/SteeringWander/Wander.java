package SteeringWander;


import DataStructures.*;
import SteeringArrive.Align;
import SteeringArrive.Smotion;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by mohz2 on 2/17/2017.
 */
public class Wander extends PApplet{
    PShape fullShape,body,head,breadCrumb;
    Agent player;
    GameObject initTarget;
    PVector initPos;
    Smotion Sarrive;
    Align Salign;
    Wmotion Swander;
    ArrayList<PVector> crumbs;
    long crumbTime, time;
    float top,bot,right,left;

    public void init(){
        initPos = new PVector(width/2, height/2);
        player = new Agent();
        initTarget = new GameObject();
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

        crumbTime = 0;

        Sarrive = new Smotion(3.f,2.f,5.f,20.f, 2.f);
        Salign = new Align( PConstants.PI/300, PConstants.PI/30,PConstants.PI/10, PConstants.PI/2, 30f );
        Sarrive.setPlayer(player);
        Sarrive.setTarget(initTarget);
        Salign.setPlayer(player);
        Salign.setTarget(initTarget);
    }

    public void settings(){
        size(800,800);
    }

    public void setup(){
        top = 0;
        bot = width;
        right = 0;
        left = height;
        init();
        initTarget.setPosition(initPos);
        initTarget.setOrientation(0);
        Swander = new Wmotion();
        Swander.setPlayer(player);
        Swander.setTarget(initTarget);
        Swander.setWanderRate(PConstants.PI);
        Swander.setWanderOffset(50);
        Swander.setWanderRadius(20);
        Swander.setSarrive(Sarrive);
        Swander.setSalign(Salign);
    }

    public void draw(){
        background(255);
        time++;

        Swander.getSteering();
        Swander.Sarrive.getSteering();
        Swander.Salign.getSteering();

        if (Swander.Salign.orientationReached()) {
            player.setRotation(0);
            player.setAngular(0);
        }

        if(time - crumbTime > 5)
        {
            updateCrumbs();
            crumbTime = time;
        }
        player.update();

        inFrame();

        drawCrumbs();

        pushMatrix();
        translate(player.getPosition().x,player.getPosition().y);
        rotate(player.getOrientation());
        shape(fullShape);
        popMatrix();
    }

    public void inFrame() {
        PVector newPosition = new PVector(player.getPosition().x, player.getPosition().y);
        if (player.getPosition().x > right && player.getPosition().x < left) {//x is correct
            if (player.getPosition().y > bot || player.getPosition().y < top) {// only y is not correct
                newPosition.y = (player.getPosition().y > bot) ? (player.getPosition().y - bot) : (player.getPosition().y + bot);
            }
        } else {// x is not correct
            if (player.getPosition().y > top && player.getPosition().y < bot) {//only x is not correct
                newPosition.x = (player.getPosition().x > left) ? (player.getPosition().x - left) : (player.getPosition().x + left);
            } else {// both x and y are messed up
                newPosition.x = (player.getPosition().x > left) ? (player.getPosition().x - left) : (player.getPosition().x + left);
                newPosition.y = (player.getPosition().y > bot) ? (player.getPosition().y - bot) : (player.getPosition().y + bot);
            }
        }
        player.setPosition(newPosition);
    }

    public void updateCrumbs(){
        if(crumbs.size() > 60)
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

    public static void main(String args[]){PApplet.main(new String("SteeringWander.Wander"));}
}
