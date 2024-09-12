package ru.vorazen.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ru.vorazen.main.GamePanel;
import ru.vorazen.main.World;

public abstract class SuperEntity {
    public int worldX = 0; // координата X
    public int worldY = 0; // координата Y
    public double angle = 0; // угол поворота
    public double angleSpeed = 0; // скорость поворота
    public double speedX = 0; // скорость по X
    public double speedY = 0; // скорость по Y
    public double angleAccel = 0; // угловое ускорение
    public double accelX = 0; // ускорение по X
    public double accelY = 0; // ускорение по Y

    public double intSpeedX = 0;
    public double intSpeedY = 0;
    public double intAccelX = 0;
    public double intAccelY = 0;
    public double intAngleSpeed = 0;
    public double intAngleAccel = 0;

    public double mass = 1; // масса
    public double J = 1; // момент инерции
    public double friction = 0; // коэффициент трения
    public double losses = 1; // коэффициент потерь
    public boolean movable = false; // может двигаться

    public BufferedImage image = null; // спрайт
    public String name = null; // название
    public int YSpriteSize = 16;
    public int XSpriteSize = 16;

    public int collisionWidth = 14;
    public int collisionHeight = 14;
    public boolean collision = true; // коллизия

    public abstract void draw(Graphics2D g2, World world);

    public abstract void update(GamePanel gp);
}
