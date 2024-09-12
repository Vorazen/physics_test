package ru.vorazen.physics.jmath;

public class Vector3f {

    public float x;
    public float y;
    public float z;

    public Vector3f(Vector3f v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3f sub(Vector3f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    public Vector3f add(Vector3f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public Vector3f mul(float d) {
        x *= d;
        y *= d;
        z *= d;
        return this;
    }

}
