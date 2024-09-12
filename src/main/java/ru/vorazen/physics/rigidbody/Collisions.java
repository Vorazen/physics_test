package ru.vorazen.physics.rigidbody;

import ru.vorazen.physics.jmath.Vector2f;
import ru.vorazen.physics.primitives.Circle;
import ru.vorazen.physics.primitives.Collider2D;

public class Collisions {
    public static CollisionManifold findCollisionFeatures(Collider2D c1, Collider2D c2) {
        if (c1 instanceof Circle && c2 instanceof Circle) {
            return findCollisionFeatures((Circle) c1, (Circle) c2);
        } else {
            assert false : "unknown collider: " + c1.getClass() + " vs " + c2.getClass();

        }
        return null;
    }

    public static CollisionManifold findCollisionFeatures(Circle a, Circle b) {
        //System.out.println("search for collisions between circles");
        CollisionManifold res = new CollisionManifold();
        float sumRad = a.getRadius() + b.getRadius();
        Vector2f dist = new Vector2f(b.getCenter()).sub(a.getCenter());
        //System.err.println(dist+" "+sumRad);
        if (dist.lenthSquared() - sumRad * sumRad > 0) {
            return res;
        }
        float depth = (float) (Math.abs(dist.legnth() - sumRad) * 0.5);
        Vector2f normal = new Vector2f(dist);
        normal.normalize();
        float distToPoint = a.getRadius() - depth;
        Vector2f contactPoint = new Vector2f(a.getCenter()).add(new Vector2f(normal).mul(distToPoint));
        res = new CollisionManifold(normal, depth);
        res.addContactPoint(contactPoint);
        
        return res;
    }
}
