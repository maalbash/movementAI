package WanderSteering;


import DataStructures.*;
import ArriveSteering.Align;
import ArriveSteering.Smotion;
import Other.Helper;
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
    float bot,right;

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

        Sarrive = new Smotion(5.f,2.f,5.f,20.f, 2.f);
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
        bot = height;
        right = width;
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

        Helper.inFrame(player, 0,0,bot, right);

        drawCrumbs();

        pushMatrix();
        translate(player.getPosition().x,player.getPosition().y);
        rotate(player.getOrientation());
        shape(fullShape);
        popMatrix();
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

    public static void main(String args[]){PApplet.main(new String("WanderSteering.Wander"));}
}
