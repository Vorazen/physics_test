package ru.vorazen.my_physics.primitives;

import ru.vorazen.my_physics.jmath.Vector2f;

public class MyCircle extends Primitive2D {
    private float radius;

    public MyCircle(RigidBody2D rb, float radius) {
        rigidBody2D = rb;
        this.radius = radius;
    }

    public MyCircle(Vector2f pos, float radius, float rotation, float scale) {
        rigidBody2D = new RigidBody2D();
        rigidBody2D.setPosition(new Vector2f(pos));
        rigidBody2D.setRotation(rotation);
        rigidBody2D.setScale(scale);
        rigidBody2D.setMass(1);
        rigidBody2D.setRadiusOfInertion(1);
        this.radius = radius;
    }

    public MyCircle() {
        this(new Vector2f(0, 0), 50, 0f, 1);
    }

    public float getRadius() {
        return this.radius * rigidBody2D.getScale();
    }

    public void setRadius(float value) {
        this.radius = value;
    }

    public Vector2f getCenter() {
        return rigidBody2D.getPosition();
    }
}
