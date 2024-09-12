package ru.vorazen.main;

import ru.vorazen.entity.Block;
import ru.vorazen.entity.SuperEntity;

public class CollisionChecker {
    World world;

    public CollisionChecker(World w) {
        world = w;
    }

    public boolean checkCollison(SuperEntity ent) {
        Block[] bl = world.blocks;
        boolean coll = false;
        Block b = null;
        boolean x = false;
        boolean y = false;
        for (Block blocks : bl) {
            if ((ent.worldX + ent.collisionWidth <= blocks.worldX
                    && ent.worldX + ent.collisionWidth + ent.speedX + ent.intSpeedX >= blocks.worldX)
                    || (ent.worldX >= blocks.worldX + blocks.collisionWidth
                            && ent.worldX + ent.speedX + ent.intSpeedX <= blocks.worldX + blocks.collisionWidth))
                if ((ent.worldY + ent.collisionHeight >= blocks.worldY
                        && ent.worldY + ent.collisionHeight <= blocks.worldY + blocks.collisionHeight)
                        || (ent.worldY >= blocks.worldY
                                && ent.worldY <= blocks.worldY + blocks.collisionHeight)) {
                    x = true;
                }

            if ((ent.worldY + ent.collisionHeight <= blocks.worldY
                    && ent.worldY + ent.collisionHeight + ent.speedY + ent.intSpeedY >= blocks.worldY)
                    || (ent.worldY >= blocks.worldY + blocks.collisionHeight
                            && ent.worldY + ent.speedY + ent.intSpeedY <= blocks.worldY + blocks.collisionHeight))
                if ((ent.worldX + ent.collisionWidth >= blocks.worldX
                        && ent.worldX + ent.collisionWidth <= blocks.worldX + blocks.collisionWidth)
                        || (ent.worldX >= blocks.worldX
                                && ent.worldX <= blocks.worldX + blocks.collisionWidth)) {
                    y = true;
                }
            //System.err.println(x + " " + y);
            if (x || y) {
                b = blocks;
                if (x) {
                    ent.speedX = 0;
                    ent.intSpeedX = 0;
                    ent.speedX=0;
                    ent.accelX=0;
                    ent.intAccelX=0;
                }
                if (y) {
                    ent.speedY = 0;
                    ent.intSpeedY = 0;
                    ent.accelY=0;
                    ent.intAccelY=0;
                }
            }

        }

        return true;
    }
}
