package ru.vorazen.my_physics.physengine;

import ru.vorazen.my_physics.entity.MyEntity;
import ru.vorazen.my_physics.jmath.Vector2f;
import ru.vorazen.my_physics.primitives.MyBox;
import ru.vorazen.my_physics.primitives.MyCircle;
import ru.vorazen.my_physics.primitives.Primitive2D;
import ru.vorazen.my_physics.primitives.RigidBody2D;

public class ImpulseResolver {

    public static void resolve(MyEntity myEntity, MyEntity myEntity2, CollisionResult collisionResult) {
        Primitive2D p1 = myEntity.getPrimitive2d();
        Primitive2D p2 = myEntity2.getPrimitive2d();
        if (p1 instanceof MyCircle) {
            if (p2 instanceof MyCircle) {
                circleVSCircle((MyCircle) p1, (MyCircle) p2, collisionResult);
            } else if (p2 instanceof MyBox) {
                circleVSBox((MyCircle) p1, (MyBox) p2, collisionResult);
            } else {
                assert false : "unknown Primitive2D: " + p2.getClass();
            }
        } else if (p1 instanceof MyBox) {
            if (p2 instanceof MyCircle) {
                circleVSBox((MyCircle) p2, (MyBox) p1, collisionResult);
            } else if (p2 instanceof MyBox) {
                boxVSBox((MyBox) p1, (MyBox) p2, collisionResult);
            } else {
                assert false : "unknown Primitive2D: " + p2.getClass();
            }
        } else {
            assert false : "unknown Primitive2D: " + p1.getClass();
        }
    }

    public static void circleVSCircle(MyCircle c1, MyCircle c2, CollisionResult collisionResult) {
        // System.out.println("circle vs circle res");
        RigidBody2D a = c1.getRigidBody2D();
        RigidBody2D b = c2.getRigidBody2D();
        if (a.getInfiniteMass() && b.getInfiniteMass())
            return;
        Vector2f normal = new Vector2f(collisionResult.getNormal());
        Vector2f perp = new Vector2f(-normal.y, normal.x);
        Vector2f v1 = new Vector2f(a.getLinearVelocity());
        Vector2f v2 = new Vector2f(b.getLinearVelocity());
        Vector2f relativeVel = new Vector2f(v2).sub(v1);

        // Moving away from each other? Do nothing
        if (relativeVel.dot(normal) < 0.0f) {
            return;
        }

        float v1x = 0;
        float v2x = 0;
        float v1y = 0;
        float v2y = 0;

        if (a.getInfiniteMass()) {
            v1x = v1.dot(normal);
            v1y = v1.dot(perp);
            v2x = 2 * v1.dot(normal) - v2.dot(normal);
            v2y = v2.dot(perp);
        }
        if (b.getInfiniteMass()) {
            v1x = 2 * v2.dot(normal) - v1.dot(normal);
            v1y = v1.dot(perp);
            v2x = v2.dot(normal);
            v2y = v2.dot(perp);
        }
        if (!a.getInfiniteMass() && !b.getInfiniteMass()) {
            float m1 = a.getMass();
            float m2 = b.getMass();
            v1x = ((m1 - m2) * v1.dot(normal) + 2 * m2 * v2.dot(normal)) / (m1 + m2);
            v1y = v1.dot(perp);
            v2x = ((m2 - m1) * v2.dot(normal) + 2 * m1 * v1.dot(normal)) / (m1 + m2);
            v2y = v2.dot(perp);
        }
        Vector2f ac = a.getAccumForce();
        Vector2f bc = b.getAccumForce();
        // System.out.println("ned ignor" + normal + " " + ac);
        if (ac.dot(normal) > 0.0f) {
            a.setIgnoreForce(new Vector2f(normal));
            // System.out.println("set" + a.getIgnoreForce());
            // System.out.println(normal + " " + ac);
        }
        if (bc.dot(normal) > 0.0f)
            b.setIgnoreForce(new Vector2f(normal));
        // System.out.println(a.getLinearVelocity());
        // System.out.println(b.getLinearVelocity());
        // System.out.println(a.getMass() + " " + b.getMass());
        // System.out.println(normal + " normal");
        // System.out.println(v1x + " " + v1y + " " + v2x + " " + v2y);
        Vector2f newV1 = new Vector2f(normal.x * v1x + perp.x * v1y, normal.y * v1x + perp.y * v1y);
        Vector2f newV2 = new Vector2f(normal.x * v2x + perp.x * v2y, normal.y * v2x + perp.y * v2y);
        // System.out.println(a.getLinearVelocity() + " " + b.getLinearVelocity() +
        // newV1 + " " + newV2);
        // System.out.println(a.getIgnoreForce());
        a.setLinearVelocity(newV1.mul((1 - a.getLinearLossesCoef()) * (1 - b.getLinearLossesCoef())));
        b.setLinearVelocity(newV2.mul((1 - b.getLinearLossesCoef()) * (1 - a.getLinearLossesCoef())));
        Vector2f tForce1 = new Vector2f(perp).mul(a.getAccumForce().dot(perp));
        Vector2f tForce2 = new Vector2f(perp).mul(b.getAccumForce().dot(perp));
        a.addAngularForce(tForce1.legnth()/2 );
        b.addAngularForce(tForce2.legnth()/2) ;
        //System.out.println(collisionResult.getNormal() + " " + normal + " " + perp + " " + tForce1 + " " + tForce2 + " "
        //        + a.getAccumForce() + " "
        //        + b.getAccumForce());
        if (a.getInfiniteJ() && b.getInfiniteJ())
            return;
        float w1 = a.getAngularVelocity();
        float w2 = b.getAngularVelocity();
        // System.out.println(w1 + " " + w2 + " " + a.getInertJ() + " " +
        // b.getInertJ());
        if (Math.abs(w1 + w2) < 0.01f)
            return;
        float w1n = 0;
        float w2n = 0;
        // System.out.println(a.getInfiniteJ() + " " + b.getInfiniteJ());
        if (a.getInfiniteJ()) {
            w1n = w1;
            w2n = -w1;
        }
        if (b.getInfiniteJ()) {
            w1n = -w2;
            w2n = w2;
        }
        if (!a.getInfiniteJ() && !b.getInfiniteJ()) {
            float j1 = a.getRadiusOfInertion()*a.getRadiusOfInertion()*a.getMass();
            float j2 = b.getRadiusOfInertion()*b.getRadiusOfInertion()*b.getMass();
            w1n = (j1 * w1 - j2 * w2) / (j1 + j2);
            w2n = -(j1 * w1 - j2 * w2) / (j1 + j2);
        }
        // System.err.println(w1 + " " + w2 + " " + w1n + " " + w2n);
        a.setAngularVelocity(w1n * (1 - a.getAngularLossesCoef()) * (1 - b.getAngularLossesCoef()));
        b.setAngularVelocity(w2n * (1 - b.getAngularLossesCoef()) * (1 - a.getAngularLossesCoef()));
        // System.out.println(a.getLinearVelocity());
        // System.out.println(b.getLinearVelocity());
        // System.out.println(a.getMass() + " " + b.getMass());
    }

    public static void circleVSBox(MyCircle c1, MyBox b2, CollisionResult collisionResult) {
        System.out.println("circle vs box res");
    }

    public static void boxVSBox(MyBox b1, MyBox b2, CollisionResult collisionResult) {
        System.out.println("box vs box res");
    }
}
