package ru.vorazen.entity;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ru.vorazen.main.GamePanel;
import ru.vorazen.main.World;

public class Player extends SuperEntity {
    public Player(String path, String name) {
        this.name = name;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            // TODO: handle exception
        }
    }

    private static GraphicsConfiguration getDefaultConfiguration() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

    @Override
    public void draw(Graphics2D g2, World world) {

        int width = image.getWidth();
        int height = image.getHeight();
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math.floor(h *
                cos + w * sin);
        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
                neww, newh, image.getType());

        // creating Graphics in buffered image
        Graphics2D gn = newImage.createGraphics();

        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        gn.rotate(angle, width / 2,
                height / 2);
        gn.drawImage(image, null, 0, 0);
        g2.drawImage(newImage, (int) ((worldX - world.ScreenX) * world.Xscale),
                (int) ((worldY - world.ScreenY) * world.Yscale), (int) (this.XSpriteSize * world.Xscale),
                (int) (this.YSpriteSize * world.Yscale), null);
    }

    @Override
    public void update(GamePanel gp) {
        boolean canMove = gp.world.cc.checkCollison(this);
        if (canMove) {
            worldX += speedX + intSpeedX;
            worldY += speedY + intSpeedY;
            speedX += accelX + intAccelX;
            speedY += accelY + intAccelY;
        }
        boolean canRotate = true;
        if (canRotate) {
            angle += angleSpeed;
            angleSpeed += angleAccel;
            intAngleSpeed += intAngleAccel;
        }
    }
}
