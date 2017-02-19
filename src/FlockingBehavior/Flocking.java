package FlockingBehavior;

import ArriveSteering.Align;
import ArriveSteering.Smotion;

import DataStructures.Agent;
import DataStructures.GameObject;
import Other.Helper;
import WanderSteering.Wmotion;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mohz2 on 2/18/2017.
 */
public class Flocking extends PApplet {
    PShape breadCrumb;
    Random r = new Random();
    PVector initPos, boidInitPos;
    float COHESION_WEIGHT = 0.1f;
    float SEPERATION_DISTANCE = 100f;
    float VELOCITY_MATCHING_WEIGHT = 0.125f;
    Boid leaderBoid;
    ArrayList<Boid> Boids;
    Agent leader;
    GameObject initTarget;
    Smotion Sarrive;
    Align Salign;
    Wmotion Swander;
    ArrayList<Agent> followers;
    long time, crumpTime;
    public void init(){
        breadCrumb = createShape(ELLIPSE,0, 0 , 4, 4);
        breadCrumb.setFill(155);
        Boids = new ArrayList<Boid>();
        for (int i = 0; i < 10; i++)
        {
            Boid boid = new Boid();
            boid.crumps = new ArrayList<PVector>();
            boid.setBoidShape(drawBoids());
            Boids.add(boid);
        }
        leaderBoid = new Boid();
        PShape body = createShape(ELLIPSE,0, 0, 20, 20);
        PShape head = createShape(TRIANGLE,0 ,- 10, 0 , 10, 20, 0);
        PShape leaderShape = createShape(GROUP);
        leaderShape.addChild(body);
        leaderShape.addChild(head);
        body.setFill(color(255,0,0));
        head.setFill(color(255,0,0));
        body.setStroke(color(255,0,0));
        head.setStroke(color(255,0,0));
        leaderBoid.setBoidShape(leaderShape);
        initPos = new PVector(width/2,height/2);
        leader = new Agent();
        leader.setPosition(initPos);

    }
    public void settings(){
        size(800,800);
    }

    public void setup(){
        init();
        initTarget = new GameObject(initPos,0);
        Sarrive = new Smotion(1.f,2.f,5.f,20.f, 2.f);
        Salign = new Align( PConstants.PI/300, PConstants.PI/30,PConstants.PI/10, PConstants.PI/2, 30f );
        Sarrive.setPlayer(leader);
        Sarrive.setTarget(initTarget);
        Salign.setPlayer(leader);
        Salign.setTarget(initTarget);
        Swander = new Wmotion();
        Swander.setPlayer(leader);
        Swander.setTarget(initTarget);
        Swander.setWanderRate(PConstants.PI);
        Swander.setWanderOffset(50);
        Swander.setWanderRadius(20);
        Swander.setSarrive(Sarrive);
        Swander.setSalign(Salign);
        leaderBoid.setSwander(Swander);
        leaderBoid.Swander.setPlayer(leader);
        leaderBoid.crumps = new ArrayList<PVector>();
        AddPlayersToBoids();
    }

    public void draw(){
        background(255);
        time++;


        if(time - crumpTime > 20)
        {
            updateCrumps();
            crumpTime = time;
        }

        drawCrumps();

        leaderBoid.Swander.getSteering();
        leaderBoid.Swander.getSarrive().getSteering();
        leaderBoid.Swander.getSalign().getSteering();

        if(leaderBoid.Swander.getSalign().orientationReached()) {
            leaderBoid.Swander.getPlayer().setRotation(0);
            leaderBoid.Swander.getPlayer().setAngular(0);
        }

        leaderBoid.getSwander().getPlayer().update();

        AllInFrame();

        moveFlock();

        drawSingleFrame();
    }

    public void AddPlayersToBoids() {
        for(int i = 0; i < Boids.size(); i++){
            boidInitPos = new PVector(width/2 - ((r.nextFloat() - r.nextFloat()) * 20.0f), height/2 - ((r.nextFloat() - r.nextFloat()) * 20.0f));
            Agent player = new Agent();
            player.setPosition(boidInitPos);
            Smotion FollowerArrive = new Smotion(1.f,2.f,5.f,20.f, 2.f);
            FollowerArrive.setPlayer(player);
            FollowerArrive.setTarget(leaderBoid.getSwander().getPlayer());
            Boids.get(i).setSarrive(FollowerArrive);
        }
    }

    public void AllInFrame(){
        for(Boid boid : Boids){
            Helper.inFrame(boid.getSarrive().getPlayer(),0,0,height,width);
        }
        Helper.inFrame(leaderBoid.getSwander().getPlayer(),-25,-25,height + 25,width + 25);
    }

    public void drawSingleFrame(){
        pushMatrix();
        translate(leaderBoid.getSwander().getPlayer().getPosition().x,leaderBoid.getSwander().getPlayer().getPosition().y);
        rotate(leaderBoid.getSwander().getPlayer().getOrientation());
        shape(leaderBoid.getBoidShape());
        popMatrix();
        for(Boid boid : Boids){
            pushMatrix();
            translate(boid.getSarrive().getPlayer().getPosition().x,boid.getSarrive().getPlayer().getPosition().y);
            rotate(boid.getSarrive().getPlayer().getOrientation());
            shape(boid.getBoidShape());
            popMatrix();
        }
    }

    public PShape drawBoids(){
        PShape body = createShape(ELLIPSE,0, 0, 10, 10);
        PShape head = createShape(TRIANGLE,0 ,- 5, 0 , 5, 10, 0);
        PShape fullShape = createShape(GROUP);
        fullShape.addChild(body);
        fullShape.addChild(head);
        body.setFill(0);
        head.setFill(0);
        return fullShape;
    }

    public void updateCrumps(){
        for(Boid boid : Boids)
        {
            if(boid.crumps.size() > 20)
            {
                boid.crumps.remove(0);
            }
            boid.crumps.add(boid.getSarrive().getPlayer().getPosition());
        }
        if(leaderBoid.crumps.size() > 20)
            leaderBoid.crumps.remove(0);
        leaderBoid.crumps.add(leaderBoid.getSwander().getPlayer().getPosition());
    }

    public void drawCrumps(){
        for(Boid boid : Boids){
            for(PVector crumb : boid.crumps)
            {
                pushMatrix();
                translate(crumb.x,crumb.y);
                shape(breadCrumb);
                popMatrix();
            }
        }
        for(PVector crumb : leaderBoid.crumps)
        {
            pushMatrix();
            translate(crumb.x,crumb.y);
            shape(breadCrumb);
            popMatrix();
        }
    }

    public void moveFlock(){
        for(Boid boid : Boids){
            PVector resultForce = PVector.add(cohesion(boid),seperation(boid));
            resultForce.add(match_velocity(boid));
            boid.getSarrive().getSteering();
            boid.getSarrive().getPlayer().setVelocity(PVector.add(boid.getSarrive().getPlayer().getVelocity(),resultForce));
            boid.getSarrive().getPlayer().setOrientation(boid.getSarrive().getPlayer().getVelocity().heading());
            boid.getSarrive().getPlayer().update();
        }
    }

    public PVector cohesion(Boid b){
        PVector PCj = new PVector(0,0);
        for(Boid boid : Boids)
        {
            if(boid != b)
            {
                PCj = PVector.add(PCj,boid.getSarrive().getPlayer().getPosition());
            }
        }
        PCj = PCj.div(Boids.size()-1);

        PCj = PVector.sub(PCj,b.getSarrive().getPlayer().getPosition());
        return PVector.mult(PCj,COHESION_WEIGHT);
    }

    public PVector seperation(Boid b){
        PVector PCj = new PVector(0,0);
        for(Boid boid : Boids)
        {
            if (boid != b)
            {
                if((PVector.dist(boid.getSarrive().getPlayer().getPosition(),b.getSarrive().getPlayer().getPosition())) < SEPERATION_DISTANCE)
                {
                    PCj = PVector.sub(PCj,PVector.sub(boid.getSarrive().getPlayer().getPosition(),b.getSarrive().getPlayer().getPosition()));
                }
            }
        }
        return PCj;
    }

    public PVector match_velocity(Boid b){
        PVector PCj = new PVector(0,0);
        for(Boid boid : Boids)
        {
            if(boid != b)
            {
                PCj = PVector.add(PCj,boid.getSarrive().getPlayer().getVelocity());
            }
        }
        PCj = PVector.div(PCj,Boids.size()-1);

        return PVector.div(PCj,VELOCITY_MATCHING_WEIGHT);
    }

    public static void main(String args[]){ PApplet.main(new String ("FlockingBehavior.Flocking"));}
}
