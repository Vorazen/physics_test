package ru.vorazen.physics.forces;

import ru.vorazen.physics.rigidbody.RigidBody;

public class ForceRegistration {
    public ForceGenerator fg = null;
    public RigidBody rb = null;

    public ForceRegistration(ForceGenerator fg, RigidBody rb) {
        this.fg = fg;
        this.rb = rb;
    }

    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other.getClass() != ForceRegistration.class)
            return false;
        ForceRegistration fr = (ForceRegistration) other;
        return fr.rb == this.rb && fr.fg == this.fg;
    }
}
