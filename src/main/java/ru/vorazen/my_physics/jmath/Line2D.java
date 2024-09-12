package ru.vorazen.my_physics.jmath;

public class Line2D {
    private Vector2f from;
    private Vector2f to;
    private Vector3f color;
    private float transparency;

    public Line2D(Vector2f from, Vector2f to) {
        this.from = from;
        this.to = to;
    }

    public Line2D(Vector2f from, Vector2f to, Vector3f color, float tr) {
        this.from = from;
        this.to = to;
        this.color = color;
        transparency = tr;
    }

    public String toString() {
        return "from: " + from + " to: " + to;
    }

    public Vector2f getFrom() {
        return new Vector2f(from);
    }

    public Vector2f getTo() {
        return new Vector2f(to);
    }

    public Vector3f getColor() {
        return new Vector3f(color);
    }

    public float getTransparency() {
        return transparency;
    }

    public float lengthSquared() {
        return new Vector2f(to).sub(from).lenthSquared();
    }
}
