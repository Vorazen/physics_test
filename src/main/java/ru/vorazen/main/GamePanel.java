package ru.vorazen.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int FPS = 60;
    public World world;
    public InputHandler IH = new InputHandler();
    Thread gameThread;

    public GamePanel() {
        this.world = new World(this);
        System.out.println(world.Xscale + " " + world.Yscale);
        System.out.println(Main.window.getSize());
        this.setPreferredSize(new Dimension(world.screenWidth, world.screenHeight));
        System.out.println(Main.window.getSize());
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addKeyListener(IH);
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
                System.out.println(world.Xscale + " " + world.Yscale);
                System.out.println(world.screenWidth + " " + world.screenHeight);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        world.update(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        world.draw(g2, this);
        g2.dispose(); // free memory
    }
}
