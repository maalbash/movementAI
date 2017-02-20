/**
 * Created by maalbash on 2/18/2017.
 */

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


public class Flocking extends PApplet {
    PShape breadCrumb, leaderBreadCrumb;
    Random r = new Random();
    PVector initPos, boidInitPos, leaderInitPos;
    float COHESION_FACTOR = 0.4f;
    float SEPERATION_DISTANCE = 20f;
    float SEPERATION_FACTOR = 0.5f;
    float VELOCITY_MATCHING_WEIGHT = 0.1f;
    ArrayList<Boid> Wanderers;
    ArrayList<Boid> Boids;
    Smotion Sarrive;
    Align Salign;
    Wmotion Swander;
    long time, crumpTime;

    public void init(){
        breadCrumb = createShape(ELLIPSE,0, 0 , 4, 4);
        leaderBreadCrumb = createShape(ELLIPSE,0, 0 , 4, 4);
        breadCrumb.setFill(color(0,255,100));
        leaderBreadCrumb.setFill(color(255,0,200));
        Boids = new ArrayList<Boid>();
        Wanderers = new ArrayList<Boid>();
        for(int j= 0; j < 4; j++){
            Boid boid = new Boid();
            boid.setBoidShape(drawLeader());
            Wanderers.add(boid);
        }

        for (int i = 0; i < 6; i++)
        {
            Boid boid = new Boid();
            boid.crumps = new ArrayList<PVector>();
            boid.setBoidShape(drawBoids());
            Boids.add(boid);
        }

    }

    public void settings(){
        size(1000,1000);
    }

    public void setup(){
        init();
        AddPlayersToLeaders();
        AddPlayersToFollowers();
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

        moveLeaders();

        FindLeader();

        AllInFrame();

        moveFlock();

        drawSingleFrame();
    }

    public void moveLeaders(){
        for(Boid leader : Wanderers){
            leader.Swander.getSteering();
            leader.Swander.getSarrive().getSteering();
            leader.Swander.getSalign().getSteering();

            if(leader.Swander.getSalign().orientationReached()){
                leader.Swander.getPlayer().setRotation(0);
                leader.Swander.getPlayer().setAngular(0);
            }

            leader.getSwander().getPlayer().update();
        }
    }

    public void FindLeader(){

        for(Boid follower : Boids){
            float maxDist = 1000000.0f;
            for(Boid leader : Wanderers){
                if(PVector.dist(follower.getSarrive().getPlayer().getPosition(),leader.getSwander().getPlayer().getPosition()) < maxDist){
                    maxDist = PVector.dist(follower.getSarrive().getPlayer().getPosition(),leader.getSwander().getPlayer().getPosition());
                    follower.getSarrive().setTarget(new GameObject(leader.getSwander().getPlayer().getPosition(),leader.getSwander().getPlayer().getOrientation()));
                    follower.getSalign().setTarget(new GameObject(leader.getSwander().getPlayer().getPosition(),leader.getSwander().getPlayer().getOrientation()));
                }
            }
        }
    }

    public void AddPlayersToLeaders(){
        for(Boid wanderer: Wanderers){
            leaderInitPos = new PVector(- ((r.nextFloat() - r.nextFloat()) * width), ((r.nextFloat() - r.nextFloat()) * height));
            Agent player = new Agent();
            player.setPosition(leaderInitPos);
            Sarrive = new Smotion(2.f,2.f,5.f,20.f, 2.f);
            Salign = new Align( PConstants.PI/300, PConstants.PI/30,PConstants.PI/10, PConstants.PI/2, 30f );
            Sarrive.setPlayer(player);
            Sarrive.setTarget(new GameObject(leaderInitPos,0));
            Salign.setPlayer(player);
            Salign.setTarget(new GameObject(leaderInitPos,0));
            Swander = new Wmotion();
            Swander.setPlayer(player);
            Swander.setTarget(new GameObject(leaderInitPos,0));
            Swander.setWanderRate(PConstants.PI);
            Swander.setWanderOffset(50);
            Swander.setWanderRadius(20);
            Swander.setSarrive(Sarrive);
            Swander.setSalign(Salign);
            wanderer.setSwander(Swander);
            wanderer.crumps = new ArrayList<PVector>();
        }
    }

    public void AddPlayersToFollowers() {
        for(int i = 0; i < Boids.size(); i++){
            boidInitPos = new PVector(width/2 - ((r.nextFloat() - r.nextFloat()) * 20.0f), height/2 - ((r.nextFloat() - r.nextFloat()) * 20.0f));
            Agent player = new Agent();
            player.setPosition(boidInitPos);
            Smotion FollowerArrive = new Smotion(1.5f,2.f,5.f,20.f, 2.f);
            Align FollowerAlign = new Align(PConstants.PI/50, PConstants.PI/30,PConstants.PI/15, PConstants.PI/2, 10f);
            FollowerArrive.setPlayer(player);
            FollowerArrive.setTarget(Wanderers.get(0).getSwander().getPlayer());
            FollowerAlign.setPlayer(player);
            FollowerAlign.setTarget(Wanderers.get(0).getSwander().getPlayer());
            Boids.get(i).setSarrive(FollowerArrive);
            Boids.get(i).setSalign(FollowerAlign);
        }
    }

    public void AllInFrame(){
        for(Boid boid : Boids){
            Helper.inFrame(boid.getSarrive().getPlayer(),0,0,height,width);
        }
        for(Boid leader : Wanderers){
            Helper.inFrame(leader.getSwander().getPlayer(),0,0,height ,width );
        }

    }

    public void drawSingleFrame(){
        for(Boid leader : Wanderers){
            pushMatrix();
            translate(leader.getSwander().getPlayer().getPosition().x,leader.getSwander().getPlayer().getPosition().y);
            rotate(leader.getSwander().getPlayer().getOrientation());
            shape(leader.getBoidShape());
            popMatrix();
        }
        for(Boid boid : Boids){
            pushMatrix();
            translate(boid.getSarrive().getPlayer().getPosition().x,boid.getSarrive().getPlayer().getPosition().y);
            rotate(boid.getSarrive().getPlayer().getOrientation());
            shape(boid.getBoidShape());
            popMatrix();
        }
    }

    public PShape drawLeader(){
        PShape body = createShape(ELLIPSE,0, 0, 20, 20);
        PShape head = createShape(TRIANGLE,0 ,- 10, 0 , 10, 20, 0);
        PShape leaderShape = createShape(GROUP);
        leaderShape.addChild(body);
        leaderShape.addChild(head);
        body.setFill(color(255,0,0));
        head.setFill(color(255,0,0));
        body.setStroke(color(255,0,0));
        head.setStroke(color(255,0,0));
        return leaderShape;
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
        for(Boid leader : Wanderers){
            if(leader.crumps.size() > 20)
                leader.crumps.remove(0);
            leader.crumps.add(leader.getSwander().getPlayer().getPosition());
        }

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
        for(Boid leader : Wanderers){
            for(PVector crumb : leader.crumps)
            {
                pushMatrix();
                translate(crumb.x,crumb.y);
                shape(leaderBreadCrumb);
                popMatrix();
            }
        }

    }

    public void moveFlock(){
        for(Boid boid : Boids){
            boid.getSarrive().getFlockingSteering(cohesion(boid),seperation(boid),match_velocity(boid));
            boid.getSarrive().getPlayer().update();
            boid.getSalign().getSteering();
            if(boid.getSalign().orientationReached())
            {
                boid.getSalign().getPlayer().setRotation(0);
                boid.getSalign().getPlayer().setAngular(0);
            }
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
        return PVector.mult(PCj,COHESION_FACTOR);
    }

    public PVector seperation(Boid b){
        PVector PCj = new PVector(0,0);
        for(Boid boid : Boids)
        {
            if (boid != b)
            {
                if((PVector.dist(boid.getSarrive().getPlayer().getPosition(),b.getSarrive().getPlayer().getPosition())) <= SEPERATION_DISTANCE)
                {
                    PCj = PVector.sub(PCj,PVector.sub(boid.getSarrive().getPlayer().getPosition(),b.getSarrive().getPlayer().getPosition()));
                }
            }
        }
        return PVector.mult(PCj,SEPERATION_FACTOR);
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

    public void mousePressed(){
        PVector mouseLocation = new PVector(mouseX,mouseY);
        Boid newFollower = new Boid();
        newFollower.crumps = new ArrayList<PVector>();
        newFollower.setBoidShape(drawBoids());
        Agent player = new Agent();
        player.setPosition(mouseLocation);
        Smotion FollowerArrive = new Smotion(1.5f,2.f,5.f,20.f, 2.f);
        Align FollowerAlign = new Align(PConstants.PI/50, PConstants.PI/30,PConstants.PI/15, PConstants.PI/2, 10f);
        FollowerArrive.setPlayer(player);
        FollowerArrive.setTarget(Wanderers.get(0).getSwander().getPlayer());
        FollowerAlign.setPlayer(player);
        FollowerAlign.setTarget(Wanderers.get(0).getSwander().getPlayer());
        newFollower.setSarrive(FollowerArrive);
        newFollower.setSalign(FollowerAlign);
        Boids.add(newFollower);
    }

    public static void main(String args[]){ PApplet.main(new String ("FlockingBehavior.Flocking"));}
}
