package ru.vorazen.physics.primitives;

import ru.vorazen.physics.jmath.JMath;
import ru.vorazen.physics.jmath.Vector2f;
import ru.vorazen.physics.rigidbody.RigidBody;

public class Box2D {
    private Vector2f size = new Vector2f();
    private Vector2f halfsize = new Vector2f();
    private RigidBody rigidBody = null;

    public Box2D() {
        this.halfsize = new Vector2f(size).mul(0.5f);
    }

    public void setRigidBody(RigidBody rb) {
        rigidBody = rb;
    }

    public Box2D(Vector2f min, Vector2f max) {
        this.size = new Vector2f(max).sub(min);
        this.halfsize = new Vector2f(size).mul(0.5f);
        rigidBody = new RigidBody();
        rigidBody.setTransform(new Vector2f(max).add(min).mul(0.5f), 0);
    }

    public Vector2f getLocalMin() {
        return new Vector2f(this.rigidBody.getPosition()).sub(this.halfsize);
    }

    public Vector2f getLocalMax() {
        return new Vector2f(this.rigidBody.getPosition()).add(this.halfsize);
    }

    public Vector2f[] getVerticies() {
        Vector2f min = getLocalMin();
        Vector2f max = getLocalMax();
        Vector2f[] verticies = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y), new Vector2f(max.x, min.y),
                new Vector2f(max.x, max.y)

        };

        if (rigidBody.getRotation() != 0.0f) {
            for (Vector2f vert : verticies) {
                JMath.rotate(vert, this.rigidBody.getPosition(),
                        this.rigidBody.getRotation());
            }
        }
        return verticies;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public Vector2f getSize() {
        return this.size;
    }

    public Vector2f getHalfSize() {
        return halfsize;
    }

    public void setSize(Vector2f sz) {
        size = sz;
        halfsize.set(size.x / 2, size.y / 2);
    }
}
