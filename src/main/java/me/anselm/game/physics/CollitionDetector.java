package me.anselm.game.physics;

import me.anselm.game.entities.Entity;

public class CollitionDetector {

    public static boolean colides(Entity source, Entity target) {

        float sourceXMin = source.getCenter().x - (source.getWidth() / 2);
        float sourceXMax = source.getCenter().x + (source.getWidth() / 2);
        float sourceYMin = source.getCenter().y - (source.getHeight() / 2);
        float sourceYMax = source.getCenter().y + (source.getHeight() / 2);
        float targetXMin = target.getCenter().x - (target.getWidth() / 2);
        float targetXMax = target.getCenter().x + (target.getWidth() / 2);
        float targetYMin = target.getCenter().y - (target.getHeight() / 2);
        float targetYMax = target.getCenter().y + (target.getHeight() / 2);

        if (sourceXMax < targetXMax && sourceXMax > targetXMin) {

            if (sourceYMax < targetYMax && sourceYMax > targetYMin) {
                return true;
            }

            if (sourceYMin > targetYMin && sourceYMin < targetYMax) {
                return true;
            }
        }

        if(sourceXMin > targetXMin && sourceXMax < targetXMax ) {

            if (sourceYMax < targetYMax && sourceYMax > targetYMin) {
                return true;
            }

            if (sourceYMin > targetYMin && sourceYMin < targetYMax) {
                return true;
            }

        }

        return false;
    }
}
