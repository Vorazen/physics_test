package ru.vorazen.physics.forces;

import java.util.ArrayList;
import java.util.List;

import ru.vorazen.physics.rigidbody.RigidBody;

public class ForceRegistry {
    private List<ForceRegistration> registry;

    public ForceRegistry() {
        registry = new ArrayList<>();
    }

    public void add(RigidBody rb, ForceGenerator rg) {
        ForceRegistration fr = new ForceRegistration(rg, rb);
        registry.add(fr);
    }

    public void remove(RigidBody rb, ForceGenerator fg) {
        ForceRegistration fr = new ForceRegistration(fg, rb);
        registry.remove(fr);
    }

    public void clear() {
        registry.clear();
    }

    public void updateForces(float dt) {
        for (ForceRegistration fr : registry) {
            fr.fg.updateForce(fr.rb, dt);
        }
    }

    public void zeroForces() {
        //TODO
        // fr.rb.zeroForces();
    }
}
