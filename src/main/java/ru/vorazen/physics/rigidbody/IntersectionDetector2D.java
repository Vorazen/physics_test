package ru.vorazen.physics.rigidbody;

import ru.vorazen.physics.jmath.JMath;
import ru.vorazen.physics.jmath.Line2D;
import ru.vorazen.physics.jmath.Vector2f;
import ru.vorazen.physics.primitives.AABB;
import ru.vorazen.physics.primitives.Box2D;
import ru.vorazen.physics.primitives.Circle;
import ru.vorazen.physics.primitives.Ray2D;
import ru.vorazen.physics.primitives.RayCastResult;

public class IntersectionDetector2D {
    // ==============================
    // Point vs Primitive
    // ==============================
    public static int pointAboveLine(Vector2f point, Line2D line) {
        float dy = line.getTo().y - line.getFrom().y;
        float dx = line.getTo().x - line.getFrom().x;
        if (dx == 0) {
            if (Math.max(line.getFrom().y, line.getTo().y) < point.y)
                return 1;
            if (Math.min(line.getFrom().y, line.getTo().y) > point.y)
                return -1;
            return 0;
        }
        float m = dy / dx;
        float b = line.getFrom().y - (m * line.getFrom().x);
        if (point.y > m * point.x + b)
            return 1;
        if (point.y < m * point.x + b)
            return -1;
        return 0;
    }

    public static boolean pointOnLine(Vector2f point, Line2D line) {
        float dy = line.getTo().y - line.getFrom().y;
        float dx = line.getTo().x - line.getFrom().x;
        if (dx == 0) {
            return JMath.between(point.y, line.getFrom().y, line.getTo().y);
        }
        float m = dy / dx;
        float b = line.getFrom().y - (m * line.getFrom().x);
        return point.y == m * point.x + b;
    }

    public static boolean pointInCircle(Vector2f point, Circle circle) {
        Vector2f cCenter = circle.getCenter();
        Vector2f cenToPoint = new Vector2f(point).sub(cCenter);
        return cenToPoint.lenthSquared() <= circle.getRadius() * circle.getRadius();
    }

    public static boolean pointInAABB(Vector2f point, AABB box) {
        Vector2f min = box.getMin();
        Vector2f max = box.getMax();
        return min.x <= point.x && point.x <= max.x && min.y <= point.y && point.y <= max.y;
    }

    public static boolean pointInBox2D(Vector2f point, Box2D box) {
        Vector2f pointLocalBoxSpace = new Vector2f(point);

        JMath.rotate(pointLocalBoxSpace, box.getRigidBody().getPosition(), box.getRigidBody().getRotation());
        Vector2f min = box.getLocalMin();
        Vector2f max = box.getLocalMax();
        return min.x <= point.x && point.x <= max.x && min.y <= point.y && point.y <= max.y;

    }

    // ==============================
    // Line vs Primitive
    // ==============================
    public static boolean lineAndCircle(Line2D line, Circle circle) {
        if (pointInCircle(line.getTo(), circle) || pointInCircle(line.getFrom(), circle))
            return true;
        Vector2f ab = new Vector2f(line.getTo().sub(line.getFrom()));
        Vector2f cCen = circle.getCenter();
        Vector2f centerToLineStart = new Vector2f(cCen).sub(line.getFrom());
        float t = centerToLineStart.dot(ab) / ab.dot(ab);

        if (t < 0.0f || t > 1.0f) {
            return false;
        }
        Vector2f closest = new Vector2f(line.getFrom()).add(ab.mul(t));
        return pointInCircle(closest, circle);
    }

    public static boolean lineAndAABB(Line2D line, AABB box) {
        if (pointInAABB(line.getFrom(), box) || pointInAABB(line.getTo(), box))
            return true;
        Vector2f unitV = new Vector2f(line.getTo()).sub(line.getFrom());
        unitV.normalize();
        unitV.x = (unitV.x != 0) ? 1.0f / unitV.x : 0f;
        unitV.y = (unitV.y != 0) ? 1.0f / unitV.y : 0f;
        Vector2f min = box.getMin();
        min.sub(line.getFrom()).mul(unitV);
        Vector2f max = new Vector2f(box.getMax());
        max.sub(line.getFrom()).mul(unitV);
        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
        if (tmax < 0 || tmin > tmax)
            return false;
        float t = (tmin < 0) ? tmax : tmin;
        return t > 0 && t * t < line.lengthSquared();
    }

    public static boolean lineAndBox2D(Line2D line, Box2D box) {
        float theta = box.getRigidBody().getRotation();
        Vector2f center = box.getRigidBody().getPosition();
        Vector2f ls = new Vector2f(line.getFrom());
        Vector2f le = new Vector2f(line.getTo());
        JMath.rotate(ls, center, theta);
        JMath.rotate(le, center, theta);
        Line2D lLine = new Line2D(ls, le);
        AABB aabb = new AABB(box.getLocalMin(), box.getLocalMax());
        return lineAndAABB(lLine, aabb);
    }

    // RAYCASTYING
    public static boolean raycast(Circle circle, Ray2D ray, RayCastResult res) {
        RayCastResult.reset(res);
        Vector2f origintoCirc = new Vector2f(circle.getCenter()).sub(ray.getOrigin());

        float radSquar = circle.getRadius() * circle.getRadius();
        float orToCicSquar = origintoCirc.lenthSquared();

        float a = origintoCirc.dot(ray.getDirection());
        float bSq = orToCicSquar - a * a;
        if (radSquar - bSq < 0.0) {
            return false;
        }
        float f = (float) Math.sqrt(radSquar - bSq);
        float t = 0;
        if (orToCicSquar < radSquar) {
            t = a + f;
        } else {
            t = a - f;
        }
        if (res != null) {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection()).mul(t));
            Vector2f normal = new Vector2f(point).sub(circle.getCenter());
            normal.normalize();
            res.init(point, normal, t, true);
        }

        return true;
    }

    public static boolean raycast(AABB box, Ray2D ray, RayCastResult res) {
        RayCastResult.reset(res);
        Vector2f unitV = new Vector2f(ray.getDirection());
        unitV.normalize();
        unitV.x = (unitV.x != 0) ? 1.0f / unitV.x : 0f;
        unitV.y = (unitV.y != 0) ? 1.0f / unitV.y : 0f;
        Vector2f min = box.getMin();
        min.sub(ray.getOrigin()).mul(unitV);
        Vector2f max = new Vector2f(box.getMax());
        max.sub(ray.getOrigin()).mul(unitV);
        float tmin = Math.max(Math.min(min.x, max.x), Math.min(min.y, max.y));
        float tmax = Math.min(Math.max(min.x, max.x), Math.max(min.y, max.y));
        if (tmax < 0 || tmin > tmax)
            return false;
        float t = (tmin < 0) ? tmax : tmin;
        boolean hit = t > 0f;// && t * t < ray.getMaximum();
        if (!hit)
            return false;
        if (res != null) {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection()).mul(t));
            Vector2f normal = new Vector2f(ray.getOrigin()).sub(point);
            normal.normalize();
            res.init(point, normal, t, true);
        }
        return true;
    }

    public static boolean raycast(Box2D box, Ray2D ray, RayCastResult res) {
        RayCastResult.reset(res);
        Vector2f size = box.getSize();
        Vector2f xaxis = new Vector2f(1, 0);
        Vector2f yaxis = new Vector2f(0, 1);
        Vector2f zero = new Vector2f(0, 0);
        JMath.rotate(xaxis, zero, box.getRigidBody().getRotation());
        JMath.rotate(yaxis, zero, box.getRigidBody().getRotation());

        Vector2f p = new Vector2f(box.getRigidBody().getPosition()).sub(ray.getOrigin());
        Vector2f f = new Vector2f(xaxis.dot(ray.getDirection()), yaxis.dot(ray.getDirection()));
        Vector2f e = new Vector2f(xaxis.dot(p), yaxis.dot(p));
        float[] tArr = { 0, 0, 0, 0 };
        for (int i = 0; i < 2; i++) {
            if (JMath.compare(f.get(i), 0)) {
                // if parallel to axis and origin of the ray
                // is not inside then no hit
                if (e.get(i) - size.get(i) > 0 || -e.get(i) + size.get(i) < 0) {
                    return false;
                }
                f.setComponent(i, 0.000001f);
            }
            tArr[i * 2 + 0] = (e.get(i) + size.get(i)) / f.get(i);
            tArr[i * 2 + 1] = (e.get(i) - size.get(i)) / f.get(i);
        }
        float tmin = Math.max(Math.min(tArr[0], tArr[1]), Math.min(tArr[2], tArr[3]));
        float tmax = Math.min(Math.max(tArr[0], tArr[1]), Math.max(tArr[2], tArr[3]));
        if (tmax < 0 || tmin > tmax)
            return false;
        float t = (tmin < 0) ? tmax : tmin;
        boolean hit = t > 0f;// && t * t < ray.getMaximum();
        if (!hit)
            return false;
        if (res != null) {
            Vector2f point = new Vector2f(ray.getOrigin()).add(new Vector2f(ray.getDirection()).mul(t));
            Vector2f normal = new Vector2f(ray.getOrigin()).sub(point);
            normal.normalize();
            res.init(point, normal, t, true);
        }
        return true;
    }

    public static boolean circleAndLine(Circle circle, Line2D line) {
        return lineAndCircle(line, circle);
    }

    public static boolean circleAndCircle(Circle c1, Circle c2) {
        Vector2f b = new Vector2f(c1.getCenter()).sub(c2.getCenter());
        float radS = c1.getRadius() + c2.getRadius();
        return b.lenthSquared() <= radS * radS;
    }

    public static boolean circleAndAABB(Circle c1, AABB box) {
        Vector2f min = box.getMin();
        Vector2f max = box.getMax();
        Vector2f closest = new Vector2f(c1.getCenter());
        if (closest.x < min.x) {
            closest.x = min.x;
        } else if (closest.x > max.x) {
            closest.x = max.x;
        }
        if (closest.y < min.y) {
            closest.y = min.y;
        } else if (closest.y > max.y) {
            closest.y = max.y;
        }
        Vector2f circleToBox = new Vector2f(c1.getCenter()).sub(closest);
        return circleToBox.lenthSquared() <= c1.getRadius() * c1.getRadius();
    }

    public static boolean circleAndBox2D(Circle circle, Box2D box) {
        Vector2f min = box.getLocalMin();
        Vector2f max = box.getLocalMax();

        Vector2f r = new Vector2f(circle.getCenter()).sub(box.getRigidBody().getPosition());
        JMath.rotate(r, new Vector2f(0, 0), -box.getRigidBody().getRotation());

        Vector2f lcp = new Vector2f(r).add(box.getHalfSize());

        Vector2f closest = new Vector2f(lcp);
        if (closest.x < min.x) {
            closest.x = min.x;
        } else if (closest.x > max.x) {
            closest.x = max.x;
        }
        if (closest.y < min.y) {
            closest.y = min.y;
        } else if (closest.y > max.y) {
            closest.y = max.y;
        }
        Vector2f circleToBox = new Vector2f(lcp).sub(closest);
        return circleToBox.lenthSquared() <= circle.getRadius() * circle.getRadius();
    }

    public static boolean AABBAndCircle(Circle circle, AABB box) {
        return circleAndAABB(circle, box);
    }

    public static boolean AABBAndAABB(AABB b1, AABB b2) {
        Vector2f axesToTest[] = { new Vector2f(1, 0), new Vector2f(0, 1) };
        for (int i = 0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(b1, b2, axesToTest[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean AABBAndBox2D(AABB b1, Box2D b2) {
        Vector2f axesToTest[] = { new Vector2f(1, 0), new Vector2f(0, 1), new Vector2f(1, 0), new Vector2f(0, 1) };

        JMath.rotate(axesToTest[2], new Vector2f(0, 0), b2.getRigidBody().getRotation());
        JMath.rotate(axesToTest[3], new Vector2f(0, 0), b2.getRigidBody().getRotation());

        for (int i = 0; i < axesToTest.length; i++) {
            if (!overlapOnAxis(b1, b2, axesToTest[i])) {
                return false;
            }
        }
        return true;
    }

    // ======================
    // SAT helpers
    // ======================
    private static Vector2f getInterval(AABB rect, Vector2f axis) {
        Vector2f res = new Vector2f(0, 0);
        Vector2f min = rect.getMin();
        Vector2f max = rect.getMax();
        Vector2f[] verticies = {
                new Vector2f(min.x, min.y), new Vector2f(min.x, max.y), new Vector2f(max.x, min.y),
                new Vector2f(max.x, max.y)

        };
        res.x = axis.dot(verticies[0]);
        res.y = res.x;
        for (int i = 1; i < 4; i++) {
            float pr = axis.dot(verticies[i]);
            if (pr < res.x) {
                res.x = pr;
            }
            if (pr > res.y) {
                res.y = pr;
            }
        }

        return res;
    }

    private static Vector2f getInterval(Box2D rect, Vector2f axis) {
        Vector2f res = new Vector2f(0, 0);
        Vector2f[] verticies = rect.getVerticies();
        res.x = axis.dot(verticies[0]);
        res.y = res.x;
        for (int i = 1; i < 4; i++) {
            float pr = axis.dot(verticies[i]);
            if (pr < res.x) {
                res.x = pr;
            }
            if (pr > res.y) {
                res.y = pr;
            }
        }

        return res;
    }

    private static boolean overlapOnAxis(AABB b1, AABB b2, Vector2f axis) {
        Vector2f int1 = getInterval(b1, axis);
        Vector2f int2 = getInterval(b2, axis);
        return int2.x <= int1.y && int1.x <= int2.y;
    }

    private static boolean overlapOnAxis(AABB b1, Box2D b2, Vector2f axis) {
        Vector2f int1 = getInterval(b1, axis);
        Vector2f int2 = getInterval(b2, axis);
        return int2.x <= int1.y && int1.x <= int2.y;
    }

    private static boolean overlapOnAxis(Box2D b1, Box2D b2, Vector2f axis) {
        Vector2f int1 = getInterval(b1, axis);
        Vector2f int2 = getInterval(b2, axis);
        return int2.x <= int1.y && int1.x <= int2.y;
    }
}
