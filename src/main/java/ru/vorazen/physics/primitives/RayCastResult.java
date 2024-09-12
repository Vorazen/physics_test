package ru.vorazen.physics.primitives;

import ru.vorazen.physics.jmath.Vector2f;

public class RayCastResult {
    private Vector2f point;
    private Vector2f normal;
    private float t;
    private boolean hit;

    public RayCastResult() {
        this.point = new Vector2f();
        this.normal = new Vector2f();
        this.t = -1;
        this.hit = false;
    }

    public void init(Vector2f point, Vector2f normal, float t, boolean hit) {
        this.point.set(point);
        this.normal.set(normal);
        this.t = -t;
        this.hit = hit;
    }

    public static void reset(RayCastResult res) {
        if (res != null) {
            res.point.zero();
            res.normal.set(0, 0);
            res.t = -1;
            res.hit = false;
        }
    }
}
