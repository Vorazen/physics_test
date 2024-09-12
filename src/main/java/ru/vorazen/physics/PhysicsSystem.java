package ru.vorazen.physics;

import java.util.ArrayList;
import java.util.List;

import ru.vorazen.physics.forces.ForceRegistry;
import ru.vorazen.physics.forces.Gravity2D;
import ru.vorazen.physics.jmath.Vector2f;
import ru.vorazen.physics.primitives.Collider2D;
import ru.vorazen.physics.rigidbody.CollisionManifold;
import ru.vorazen.physics.rigidbody.Collisions;
import ru.vorazen.physics.rigidbody.RigidBody;

public class PhysicsSystem {
    private ForceRegistry forceRegistry;
    private List<RigidBody> rigidbodies;
    private Gravity2D gravity;
    private float fixedUpdate;
    private List<RigidBody> bodies1;
    private List<RigidBody> bodies2;
    private List<CollisionManifold> collisions;
    private int impulseIterations = 6;

    public PhysicsSystem(float fixedUpdateDt, Vector2f grav) {
        forceRegistry = new ForceRegistry();
        rigidbodies = new ArrayList<>();
        gravity = new Gravity2D(grav);
        bodies1 = new ArrayList<>();
        bodies2 = new ArrayList<>();
        collisions = new ArrayList<>();
        fixedUpdate = fixedUpdateDt;
    }

    public void update(float dt) {

    }

    public void fixedUpdate() {
        bodies1.clear();
        bodies2.clear();
        collisions.clear();

        // find collisiobs
        int size = rigidbodies.size();
        // System.out.println("Start update for " + size + " bodies");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j)
                    continue;
                CollisionManifold res = new CollisionManifold();
                RigidBody r1 = rigidbodies.get(i);
                RigidBody r2 = rigidbodies.get(j);
                Collider2D c1 = r1.getCollider();
                Collider2D c2 = r2.getCollider();

                if (c1 != null && c2 != null && !(r1.hasInfiniteMass() && r2.hasInfiniteMass())) {
                    // System.err.println("no colliders or infinite mass");
                    res = Collisions.findCollisionFeatures(c1, c2);
                }
                if (res != null && res.isColliding()) {
                    System.err.println("Colided");
                    bodies1.add(r1);
                    bodies2.add(r2);
                    collisions.add(res);
                }
            }
        }
        // update forces
        forceRegistry.updateForces(fixedUpdate);
        // resolve collisions
       // System.out.println(collisions.size() + bodies1.size() + bodies2.size());
        for (int k = 0; k < impulseIterations; k++) {
            for (int i = 0; i < collisions.size(); i++) {
                int jSize = collisions.get(i).getContactPoints().size();
                // System.out.println(jSize);
                for (int j = 0; j < jSize; j++) {
                  //  System.err.println(i + " " + bodies1.size()+);
                    RigidBody r1 = bodies1.get(i);
                    RigidBody r2 = bodies2.get(i);
                    applyImpulse(r1, r2, collisions.get(i));
                }
            }
        }
        // update velocities
        for (int i = 0; i < rigidbodies.size(); i++) {
            rigidbodies.get(i).physicsUpdate(fixedUpdate);

        }
    }

    private void applyImpulse(RigidBody a, RigidBody b, CollisionManifold m) {
        // Linear velocity
        float invMass1 = a.getInverseMass();
        float invMass2 = b.getInverseMass();
        float invMassSum = invMass1 + invMass2;
        if (invMassSum == 0f) {
            return;
        }
       // System.out.println(a + " " + b);
        // Relative velocity
        Vector2f relativeVel = new Vector2f(b.getLinearVelocity()).sub(a.getLinearVelocity());
        Vector2f relativeNormal = new Vector2f(m.getNormal()).normalize();
        // Moving away from each other? Do nothing
        if (relativeVel.dot(relativeNormal) > 0.0f) {
            return;
        }

        float e = Math.min(a.getCor(), b.getCor());
        float numerator = (-(1.0f + e) * relativeVel.dot(relativeNormal));
        float j = numerator / invMassSum;
        if (m.getContactPoints().size() > 0 && j != 0.0f) {
            j /= (float) m.getContactPoints().size();
        }

        Vector2f impulse = new Vector2f(relativeNormal).mul(j);
        System.out.println(impulse);
        a.setLinearVelocity(
                new Vector2f(a.getLinearVelocity()).add(new Vector2f(impulse).mul(invMass1).mul(-1f)));
        b.setLinearVelocity(
                new Vector2f(b.getLinearVelocity()).add(new Vector2f(impulse).mul(invMass2).mul(1f)));
    }

    public void addRigidBody(RigidBody body, boolean addGravity) {
        rigidbodies.add(body);
        if (addGravity)
            this.forceRegistry.add(body, gravity);
    }
}
