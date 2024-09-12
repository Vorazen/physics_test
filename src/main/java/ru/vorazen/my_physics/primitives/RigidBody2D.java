package ru.vorazen.my_physics.primitives;

import ru.vorazen.my_physics.jmath.Vector2f;

public class RigidBody2D {
    private float scale = 1.0f;
    private Vector2f position;
    private Vector2f LinearVelocity = new Vector2f();
    private Vector2f accumForce = new Vector2f();
    private Vector2f ignoreForce = new Vector2f();

    private float rotation;
    private float angularVelocity;
    private float angularForce;

    private float mass;
    private float inverseMass;

    private float radiusOfInertion;
    private float inverseradiusOfInertion;

    private boolean fixedRotation;
    private float invScale;
    private float linearLossesCoef = 0;
    private float angularLossesCoef = 0;
    private float linearLosses = 0;
    private float angularLosses = 0;

    public void update(float dt) {
        // System.out.println("update"+dt);
        if (this.mass != 0.0f) {
            Vector2f acceler = accumForce.add(new Vector2f(ignoreForce).mul(-new Vector2f(accumForce).dot(ignoreForce)))
                    .mul(this.inverseMass);
            LinearVelocity.add(acceler.mul(dt));
            // System.out.println("ig"+ignoreForce);
            // System.err.println("changed vel" + position);
        }
        ignoreForce.zero();
        if (LinearVelocity.lenthSquared() < 1f)
            LinearVelocity.zero();
        LinearVelocity.mul(1 - linearLosses);
        this.position.add(new Vector2f(LinearVelocity).mul(dt));
        clearAccumulators();

        if (radiusOfInertion != 0.0f) {
            float angAc = angularForce * inverseradiusOfInertion * inverseMass * invScale;
            angularVelocity += angAc * dt;
            // System.err.println("changed rot " + rotation);

        }
        angularVelocity *= 1 - angularLosses;
        if (Math.abs(angularVelocity) < 0.01f)
            angularVelocity = 0;
        rotation += angularVelocity * dt;
        clearAngularAccumulators();

    }

    private void clearAngularAccumulators() {
        angularForce = 0;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public void setPosition(Vector2f value) {
        this.position = value;
    }

    public Vector2f getLinearVelocity() {
        return this.LinearVelocity;
    }

    public void setLinearVelocity(Vector2f value) {
        this.LinearVelocity = value;
    }

    public void addLinearVelocity(Vector2f value) {
        this.LinearVelocity.add(value);
    }

    public Vector2f getAccumForce() {
        return this.accumForce;
    }

    public void addAccumForce(Vector2f value) {
        this.accumForce.add(value);
    }

    private void clearAccumulators() {
        accumForce.zero();
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float value) {
        this.rotation = value;
    }

    public void addRotation(float value) {
        this.rotation += value;
    }

    public float getAngularVelocity() {
        return this.angularVelocity;
    }

    public void setAngularVelocity(float value) {
        this.angularVelocity = value;
    }

    public float getAngularForce() {
        return this.angularForce;
    }

    public void addAngularForce(float value) {
        this.angularForce += value;
    }

    public float getMass() {
        return this.mass;
    }

    public void setMass(float value) {
        this.mass = value;
        if (mass != 0)
            inverseMass = 1 / mass;
    }

    public float getInverseMass() {
        return this.inverseMass;
    }

    public void setInverseMass(float value) {
        this.inverseMass = value;
        if (inverseMass != 0)
            mass = 1 / inverseMass;
    }

    public float getRadiusOfInertion() {
        return this.radiusOfInertion;
    }

    public void setRadiusOfInertion(float value) {
        this.radiusOfInertion = value;
        if (value != 0)
            inverseradiusOfInertion = 1 / value;
    }

    public float getInverseradiusOfInertion() {
        return this.inverseradiusOfInertion;
    }

    public void setInverseradiusOfInertion(float value) {
        this.inverseradiusOfInertion = value;
        if (value != 0)
            radiusOfInertion = 1 / value;
    }

    public boolean getFixedRotation() {
        return this.fixedRotation;
    }

    public void setFixedRotation(boolean value) {
        this.fixedRotation = value;
    }

    public boolean getInfiniteMass() {
        return mass == 0 || inverseMass == 0;
    }

    public float getLinearLossesCoef() {
        return this.linearLossesCoef;
    }

    public void setLinearLossesCoef(float value) {
        this.linearLossesCoef = value;
    }

    public float getAngularLossesCoef() {
        return this.angularLossesCoef;
    }

    public void setAngularLossesCoef(float value) {
        this.angularLossesCoef = value;
    }

    public boolean getInfiniteJ() {
        return radiusOfInertion == 0 || inverseradiusOfInertion == 0;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float value) {
        this.scale = value;
        invScale = 1 / value;
    }

    public Vector2f getIgnoreForce() {
        return this.ignoreForce;
    }

    public void setIgnoreForce(Vector2f value) {
        this.ignoreForce = value;
    }

    public float getLinearLosses() {
      return this.linearLosses;
    }
    public void setLinearLosses(float value) {
      this.linearLosses = value;
    }

    public float getAngularLosses() {
      return this.angularLosses;
    }
    public void setAngularLosses(float value) {
      this.angularLosses = value;
    }
}
