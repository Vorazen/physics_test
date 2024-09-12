package ru.vorazen.my_physics.jmath;

public class JMath {

    public static void rotate(Vector2f vec, Vector2f origin, float angleRadians) {
        float x = vec.x - origin.x;
        float y = vec.y - origin.x;
        float cos = (float) Math.cos(angleRadians);
        float sin = (float) Math.sin(angleRadians);
        float xPr = x * cos - y * sin;
        float yPr = x * sin + y * cos;
        xPr += origin.x;
        yPr += origin.y;
        vec.x = xPr;
        vec.y = yPr;
    }

    public static boolean compare(float x, float y) {
        return Math.abs(x - y) <= Float.MIN_VALUE * Math.max(1.0f, Math.max(Math.abs(x), Math.abs(y)));
    }

    public static boolean compare(Vector2f vec1, Vector2f vec2) {
        return compare(vec1.x, vec2.x) && compare(vec1.y, vec2.y);
    }

    public static boolean between(float x, float a, float b) {
       // System.out.println(x + " " + a + " " + b + " " +( a <= x && x <= b || a >= x && x >= b));
        return a <= x && x <= b || a >= x && x >= b;
    }
}
