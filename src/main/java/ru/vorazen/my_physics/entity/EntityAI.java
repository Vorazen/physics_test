package ru.vorazen.my_physics.entity;

import ru.vorazen.my_physics.primitives.Primitive2D;

public interface EntityAI {

    void applyInternalForce(Primitive2D body);

    void applyInternalVel(Primitive2D body);

    void changeSprite(DrawableComponent dC, float dt);

}
