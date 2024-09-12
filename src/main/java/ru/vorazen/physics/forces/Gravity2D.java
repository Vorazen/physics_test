package ru.vorazen.physics.forces;

import ru.vorazen.physics.jmath.Vector2f;
import ru.vorazen.physics.rigidbody.RigidBody;

public class Gravity2D implements ForceGenerator {
    private Vector2f gravity;

    public Gravity2D(Vector2f f) {
        if (gravity == null)
            gravity = new Vector2f();
        gravity.set(f);
    }

    @Override
    public void updateForce(RigidBody body, float dt) {
        //System.err.println("updForce" + body.getPosition() + " " + body.getInverseMass() + " " + gravity);
        body.addForce(new Vector2f(gravity).mul(body.getMass()));
    }

}
