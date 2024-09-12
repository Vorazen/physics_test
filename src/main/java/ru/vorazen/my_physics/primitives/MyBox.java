package ru.vorazen.my_physics.primitives;

import ru.vorazen.my_physics.jmath.JMath;
import ru.vorazen.my_physics.jmath.Vector2f;

public class MyBox extends Primitive2D {
    private Vector2f size;
    private Vector2f halfsize;

    public MyBox(RigidBody2D rb, Vector2f size) {
        rigidBody2D = rb;
        setSize(size);
    }

    public MyBox(Vector2f pos, Vector2f size, float rotation, float scale) {
        rigidBody2D = new RigidBody2D();
        rigidBody2D.setPosition(new Vector2f(pos));
        rigidBody2D.setRotation(rotation);
        rigidBody2D.setScale(scale);
        setSize(size);
        rigidBody2D.setMass(1);
        rigidBody2D.setRadiusOfInertion(1);
    }

    public MyBox() {
        this(new Vector2f(0, 0), new Vector2f(50, 50), 0f, 1);
    }

    public Vector2f getLocalMin() {
        return new Vector2f(rigidBody2D.getPosition()).sub(this.halfsize);
    }

    public Vector2f getLocalMax() {
        return new Vector2f(rigidBody2D.getPosition()).add(this.halfsize);
    }

    public Vector2f[] getVerticies() {
        Vector2f min = getLocalMin();
        Vector2f max = getLocalMax();
        Vector2f[] verticies = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y), new Vector2f(max.x, min.y),
                new Vector2f(max.x, max.y)

        };

        if (rigidBody2D.getRotation() != 0.0f) {
            for (Vector2f vert : verticies) {
                JMath.rotate(vert, this.rigidBody2D.getPosition(),
                        this.rigidBody2D.getRotation());
            }
        }
        return verticies;
    }

    public RigidBody2D getRigidBody() {
        return rigidBody2D;
    }

    public Vector2f getSize() {
        return this.size;
    }

    public Vector2f getHalfSize() {
        return halfsize;
    }

    public void setSize(Vector2f sz) {
        size = new Vector2f(sz).mul(rigidBody2D.getScale());
        if (halfsize == null)
            halfsize = new Vector2f();
        halfsize.set(size.x / 2, size.y / 2);
    }
}
