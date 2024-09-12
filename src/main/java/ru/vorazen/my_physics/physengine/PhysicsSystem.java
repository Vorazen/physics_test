package ru.vorazen.my_physics.physengine;

import java.util.ArrayList;
import java.util.List;

import ru.vorazen.my_physics.entity.MyEntity;

public class PhysicsSystem {
    private List<MyEntity> entities;
    private List<MyEntity> ent1;
    private List<MyEntity> ent2;
    private List<CollisionResult> collisionResults;
    private int impulseIterations = 6;

    public PhysicsSystem() {
        entities = new ArrayList<>();
        ent1 = new ArrayList<>();
        ent2 = new ArrayList<>();
        collisionResults = new ArrayList<>();
    }

    public void update(float dt) {

        // clear lists
        ent1.clear();
        ent2.clear();
        collisionResults.clear();
        int sz = entities.size();
        // update forces and velocities using AI (for internal) and ForceGenerator (for
        // external)

        for (MyEntity myEntity : entities) {
            myEntity.updateAIandForces(dt);
        }
        // find collisions
        for (int i = 0; i < sz; i++) {
            for (int j = 0; j < sz; j++) {
                if (i == j)
                    continue;
                MyEntity e1 = entities.get(i);
                MyEntity e2 = entities.get(j);
                CollisionResult cr = MyCollisionChecker.checkCollision(e1, e2);
                if (cr != null) {
                    ent1.add(e1);
                    ent2.add(e2);
                    collisionResults.add(cr);
                }
            }
        }

        // resolve collisions
        //System.out.println("Resolve collisions");
        for (int k = 0; k < impulseIterations; k++) {
            for (int i = 0; i < collisionResults.size(); i++) {
                //System.out.println(collisionResults.size());
                // System.err.println(i + " " + bodies1.size()+);
                ImpulseResolver.resolve(ent1.get(i), ent2.get(i), collisionResults.get(i));
                // applyImpulse(r1, r2, collisionResults.get(i));

            }
        }
        // move entities
        for (MyEntity myEntity : entities) {
            myEntity.update(dt);
        }
    }

    public List<MyEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<MyEntity> entities) {
        this.entities = entities;
    }

    public void addEntity(MyEntity ent) {
        if (entities == null)
            entities = new ArrayList<>();
        entities.add(ent);
    }

    public void deleteEntity(int ID) {
        if (entities == null)
            return;
        if (ID < 0 || ID > entities.size())
            return;
        entities.remove(ID);
    }

    public void deleteEntity(MyEntity ent) {
        if (entities == null)
            return;
        entities.remove(ent);
    }

    public void clearEntities() {
        if (entities == null)
            entities = new ArrayList<>();
        entities.clear();
    }
}
