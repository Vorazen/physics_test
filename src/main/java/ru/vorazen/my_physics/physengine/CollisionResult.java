package ru.vorazen.my_physics.physengine;

import java.util.ArrayList;
import java.util.List;

import ru.vorazen.my_physics.jmath.Vector2f;

public class CollisionResult {
    private Vector2f normal;
    private List<Vector2f> points;

    public void setNormal(Vector2f normal) {
        this.normal = normal;
    }

    public void addPoint(Vector2f vector2f) {
        if (points == null)
            points = new ArrayList<>();
        points.add(vector2f);
    }

    public Vector2f getNormal() {
        return normal;
    }

    public List<Vector2f> getContactPoints() {
        return points;
    }

}
