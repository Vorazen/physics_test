package ru.vorazen.my_physics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import ru.vorazen.my_physics.entity.MyEntity;
import ru.vorazen.my_physics.entitypattern.CircleEntity;
import ru.vorazen.my_physics.jmath.Vector2f;
import ru.vorazen.my_physics.physengine.ExternalForces;
import ru.vorazen.my_physics.physengine.PhysicsSystem;

public class MyPanel extends JPanel implements Runnable {
    final int FPS = 60;
    Thread gameThread;

    PhysicsSystem physicsSystem;
    List<MyEntity> entities;

    public MyPanel() {
        this.setPreferredSize(new Dimension(600, 500));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        physicsSystem = new PhysicsSystem();
        entities = new ArrayList<>();
        //
        CircleEntity c1 = new CircleEntity(0, 32, new Vector2f(200, 400), 2, 0);
        c1.setDrawable("/blocks/wheel.png", 64, 64);
        c1.getPrimitive2d().getRigidBody2D().setRadiusOfInertion(0);
        entities.add(c1);
        CircleEntity c3 = new CircleEntity(0, 32, new Vector2f(50, 200), 1, 0);
        c3.setDrawable("/blocks/wheel.png", 64, 64);
        c3.getPrimitive2d().getRigidBody2D().setRadiusOfInertion(0.1f);
        c3.setLosses(0, 0.01f);
        entities.add(c3);
        CircleEntity c2 = new CircleEntity(1, 32, new Vector2f(101, 000), 1, 0);
        c2.setDrawable("/blocks/wheel.png", 64, 64);
        c2.setLossesCoef(0.5f, 0f);
        c2.setLosses(0.005f, 0.01f);
        c2.getPrimitive2d().getRigidBody2D().setRadiusOfInertion(0.5f);
        entities.add(c2);

        //

        physicsSystem.setEntities(entities);
        ExternalForces.setGravity(new Vector2f(0, 500));
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1) {

                // UPDATE
                update(1.0f / FPS);

                // DRAW
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(float dt) {
        physicsSystem.update(dt);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (MyEntity myEntity : entities) {
            myEntity.draw(g2, 1 / FPS);
        }
    }
}
