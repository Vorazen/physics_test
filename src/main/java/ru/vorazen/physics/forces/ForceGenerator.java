package ru.vorazen.physics.forces;

import ru.vorazen.physics.rigidbody.RigidBody;

public interface ForceGenerator {
    void updateForce(RigidBody body, float dt);
}
