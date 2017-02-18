package Other;

import processing.core.PConstants;
import DataStructures.*;

/**
 * Created by mohz2 on 2/16/2017.
 */
public class Helper {
    public static float mapToRange(float goalOrientation)
    {
        float r = goalOrientation % (PConstants.PI * 2);
        if (Math.abs(r) <= PConstants.PI) {
            return r;
        }
        else
        {
            return (r > PConstants.PI) ? r - 2 * PConstants.PI : r + 2 * PConstants.PI;
        }
    }

    public static boolean checkTargetReached(Agent player, GameObject target) {
        return((Math.abs(target.getPosition().x - player.getPosition().x) <= 1.f) && (Math.abs(target.getPosition().y - player.getPosition().y) <= 1.f));
    }

    public static boolean checkOrientationReached(Agent O1, GameObject O2){
        return Math.abs(O2.getOrientation()- O1.getOrientation()) % 2 * PConstants.PI < PConstants.PI/1000;
    }
}
