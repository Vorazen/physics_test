package ru.vorazen.my_physics.entitypattern;

import ru.vorazen.my_physics.entity.DrawableComponent;
import ru.vorazen.my_physics.entity.EntityAI;
import ru.vorazen.my_physics.entity.MyEntity;
import ru.vorazen.my_physics.jmath.Vector2f;
import ru.vorazen.my_physics.primitives.MyCircle;

public class CircleEntity extends MyEntity {
    public CircleEntity(float mass, float radius, Vector2f pos, float scale, float rot) {
        MyCircle circle = new MyCircle();
        circle.setRadius(radius);
        circle.getRigidBody2D().setPosition(new Vector2f(pos));
        circle.getRigidBody2D().setMass(mass);
        circle.getRigidBody2D().setRadiusOfInertion(radius);
        circle.getRigidBody2D().setRotation(rot);
        circle.getRigidBody2D().setScale(scale);
        body = circle;
    }

    public void setAI(EntityAI AI) {
        this.AI = AI;
    }

    public void setVel(Vector2f linVel, float angVel) {
        body.getRigidBody2D().setLinearVelocity(linVel);
        body.getRigidBody2D().setAngularVelocity(angVel);
    }
    public void setLosses(float linLoss, float angLoss) {
        body.getRigidBody2D().setAngularLosses(angLoss);
        body.getRigidBody2D().setLinearLosses(linLoss);
    }
    public void setLossesCoef(float linLoss, float angLoss) {
        body.getRigidBody2D().setAngularLossesCoef(angLoss);
        body.getRigidBody2D().setLinearLossesCoef(linLoss);
    }

    public void setDrawable(String path, int w, int h) {
        DC = new DrawableComponent(body, path, 64, 64);
    }
}
