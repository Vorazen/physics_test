package ru.vorazen.physics.primitives;

import ru.vorazen.physics.jmath.Vector2f;
import ru.vorazen.physics.rigidbody.RigidBody;

public class Circle extends Collider2D{
    private float radius = 1.0f;
    private RigidBody rigidBody = null;

    public void setRigidBody(RigidBody rb) {
        rigidBody = rb;
    }

    public void setRadius(float r) {
        radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public Vector2f getCenter() {
        return rigidBody.getPosition();
    }
}
