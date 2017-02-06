/**
 * Created by mohz2 on 12/18/2016.
 */
import processing.core.PApplet;
import processing.core.PShape;

public class Processingtest extends PApplet{
    PShape player,body,head;

    public static void main(String args[]) {
        PApplet.main( new String[]{"Processingtest"});
    }

    public void settings(){
        size(600,600);
    }

    public void setup(){
        body = createShape(ELLIPSE,width/2, height/2, 20, 20);
        head = createShape(TRIANGLE,width/2 ,height/2 - 10, width/2 , height/2 + 10, width/2 + 20, height/2);
        player = createShape(GROUP);
        player.addChild(body);
        player.addChild(head);
        body.setFill(0);
        head.setFill(0);
    }

    public void draw()
    {
        background(255);
        shape(player);
    }
}
