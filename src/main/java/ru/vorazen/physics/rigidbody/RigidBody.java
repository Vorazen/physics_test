package ru.vorazen.physics.rigidbody;

import ru.vorazen.physics.jmath.Vector2f;
import ru.vorazen.physics.primitives.Collider2D;

public class RigidBody {
    //private Transform rawTransform;
    private Vector2f position = new Vector2f();
    private float rotation = 0;
    private Vector2f linearVelocity = new Vector2f();
    private float angularVelocity = 0.0f;
    private Vector2f linearDamping = new Vector2f();
    private float angularDamping = 0.0f;
    private boolean fixedRotation = false;
    private float mass = 0.0f;
    private float inverseMass = 0.0f;
    private Vector2f forceAccum = new Vector2f();
    private Collider2D collider;
    private float cor = 1.0f;

    public void physicsUpdate(float dt) {
        if (this.mass == 0.0f)
            return;
        // System.err.println("update"+forceAccum+" "+inverseMass);
        Vector2f acceler = new Vector2f(forceAccum).mul(this.inverseMass);
        linearVelocity.add(acceler.mul(dt));
        this.position.add(new Vector2f(linearVelocity).mul(dt));
       // synchCollsiionTransforms();
        clearAccumulators();

    }

   /*  public void synchCollsiionTransforms() {
        if (rawTransform != null) {
            rawTransform.position.set(this.position);
        }
    } */

    public Vector2f getAccum() {
        return forceAccum;
    }

    public void addForce(Vector2f f) {
        // System.err.println("add"+f);
        forceAccum.add(f);
    }

    private void clearAccumulators() {
        forceAccum.zero();
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public void setTransform(Vector2f pos, float f) {
        rotation = f;
        position = pos;
    }

    public void setTransform(Vector2f pos) {
        position = pos;
    }

    public float getMass() {
        return mass;
    }

    public float getInverseMass() {
        return inverseMass;
    }

    public void setMass(float m) {
        mass = m;
        if (m != 0.0f) {
            inverseMass = 1 / m;
        }
    }

    public void setInverseMass(float im) {
        inverseMass = im;
    }

    public void setRotation(float r) {
        rotation = r;
    }

    public boolean hasInfiniteMass() {
        return this.mass == 0.0f;
    }

 /*    public void setRawTransform(Transform tr) {
        rawTransform = tr;
        position.set(tr.position);
    }
 */
    public void setCollider(Collider2D coll) {
        collider = coll;
    }

    public Collider2D getCollider() {
        return collider;
    }

    public Vector2f getLinearVelocity() {
        return this.linearVelocity;
    }

    public void setLinearVelocity(Vector2f value) {
        this.linearVelocity = value;
    }

    public float getAngularVelocity() {
        return this.angularVelocity;
    }

    public void setAngularVelocity(float value) {
        this.angularVelocity = value;
    }

    public Vector2f getLinearDamping() {
        return this.linearDamping;
    }

    public void setLinearDamping(Vector2f value) {
        this.linearDamping = value;
    }

    public float getAngularDamping() {
        return this.angularDamping;
    }

    public void setAngularDamping(float value) {
        this.angularDamping = value;
    }

    public boolean getFixedRotation() {
        return this.fixedRotation;
    }

    public void setFixedRotation(boolean value) {
        this.fixedRotation = value;
    }

    public float getCor() {
        return this.cor;
    }

    public void setCor(float value) {
        this.cor = value;
    }
}
