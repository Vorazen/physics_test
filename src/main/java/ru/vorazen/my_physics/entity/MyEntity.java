package ru.vorazen.my_physics.entity;

import java.awt.Graphics2D;

import ru.vorazen.my_physics.physengine.ExternalForces;
import ru.vorazen.my_physics.primitives.MyBox;
import ru.vorazen.my_physics.primitives.Primitive2D;

public class MyEntity {
    protected DrawableComponent DC;
    protected EntityAI AI;
    protected Primitive2D body;

    public void draw(Graphics2D g2, float dt) {
        DC.paintComponent(g2);
    }

    public void update(float dt) {
        body.getRigidBody2D().update(dt);
        if (AI != null)
            AI.changeSprite(DC, dt);
    }

    public MyEntity() {
        AI = null;
        body = new MyBox();
        DC = null;
    }

    public MyEntity(Primitive2D body, EntityAI AI, String path, int w, int h) {
        this.body = body;
        this.AI = AI;
        DC = new DrawableComponent(this.body, path, w, h);
    }

    public Primitive2D getPrimitive2d() {
        return body;
    }

    public void updateAIandForces(float dt) {
        ExternalForces.applyExternalForce(body);
        if (AI != null) {
            AI.applyInternalForce(body);
        }
    }
}
