package ru.vorazen.my_physics.physengine;

import ru.vorazen.my_physics.jmath.Vector2f;
import ru.vorazen.my_physics.primitives.Primitive2D;

public class ExternalForces {
    private static Vector2f gravity = new Vector2f();

    public static void setGravity(Vector2f g) {
        gravity = g;
    }

    public static void applyExternalForce(Primitive2D body) {
        body.getRigidBody2D().addAccumForce(gravity);
    }

}
