package ru.vorazen.physics.primitives;

import ru.vorazen.physics.jmath.Vector2f;
import ru.vorazen.physics.rigidbody.RigidBody;

public class AABB {
    private Vector2f size = new Vector2f();
    private Vector2f halfsize = new Vector2f();
    private RigidBody rigidBody = null;

    public AABB(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max).sub(min);
        this.halfsize = new Vector2f(size).mul(0.5f);
        rigidBody = new RigidBody();
        rigidBody.setTransform(new Vector2f(max).add(min).mul(0.5f), 0);
    }

    public void setRigidBody(RigidBody rb) {
        rigidBody = rb;
    }

    public Vector2f getMin() {
        return new Vector2f(this.rigidBody.getPosition()).sub(this.halfsize);
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public Vector2f getMax() {
        return new Vector2f(this.rigidBody.getPosition()).add(this.halfsize);
    }

    public Vector2f getSize() {
        return size;
    }

    public void setSize(Vector2f sz) {
        size = sz;
        halfsize.set(size.x / 2, size.y / 2);
    }
}
