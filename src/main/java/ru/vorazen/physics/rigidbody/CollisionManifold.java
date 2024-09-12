package ru.vorazen.physics.rigidbody;

import java.util.ArrayList;
import java.util.List;

import ru.vorazen.physics.jmath.Vector2f;

public class CollisionManifold {
    private Vector2f normal;
    private List<Vector2f> contactPoints;
    private float depth;
    private boolean isColliding;

    public CollisionManifold() {
        normal = new Vector2f();
        depth = 0.0f;
        isColliding = false;
    }

    public void addContactPoint(Vector2f point) {
        contactPoints.add(point);
    }

    public CollisionManifold(Vector2f normal, float depth) {
        this.normal = normal;
        contactPoints = new ArrayList<>();
        this.depth = depth;
        isColliding = true;
    }

    public List<Vector2f> getContactPoints() {
        return this.contactPoints;
    }

    public void setContactPoints(List<Vector2f> value) {
        this.contactPoints = value;
    }

    public float getDepth() {
        return this.depth;
    }

    public void setDepth(float value) {
        this.depth = value;
    }

    public Vector2f getNormal() {
        return this.normal;
    }

    public void setNormal(Vector2f value) {
        this.normal = value;
    }

    public boolean isColliding() {
        return isColliding;
    }
}
