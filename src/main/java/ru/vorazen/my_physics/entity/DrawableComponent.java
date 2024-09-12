package ru.vorazen.my_physics.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ru.vorazen.my_physics.primitives.Primitive2D;
import ru.vorazen.my_physics.primitives.RigidBody2D;

public class DrawableComponent {

    private BufferedImage spriteImage;
    private RigidBody2D rigidBody;
    private int spriteWidth;
    private int spriteHeight;
    private int spriteLine;
    private int spriteCol;
    private BufferedImage[][] spriteFrame;

    public int getSpriteCol() {
        return spriteCol;
    }

    public int getSpriteLine() {
        return spriteLine;
    }

    public void setSpriteLine(int l) {
        spriteLine = l;
    }

    public void setSpriteCol(int c) {
        spriteCol = c;
    }

    public DrawableComponent(Primitive2D body, String path, int width, int height) {

        // get rigidBody for pos and angle
        rigidBody = body.getRigidBody2D();
        spriteHeight = height;
        spriteWidth = height;
        try {
            // load texture
            spriteImage = ImageIO.read(getClass().getResourceAsStream(path));
            int w = spriteImage.getWidth();
            int h = spriteImage.getHeight();
            int l = w / width;
            int c = h / height;
            // ystem.out.println(l + " " + c);
            spriteFrame = new BufferedImage[c][l];
            for (int i = 0; i < spriteFrame.length; i++) {
                for (int j = 0; j < spriteFrame[i].length; j++) {
                    spriteFrame[i][j] = spriteImage.getSubimage(j * width, i * height, width, height);
                    // System.out.println(spriteFrame[i][j]);
                }
            }
        } catch (IOException e) {

        }

    }

    public void paintComponent(Graphics2D g2) {

        double radians = rigidBody.getRotation();
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        // System.out.println(spriteCol + " " + spriteLine);
        BufferedImage image = spriteFrame[spriteLine][spriteCol];
        int newWidth = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        BufferedImage rotatedImg = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g2d = rotatedImg.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - image.getWidth()) / 2, (newHeight - image.getHeight()) / 2);
        at.rotate(radians, image.getWidth() / 2, image.getHeight() / 2);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        // ====================== FOR DEBUGGING
        // ====================== FOR DEBUGGING
        if (false) {
            Color c = Color.BLUE;
            int rg = c.getRGB();
            for (int i = 0; i < rotatedImg.getWidth(); i++) {
                rotatedImg.setRGB(i, 0, rg);
                rotatedImg.setRGB(i, rotatedImg.getHeight() - 1, rg);
            }
            for (int i = 0; i < rotatedImg.getHeight(); i++) {
                rotatedImg.setRGB(0, i, rg);
                rotatedImg.setRGB(rotatedImg.getWidth() - 1, i, rg);
            }
        }
        // ======================
        // ======================
        g2d.dispose();
        g2.drawImage(rotatedImg,
                (int) (rigidBody.getPosition().x + ((image.getWidth() - newWidth) / 2)
                        - rotatedImg.getWidth() / 2 * (rigidBody.getScale() - 1)),
                (int) (rigidBody.getPosition().y + ((image.getHeight() - newHeight) / 2)  - rotatedImg.getHeight() / 2 * (rigidBody.getScale() - 1)),
                (int) (rotatedImg.getWidth() * rigidBody.getScale()),
                (int) (rotatedImg.getHeight() * rigidBody.getScale()),
                null);
    }
}
