package ru.vorazen.my_physics.physengine;

import ru.vorazen.my_physics.entity.MyEntity;
import ru.vorazen.my_physics.jmath.Vector2f;
import ru.vorazen.my_physics.primitives.MyBox;
import ru.vorazen.my_physics.primitives.MyCircle;
import ru.vorazen.my_physics.primitives.Primitive2D;
import ru.vorazen.my_physics.primitives.RigidBody2D;

public class MyCollisionChecker {

    public static CollisionResult checkCollision(MyEntity e1, MyEntity e2) {
        Primitive2D p1 = e1.getPrimitive2d();
        Primitive2D p2 = e2.getPrimitive2d();
        if (p1 instanceof MyCircle) {
            if (p2 instanceof MyCircle) {
                return circleVSCircle((MyCircle) p1, (MyCircle) p2);
            } else if (p2 instanceof MyBox) {
                return circleVSBox((MyCircle) p1, (MyBox) p2);
            } else {
                assert false : "unknown Primitive2D: " + p2.getClass();
            }
        } else if (p1 instanceof MyBox) {
            if (p2 instanceof MyCircle) {
                return circleVSBox((MyCircle) p2, (MyBox) p1);
            } else if (p2 instanceof MyBox) {
                return boxVSBox((MyBox) p1, (MyBox) p2);
            } else {
                assert false : "unknown Primitive2D: " + p2.getClass();
            }
        } else {
            assert false : "unknown Primitive2D: " + p1.getClass();
        }
        return null;
    }

    public static CollisionResult circleVSCircle(MyCircle c1, MyCircle c2) {
        // System.err.println("circle VS circle coll");
        RigidBody2D rb1 = c1.getRigidBody2D();
        RigidBody2D rb2 = c2.getRigidBody2D();
        Vector2f p1 = new Vector2f(rb1.getPosition());
        Vector2f p2 = new Vector2f(rb2.getPosition());
        float radSum = c1.getRadius() + c2.getRadius();
        Vector2f normal = new Vector2f(p1).sub(p2);
        float flag = normal.lenthSquared() - radSum * radSum;
        if (flag < 0) {
            CollisionResult res = new CollisionResult();
            float l = normal.legnth();
            normal.normalize();
            float depth = radSum - l;
            res.setNormal(new Vector2f(normal));
           // System.out.println(res.getNormal());
            res.addPoint(p1.add(normal.mul(c1.getRadius() + depth / 2)));
            return res;
        }
        return null;
    }

    public static CollisionResult circleVSBox(MyCircle c1, MyBox b2) {
        // System.err.println("circle VS box coll");
        return null;
    }

    public static CollisionResult boxVSBox(MyBox b1, MyBox b2) {
        // System.err.println("box VS box coll");
        return null;
    }
}
