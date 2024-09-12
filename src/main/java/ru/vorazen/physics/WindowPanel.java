package ru.vorazen.physics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

//import ru.vorazen.physics.jmath.Transform;
import ru.vorazen.physics.jmath.Vector2f;
import ru.vorazen.physics.primitives.Circle;
import ru.vorazen.physics.rigidbody.RigidBody;

public class WindowPanel extends JPanel implements Runnable {
    final int FPS = 60;
    Thread gameThread;
    PhysicsSystem physicsSystem = new PhysicsSystem(1.0f / FPS, new Vector2f(0, 100));
    // Transform obj1, obj2;
    RigidBody rb1, rb2;

    public WindowPanel() {
        this.setPreferredSize(new Dimension(1200, 760));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        // obj1 = new Transform(new Vector2f(100, 100));
        // obj2 = new Transform(new Vector2f(100, 400));
        rb1 = new RigidBody();
        rb2 = new RigidBody();
        rb1.setRotation(0.7f);
        rb2.setRotation(0.3f);
        rb1.setTransform(new Vector2f(100, 100), 0.25f);
        rb2.setTransform(new Vector2f(100, 400), 0.185f);
        // rb1.setRawTransform(obj1);
        // rb2.setRawTransform(obj2);
        rb1.setMass(1f);
        rb2.setMass(00.f);
        rb1.setCor(0.1f);
        Circle c1 = new Circle();
        c1.setRadius(10);
        c1.setRigidBody(rb1);
        Circle c2 = new Circle();
        c2.setRadius(10);
        c2.setRigidBody(rb2);
        rb1.setCollider(c1);
        rb2.setCollider(c2);
        physicsSystem.addRigidBody(rb1, true);
        physicsSystem.addRigidBody(rb2, false);
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
                update();
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

    public void update() {
        physicsSystem.fixedUpdate();
        // System.out.println("upd: "+rb1.getPosition()+" "+rb2.getPosition()+"
        // "+rb1.getLinearVelocity()+" "+rb2.getLinearVelocity());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE);
        Rectangle rect = new Rectangle((int) rb1.getPosition().x, (int) rb1.getPosition().y, 20, 20);
        // System.out.println(rect);
        g2d.rotate(rb1.getRotation(), rect.x + rect.width / 2, rect.y + rect.height / 2);
        g2d.draw(rect);
        g2d.fill(rect);
        g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE);
        // System.out.println("draw: "+rb1.getPosition()+" "+rb2.getPosition()+"
        // "+rb1.getLinearVelocity()+" "+rb2.getLinearVelocity());
        Rectangle rect2 = new Rectangle((int) rb2.getPosition().x, (int) rb2.getPosition().y, 20, 20);
        // System.out.println(rect2+" "+rb2.getPosition());
        g2d.rotate(rb2.getRotation(), rect2.x + rect2.width / 2, rect2.y + rect2.height / 2);
        g2d.draw(rect2);
        g2d.fill(rect2);
        g2d.dispose(); // free memory
        // System.out.println(rb1.getPosition()+" "+rb2.getPosition());
    }
}
