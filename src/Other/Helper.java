package Other;

import processing.core.PConstants;
import DataStructures.*;
import processing.core.PVector;

/**
 * Created by mohz2 on 2/16/2017.
 */
public class Helper {
    public static float mapToRange(float goalOrientation) {
        float r = goalOrientation % (PConstants.PI * 2);
        if (Math.abs(r) <= PConstants.PI) {
            return r;
        }
        else {
            return (r > PConstants.PI) ? r - 2 * PConstants.PI : r + 2 * PConstants.PI;
        }
    }

    public static boolean checkTargetReached(Agent player, GameObject target) {
        return((Math.abs(target.getPosition().x - player.getPosition().x) <= 1.f) && (Math.abs(target.getPosition().y - player.getPosition().y) <= 1.f));
    }

    public static boolean checkOrientationReached(Agent O1, GameObject O2){
        return Math.abs(O2.getOrientation()- O1.getOrientation()) % 2 * PConstants.PI < PConstants.PI/1000;
    }

    public static void inFrame(Agent player, float top, float left, float bot, float right) {
        PVector newPosition = new PVector(player.getPosition().x, player.getPosition().y);
        if (player.getPosition().x > left && player.getPosition().x < right) {//x is correct
            if (player.getPosition().y > bot || player.getPosition().y < top) {// only y is not correct
                newPosition.y = (player.getPosition().y > bot) ? (player.getPosition().y - bot + top) : (player.getPosition().y + bot - top);
            }
        } else {// x is not correct
            if (player.getPosition().y > top && player.getPosition().y < bot) {//only x is not correct
                newPosition.x = (player.getPosition().x > right) ? (player.getPosition().x - right + left) : (player.getPosition().x + right - left);
            } else {// both x and y are messed up
                newPosition.x = (player.getPosition().x > right) ? (player.getPosition().x - right + left) : (player.getPosition().x + right - left);
                newPosition.y = (player.getPosition().y > bot) ? (player.getPosition().y - bot + top) : (player.getPosition().y + bot - top);
            }
        }
        player.setPosition(newPosition);
    }
}
