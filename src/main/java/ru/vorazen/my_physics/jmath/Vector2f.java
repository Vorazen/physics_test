package ru.vorazen.my_physics.jmath;

public class Vector2f {
    public float x;
    public float y;

    public String toString() {
        return "x: " + String.valueOf(x) + " y: " + String.valueOf(y);
    }

    public Vector2f(Vector2f v) {
        x = v.x;
        y = v.y;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f() {
        x = 0;
        y = 0;
    }

    public Vector2f(float i) {
        x = i;
        y = i;
    }

    public Vector2f sub(Vector2f v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vector2f add(Vector2f v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector2f mul(float d) {
        x *= d;
        y *= d;
        return this;
    }

    public float lenthSquared() {
        return x * x + y * y;
    }

    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    public Vector2f normalize() {
        float l = (float) Math.sqrt(lenthSquared());
        x = x / l;
        y = y / l;
        return this;
    }

    public Vector2f mul(Vector2f v) {
        x *= v.x;
        y *= v.y;
        return this;
    }

    public void set(Vector2f v) {
        x = v.x;
        y = v.y;
    }

    public void zero() {
        x = 0;
        y = 0;
    }

    public void set(float i, float j) {
        x = i;
        y = j;
    }

    public float get(int i) {
        if (i == 0)
            return x;
        else
            return y;
    }

    public void setComponent(int i, float f) {
        if (i == 0)
            x = f;
        else
            y = f;
    }

    public float legnth() {
        return (float) Math.sqrt(lenthSquared());
    }

    public boolean isZero() {
       return x==0&&y==0;
    }

}
